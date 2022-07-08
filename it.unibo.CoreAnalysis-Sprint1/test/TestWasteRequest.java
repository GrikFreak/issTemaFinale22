import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.*;

public class TestWasteRequest {

	@Before
	public void up() {
		new Thread(() -> MainCtxWasteServiceKt.main()).start();
		waitForWasteService();
	}


 	protected void waitForWasteService(){
		ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
		while( wasteservice == null ){
			ColorsOut.outappl("TestWasteRequest waits for appl ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			wasteservice = QakContext.Companion.getActor("wasteservice");
		}
	}

	@After
	public void down() {
		ColorsOut.outappl("TestWasteRequest ENDS" , ColorsOut.BLUE);
	}

    @Test
    public void testLoadRejected() {
        ColorsOut.outappl("testLoadRejected STARTS", ColorsOut.BLUE);
        String wasteTruckRequestStr = "msg(waste_request,request,truck,wasteservice,waste_request(plastic,550),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            String answer = connTcp.request(wasteTruckRequestStr);
            ColorsOut.outappl("testLoadRejected answer:" + answer, ColorsOut.GREEN);
            connTcp.close();
            assertTrue(answer.contains("loadrejected"));
        } catch (Exception e) {
            ColorsOut.outerr("testLoadRejected ERROR:" + e.getMessage());
        }
    }


	@Test
	public void testLoadAccepted() {
		ColorsOut.outappl("testLoadAccepted STARTS" , ColorsOut.BLUE);
 		String wasteTruckRequestStr = "msg(waste_request,request,truck,wasteservice,waste_request(plastic,60),1)";
		try{
			ConnTcp connTcp = new ConnTcp("localhost", 8025);
			String answer = connTcp.request(wasteTruckRequestStr);
 			ColorsOut.outappl("testLoadAccepted answer:" + answer , ColorsOut.GREEN);
			connTcp.close();
			assertTrue(answer.contains("loadaccept"));
		}catch(Exception e){
			ColorsOut.outerr("testLoadAccepted ERROR:" + e.getMessage());
		}
 	}

}