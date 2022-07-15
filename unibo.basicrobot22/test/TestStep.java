import it.unibo.ctxbasicrobot.MainCtxbasicrobotKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.*;

public class TestStep {
    @Before
    public void up() {
        new Thread(() -> MainCtxbasicrobotKt.main()).start();
        waitForRobot();
    }


    protected void waitForRobot(){
        ActorBasic basicRobot = QakContext.Companion.getActor("basicrobot");
        while( basicRobot == null ){
            ColorsOut.outappl("TestStep waits for appl ... " , ColorsOut.GREEN);
            CommUtils.delay(200);
            basicRobot = QakContext.Companion.getActor("basicrobot");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestStep ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testStepRequest() {
        ColorsOut.outappl("testStepRequest STARTS", ColorsOut.BLUE);
        String stepRequestStr = "msg(step,request,gino,basicrobot,step(1200),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8020);
            String answer = connTcp.request(stepRequestStr);
            ColorsOut.outappl("testStepRequest answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("stepdone"));
        } catch (Exception e) {
            ColorsOut.outerr("testStepRequest ERROR:" + e.getMessage());
        }
    }
}