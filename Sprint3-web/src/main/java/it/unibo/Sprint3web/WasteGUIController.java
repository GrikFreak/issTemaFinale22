package it.unibo.Sprint3web;

import it.unibo.Sprint3web.model.WasteTruckConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.unibo.Robots.common.RobotUtils;
import it.unibo.Sprint3web.model.IpConfig;
import unibo.comm22.coap.CoapConnection;

@Controller
public class WasteGUIController {
    //private static final String robotCmdId = "move";
    protected static  String robotName     = "basicrobot"; //visibility in package
    protected static String mainPage = "WasteServiceStatusGui";
    protected boolean usingTcp            = false;

    //Settaggio degli attributi del modello

    @Value("not connected")
    String ledip;
    @Value("not connected")
    String wasteserviceip;
    @Value("8030")
    int portled;
    @Value("8025")
    int portwasteservice;
    @Value("unknown")
    String type;
    @Value("0")
    int quantity;

    protected String buildThePage(Model viewmodel) {
        setConfigParams(viewmodel);
        return mainPage;
    }

    protected void setConfigParams(Model viewmodel){
        viewmodel.addAttribute("ledip",  ledip);
        viewmodel.addAttribute("wasteserviceip",wasteserviceip);
        viewmodel.addAttribute("portled",  portled);
        viewmodel.addAttribute("portwasteservice",portwasteservice);
        viewmodel.addAttribute("quantity", quantity);
        viewmodel.addAttribute("type", type);
    }

    @GetMapping("/")
    public String entry(Model viewmodel) {
        return buildThePage(viewmodel);
    }

    @PostMapping("/setip")
    public String setip(Model viewmodel, @ModelAttribute IpConfig ipConfig){

        ledip = ipConfig.getIp_led();
        portled = ipConfig.getPort_led();
        wasteserviceip = ipConfig.getIp_ws();
        portwasteservice = ipConfig.getPort_ws();

        viewmodel.addAttribute("ledip", ledip);
        viewmodel.addAttribute("wasteserviceip",wasteserviceip);
        viewmodel.addAttribute("portled", portled);
        viewmodel.addAttribute("portwasteservice",portwasteservice);

        //Attivo connessione TCP per inviare richiesta del waste truck
        WasteGUIUtils.connectWithWasteServiceUsingTcp(wasteserviceip,portwasteservice);

        //Attivo una connessione CoAP per osservare
        CoapConnection connWaste = WasteGUIUtils.connectWithWasteServiceUsingCoap(wasteserviceip,portwasteservice);
        CoapConnection connLed = WasteGUIUtils.connectWithLedUsingCoap(ledip,portled);

        connWaste.observeResource( new RobotCoapObserver() );
        connLed.observeResource( new RobotCoapObserver() );

        return buildThePage(viewmodel);
        //return mainPage;
    }

    @ResponseBody
    @PostMapping("/waste_request")
    public String sendWasteTruckRequest(Model viewmodel, @ModelAttribute WasteTruckConfig wasteTruckConfig){
        type = wasteTruckConfig.getType();
        quantity = wasteTruckConfig.getQuantity();
        String msg =  "waste_request("+type+","+quantity+")";
        System.out.println("MSG: "+msg);
        String res="";
        try {
            res = WasteGUIUtils.sendWasteTruckReq(msg);
        } catch (Exception e){
            System.out.println("WasteController | sendWasteTruckRequest ERROR:"+e.getMessage());
        }

        //return mainPage;
        return res;
    }

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
                "BaseController ERROR " + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }
}
