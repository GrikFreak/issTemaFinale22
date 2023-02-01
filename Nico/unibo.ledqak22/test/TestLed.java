import it.unibo.comm2022.utils.ColorsOut;
import it.unibo.ctxledqak22.MainCtxledqak22Kt;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestLed {

    @Before
    public void up() {
        new Thread(){
            public void run(){
                MainCtxledqak22Kt.main();
            }
        }.start();
        waitForApplStarted();
    }


    protected void waitForApplStarted(){
        ActorBasic led = QakContext.Companion.getActor("led");
        while( led == null ){
            ColorsOut.outappl("TestLed waits... " , ColorsOut.GREEN);
            CommUtils.delay(200);
            led = QakContext.Companion.getActor("led");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestLed ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testLedOn() {
        CoapClient client = new CoapClient("coap://localhost:8030/ctxledqak22/led");
        String dispatchLed = "msg(led_status,dispatch,gino,led,led_status(ON),18)";
        client.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("ON"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8030);
            connTcp.forward(dispatchLed);
            connTcp.close();
        }catch(Exception e) {
            System.out.println("testLedOn ERROR:" + e.getMessage());
        }
    }

    @Test
    public void testLedOff() {
        CoapClient client = new CoapClient("coap://localhost:8030/ctxledqak22/led");
        String dispatchLed = "msg(led_status,dispatch,gino,led,led_status(OFF),18)";
        client.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("OFF"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8030);
            connTcp.forward(dispatchLed);
            connTcp.close();
        }catch(Exception e) {
            System.out.println("testLedOn ERROR:" + e.getMessage());
        }
    }

    @Test
    public void testLedBlinks() {
        CoapClient client = new CoapClient("coap://localhost:8030/ctxledqak22/led");
        String dispatchLed = "msg(led_status,dispatch,gino,led,led_status(BLINKS),18)";
        client.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("BLINKS"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8030);
            connTcp.forward(dispatchLed);
            connTcp.close();
        }catch(Exception e) {
            System.out.println("testLedOn ERROR:" + e.getMessage());
        }
    }

}