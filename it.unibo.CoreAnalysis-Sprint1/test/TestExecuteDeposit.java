import it.unibo.ctxWasteService.MainCtxWasteServiceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

import static org.junit.Assert.assertTrue;

public class TestExecuteDeposit {

	@Before
	public void up() {
		new Thread(){
			public void run(){
				MainCtxWasteServiceKt.main();
			}
		}.start();
		waitForWasteService();
  	}

 	protected void waitForWasteService(){
		ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
		while( wasteservice == null ){
			ColorsOut.outappl("TestExecuteDeposit waits for WS ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			wasteservice = QakContext.Companion.getActor("wasteservice");
		}
	}
	@After
	public void down() {
		ColorsOut.outappl("TestExecuteDeposit ENDS" , ColorsOut.BLUE);
	}

    @Test
    public void testExecute() {
        ColorsOut.outappl("testExecute STARTS", ColorsOut.BLUE);
        String wasteServiceExecuteStr = "msg(execute,dispatch,wasteservice,transporttrolley,execute(plastic,60),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            connTcp.forward(wasteServiceExecuteStr);
            connTcp.close();
        } catch (Exception e) {
            ColorsOut.outerr("testExecute ERROR:" + e.getMessage());
        }
    }
    
    @Test
    public void testWithdrawal() {
        ColorsOut.outappl("testWithdrawal STARTS", ColorsOut.BLUE);
        String transporttrolleyWithdrawalStr = "msg(withdrawal_done,dispatch,transporttrolley,wasteservice,withdrawal_done(DONE),1)";
        try {
            ConnTcp connTcp = new ConnTcp("localhost", 8025);
            connTcp.forward(transporttrolleyWithdrawalStr);
            connTcp.close();
        } catch (Exception e) {
            ColorsOut.outerr("testWithdrawal ERROR:" + e.getMessage());
        }
    }
	
	
}