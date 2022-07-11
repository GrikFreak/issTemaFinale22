import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestHome {

    @Before
    public void up() {
        new Thread(() -> MainCtxWasteServiceKt.main()).start();
        waitForWasteService();
    }

    protected void waitForWasteService(){
        ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
        while( wasteservice == null ){
            ColorsOut.outappl("TestHome waits for WS ... " , ColorsOut.GREEN);
            CommUtils.delay(200);
            wasteservice = QakContext.Companion.getActor("wasteservice");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestHome ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testHomeDone() {
        ColorsOut.outappl("testHomeDone STARTS", ColorsOut.BLUE);
        String wasteServiceHomeStr = "msg(home_request,request,service,transporttrolley,home_request(lwwwwlwwwwww),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            String answer = connTcp.request(wasteServiceHomeStr);
            ColorsOut.outappl("testHomeRequest answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("home_done"));
        } catch (Exception e) {
            ColorsOut.outerr("testHomeRequest ERROR:" + e.getMessage());
        }
    }

}