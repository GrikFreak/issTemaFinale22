import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestStorage {

    @Before
    public void up() {
        new Thread(() -> MainCtxWasteServiceKt.main()).start();
        waitForWasteService();
    }

    protected void waitForWasteService(){
        ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
        while( wasteservice == null ){
            ColorsOut.outappl("TestStorage waits for WS ... " , ColorsOut.GREEN);
            CommUtils.delay(200);
            wasteservice = QakContext.Companion.getActor("wasteservice");
        }
    }

    @After
    public void down() {
        ColorsOut.outappl("TestStorage ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testStorageDone() {
        ColorsOut.outappl("testStorageDone STARTS", ColorsOut.BLUE);
        String wasteServiceStorageStr = "msg(storage_request,request,service,transporttrolley,storage_request(lwwwwww),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            String answer = connTcp.request(wasteServiceStorageStr);
            ColorsOut.outappl("testStorageRequest answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("storage_done"));
        } catch (Exception e) {
            ColorsOut.outerr("testStorageRequest ERROR:" + e.getMessage());
        }
    }

}