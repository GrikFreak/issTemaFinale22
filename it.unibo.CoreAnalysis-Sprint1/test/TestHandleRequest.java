import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;


public class TestHandleRequest {
    @Before
    public void up() {
        new Thread(() -> MainCtxWasteServiceKt.main()).start();
        waitForWasteService();
    }

    protected void waitForWasteService() {
        ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
        while (wasteservice == null) {
            ColorsOut.outappl("TestHandleRequest waits for WS ... ", ColorsOut.GREEN);
            CommUtils.delay(200);
            wasteservice = QakContext.Companion.getActor("wasteservice");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestHandleRequest ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void DepositAction() throws Exception {
        CoapClient client = new CoapClient("coap://localhost:8025/ctxWasteService/transporttrolley");
        String wasteTruckRequestStr = "msg(waste_request,request,truck,wasteservice,waste_request(plastic,60),1)";
        client.observe(new CoapHandler() {
            int counter = 0;
            @Override
            public void onLoad(CoapResponse response) {
                counter++;
                System.out.println("Response: "+response.getResponseText()+"counter: "+counter);
                switch (counter) {
                    case 1 : assertTrue(response.getResponseText().contains("PICKUP")); break;
                    case 2 : assertTrue(response.getResponseText().contains("STORAGE")); break;
                    case 3 : assertTrue(response.getResponseText().contains("HOME")); break;
                    default: break;
                }
            }

            @Override
            public void onError() {
                System.out.println("ERROR!");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8025);
            String answer     = connTcp.request(wasteTruckRequestStr);
            System.out.println("Answer=" + answer );
            connTcp.close();
        }catch(Exception e){
            System.out.println("test ERROR:" + e.getMessage());
        }
        Thread.sleep(7000);

    }

    @Test
    public void DepositActionQueue() throws Exception {
        CoapClient client = new CoapClient("coap://localhost:8025/ctxWasteService/transporttrolley");
        String wasteTruckRequestStr = "msg(waste_request,request,truck,wasteservice,waste_request(glass,20),1)";
        client.observe(new CoapHandler() {
            int counter = 0;
            @Override
            public void onLoad(CoapResponse response) {
                counter++;
                System.out.println("Response: "+response.getResponseText());
                switch (counter) {
                    case 1 : assertTrue(response.getResponseText().contains("pickup_done")); break;
                    case 2 : assertTrue(response.getResponseText().contains("storage_done")); break;
                    case 3 : assertTrue(response.getResponseText().contains("pickup_done")); break;
                    case 4 : assertTrue(response.getResponseText().contains("storage_done")); break;
                    case 5 : assertTrue(response.getResponseText().contains("home_done")); break;
                    default: break;
                }

            }

            @Override
            public void onError() {
                System.out.println("ERROR!");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8025);
            String answer     = connTcp.request(wasteTruckRequestStr);
            System.out.println("Answer=" + answer );
            connTcp.close();
        }catch(Exception e){
            System.out.println("test ERROR:" + e.getMessage());
        }
        Thread.sleep(12000);

    }
}