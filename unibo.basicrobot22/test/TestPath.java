import it.unibo.ctxbasicrobot.MainCtxbasicrobotKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.*;

public class TestPath {
    @Before
    public void up() {

    }

    @After
    public void down() {
        ColorsOut.outappl("TestStep ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testStepRequest() {
        ColorsOut.outappl("testStepRequest STARTS", ColorsOut.BLUE);
        String stepRequestStr = "msg(dopath,request,gino,pathexec,dopath(wwwwwl),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8020);
            String answer = connTcp.request(stepRequestStr);
            ColorsOut.outappl("testStepRequest answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("dopathdone"));
        } catch (Exception e) {
            ColorsOut.outerr("testStepRequest ERROR:" + e.getMessage());
        }
    }
}