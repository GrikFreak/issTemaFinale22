import it.unibo.comm2022.utils.ColorsOut;
import it.unibo.ctxraspberrypi.MainCtxraspberrypiKt;
import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
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

public class TestSprint2 {

    @Before
    public void up() {
        new Thread(){
            public void run(){
                MainCtxWasteServiceKt.main();
            }
        }.start();
        waitForApplStarted();
    }


    protected void waitForApplStarted(){
        ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
        while( wasteservice == null ){
            ColorsOut.outappl("TestSprint2 waits... " , ColorsOut.GREEN);
            CommUtils.delay(200);
            wasteservice = QakContext.Companion.getActor("wasteservice");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestSprint2 ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void TestLedOnAfterStop() throws InterruptedException {
        CoapClient client_trolley = new CoapClient("coap://localhost:8025/ctxWasteService/wasteservice");
        CoapClient client_led = new CoapClient("coap://127.0.0.1:8030/ctxledqak22/led");
        String stop = "msg(stop,dispatch,gino,wasteservice,stop(10),18)";
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8025);
            Thread.sleep(5000);
            connTcp.forward(stop);
            connTcp.close();
        }catch(Exception e) {
            System.out.println("testLedOnAfterStop ERROR:" + e.getMessage());
        }
        Thread.sleep(2000);

        client_trolley.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("STOP"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });

        client_led.observe(new CoapHandler() {
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

        Thread.sleep(2000);
    }

    @Test
    public void TestResumeAfterStop() throws Exception {
        CoapClient client = new CoapClient("coap://localhost:8025/ctxWasteService/wasteservice");
        String stop = "msg(stop,dispatch,gino,wasteservice,stop(10),18)";
        String resume = "msg(resume,dispatch,gino,wasteservice,resume(65),18)";
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8025);
            Thread.sleep(5000);
            connTcp.forward(stop);
            Thread.sleep(5000);
            connTcp.forward(resume);
            connTcp.close();
        }catch(Exception e) {
            System.out.println("TestResumeAfterStop ERROR:" + e.getMessage());
        }

        client.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("OK"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });
        Thread.sleep(3000);
    }

    @Test
    public void TestBlinksAfterAcceptedLoad() throws InterruptedException {
        String wastetruckRequestStr = "msg(waste_request,request,gino,wasteservice,waste_request(glass,26),1)";
        CoapClient client_led = new CoapClient("coap://localhost:8030/ctxledqak22/led");
        try{
            ConnTcp connTcp   = new ConnTcp("localhost", 8025);

            String answer     = connTcp.request(wastetruckRequestStr);
            System.out.println("Response=" + answer );
            assertTrue(answer.contains("loadaccept"));
            connTcp.close();

        }catch(Exception e){
            System.out.println("TestBlinksAfterAcceptedLoad ERROR:" + e.getMessage());

        }
        client_led.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response){
                System.out.println("Response: "+response.getResponseText());
                assertTrue(response.getResponseText().contains("BLINK"));
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });
        Thread.sleep(3000);
    }

}