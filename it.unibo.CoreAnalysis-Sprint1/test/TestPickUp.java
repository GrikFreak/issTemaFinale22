import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestPickUp {

	@Before
	public void up() {
		new Thread(() -> MainCtxWasteServiceKt.main()).start();
		waitForWasteService();
  	}

 	protected void waitForWasteService(){
		ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
		while( wasteservice == null ){
			ColorsOut.outappl("TestPickUp waits for WS ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			wasteservice = QakContext.Companion.getActor("wasteservice");
		}
	}

    @After
    public void down() {
        ColorsOut.outappl("TestPickUp ENDS" , ColorsOut.BLUE);
    }

    @Test
    public void testPickUpDone() {
        ColorsOut.outappl("testExecute STARTS", ColorsOut.BLUE);
        String wasteServicePickUpStr = "msg(pickup_request,request,service,transporttrolley,pickup_request(plastic,60),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            String answer = connTcp.request(wasteServicePickUpStr);
            ColorsOut.outappl("testPickUpRequest answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("pickup_done"));
        } catch (Exception e) {
            ColorsOut.outerr("testPickUpRequest ERROR:" + e.getMessage());
        }
    }

}