package it.unibo.Sprint3web;
/*
import unibo.actor22comm.coap.CoapConnection;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.tcp.TcpClientSupport;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
*/

import it.unibo.kactor.IApplMessage;
import unibo.comm22.coap.CoapConnection;
import unibo.comm22.interfaces.Interaction2021;
import unibo.comm22.tcp.TcpClientSupport;
import unibo.comm22.utils.ColorsOut;
import unibo.comm22.utils.CommUtils;

public class WasteGUIUtils {

    private static Interaction2021 conn;
    private static Interaction2021 connTcp;

    public static void connectWithWasteServiceUsingTcp(String addr, int port){
        try {
            connTcp = TcpClientSupport.connect(addr, port, 10);
            System.out.println("WSUtils | connect WASTE_SERVICE with Tcp conn:" + connTcp );
        }catch(Exception e){
            ColorsOut.outerr("WSUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    public static String sendWasteTruckReq(String payload) {
        String answer="";
        try {
            IApplMessage msg =  CommUtils.buildRequest("webGui", "waste_request", payload, "wasteservice");
            ColorsOut.outappl("WSUtils | sendMsg msg:" + msg + " conn=" + connTcp, ColorsOut.BLUE);
            if( msg.isRequest() ){
                answer = connTcp.request( msg.toString() );
                System.out.println("WasteRequestUtils | answer:" + answer );
            } else {
                // altro tipo di msg
            }
        } catch (Exception e) {
            ColorsOut.outerr("WasteRequestUtils | sendMsg ERROR:"+e.getMessage());
        }
        if(answer.contains("loadaccept")) {
            return "loadaccept";
        }
        else if(answer.contains("loadrejected")){
            return "loadrejected";
        } else return "free_indoor";
    }
    public static String sendFreeIndoorRequest(String payload) {
        String answer="";
        try {
            IApplMessage msg =  CommUtils.buildRequest("webGui", "free_request", payload, "wasteservice");
            ColorsOut.outappl("FreeIndoorUtils | sendMsg msg:" + msg + " conn=" + connTcp, ColorsOut.BLUE);
            if( msg.isRequest() ){
                answer = connTcp.request( msg.toString() );
                System.out.println("FreeIndoorUtils | answer:" + answer );
            } else {
                System.out.println("Sono nell' else..");
            }
        } catch (Exception e) {
            ColorsOut.outerr("FreeIndoorUtils | sendMsg ERROR:"+e.getMessage());
        }
        if(answer.contains("loadaccept")) {
            return "loadaccept";
        }
        else if(answer.contains("loadrejected")){
            return "loadrejected";
        } else return "free_indoor";
    }
    public static String sendStopResumeTrolley(String payload) {
        IApplMessage msg;
        try {
            msg = CommUtils.buildEvent("webGui", "sonardata", payload);
            ColorsOut.outappl("WSUtils | sendMsg msg:" + msg + " conn=" + connTcp, ColorsOut.BLUE);
            if(msg.isEvent()){
                connTcp.forward(msg.toString());
            }
        } catch (Exception e) {
            ColorsOut.outerr("WSUtils | sendMsg ERROR:" + e.getMessage());
        }
        return "Stop/Resume concluded";
    }

    public static CoapConnection connectWithLedUsingCoap(String addr, int port){
        try {
            String ctxqakdest       = "ctxledqak22";
            String qakdestination 	= "led";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+port, path);
            ((CoapConnection)conn).observeResource( new LedCoapObserver() );
            System.out.println("LedUtils | connect LED with Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("LedUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }

    public static CoapConnection connectWithWasteServiceUsingCoap(String addr, int port){
        try {
            String ctxqakdest       = "ctxwasteservice";
            String qakdestination 	= "wasteservice";
            String path   = ctxqakdest+"/"+qakdestination;
            conn           = new CoapConnection(addr+":"+port, path);
            ((CoapConnection)conn).observeResource( new WSCoapObserver() );
            System.out.println("WSUtils | connect WASTE_SERVICE with Coap conn:" + conn );
        }catch(Exception e){
            System.out.println("WSUtils | connectUsingCoap ERROR:"+e.getMessage());
        }
        return (CoapConnection) conn;
    }
}
