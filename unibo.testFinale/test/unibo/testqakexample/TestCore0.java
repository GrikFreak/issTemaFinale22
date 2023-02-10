package unibo.testqakexample;
import static org.junit.Assert.assertTrue;


import it.unibo.ctxledqak22.MainCtxledqak22Kt;
import it.unibo.ctxwasteservice.MainCtxwasteserviceKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.eclipse.californium.core.CoapHandler;
import org.junit.*;
import unibo.comm22.coap.CoapConnection;
import unibo.comm22.utils.ColorsOut;
import unibo.comm22.utils.CommSystemConfig;
import unibo.comm22.utils.CommUtils;


public class TestCore0 {
private CoapConnection connWS;
	private CoapConnection connLED;
	private WasteServiceObserver wso = null;
	private LedObserver ledo = null;
	private Thread tWS = null;
//	private Thread tBR = null;

	@Before
	public void up() {
		CommSystemConfig.tracing=false;

		wso = new WasteServiceObserver();
		ledo = new LedObserver();

		startWSObserverCoap("localhost", wso);
		startLEDObserverCoap("192.168.1.123", ledo);
		CommUtils.delay(4000);
		//("localhost", new TrolleyStatusObserver());
		//startObserverCoap("localhost", new LedObserver());

		 tWS = new Thread(){
			public void run(){
				it.unibo.ctxwasteservice.MainCtxwasteserviceKt.main();
			}
		};
		System.out.println("PRE RUN --------------------");
		tWS.start();
		System.out.println("POST RUN WS--------------------");

		/*
		tBR = new Thread(){
			public void run(){
				it.unibo.basicRobotForTest.MainCtxbasicrobotKt.main();
			}
		};
		tBR.start();
		System.out.println("POST RUN BR--------------------");
*/
	/*new Thread(){
			public void run(){
				MainCtxledqak22Kt.main();
			}
		}.start();
*/

		waitForApplStarted();
		CommUtils.delay(10000);
		System.out.println("Before END --------------------");
  	}



	protected void waitForApplStarted(){
		ActorBasic wasteservice = QakContext.Companion.getActor("wasteservice");
		//ActorBasic led = QakContext.Companion.getActor("led");
		//ActorBasic basicrobot = QakContext.Companion.getActor("basicrobot");
		while( wasteservice == null ){
			ColorsOut.outappl("wasteservice waits for appl ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			wasteservice = QakContext.Companion.getActor("wasteservice");
		}
/*		while( led == null ){
			ColorsOut.outappl("led waits for appl ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			led = QakContext.Companion.getActor("led");
		}
		while( basicrobot == null ){
			ColorsOut.outappl("basicrobot waits for appl ... " , ColorsOut.GREEN);
			CommUtils.delay(200);
			basicrobot = QakContext.Companion.getActor("basicrobot");
		}*/

	}
	@After
	public void down() {
		//tWS.interrupt();
		//tBR.interrupt();
		ColorsOut.outappl("TestCore0 ENDS" , ColorsOut.BLUE);
	}

	@Test
	public void testLoadOK() {
		ColorsOut.outappl("testLoadOK STARTS" , ColorsOut.BLUE);
		//assertTrue( coapCheckWS("HOME") );
		assertTrue( coapCheckWS("IDLE") );
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8025);

			String truckRequestStr = "msg(waste_request, request, gino, wasteservice,waste_request(glass,200),1)";
			String answer     = connTcp.request(truckRequestStr);
 			ColorsOut.outappl("testLoadok answer=" + answer , ColorsOut.GREEN);
			assertTrue(answer.contains("loadaccept"));

			String truckFreeIndoor = "msg(free_request, request, gino, wasteservice,free_request(FREEEE),1)";
			answer     = connTcp.request(truckFreeIndoor);
			ColorsOut.outappl("testFreeIndoor answer=" + answer , ColorsOut.GREEN);
			assertTrue(answer.contains("free_indoor"));
			connTcp.close();

			CommUtils.delay(2000);
			assertTrue( coapCheckWS("INDOOR") );
			assertTrue( coapCheckWS("GENERIC") );
			assertTrue(coapCheckLED("BLINK"));

			CommUtils.delay(12000);
			assertTrue( coapCheckWS("CONTAINER_G") );
			assertTrue(coapCheckWS("WORKINGContainersWeight:0, 200"));
			//CommUtils.delay(3000);

			CommUtils.delay(10000);
			assertTrue( coapCheckWS("HOME") );
			assertTrue( coapCheckWS("IDLE") );
			assertTrue(coapCheckLED("OFF"));


		}catch(Exception e){
			ColorsOut.outerr("testLoadok ERROR:" + e.getMessage());

		}
 	}

	@Test
	public void testLoadKO() {
		ColorsOut.outappl("testLoadKO STARTS" , ColorsOut.BLUE);
		//assertTrue( coapCheckWS("HOME") );
		assertTrue( coapCheckWS("IDLE") );
		try{
			ConnTcp connTcp   = new ConnTcp("localhost", 8025);

			String truckRequestStr = "msg(waste_request, request, gino, wasteservice, waste_request(plastic,800),1)";
			String answer     = connTcp.request(truckRequestStr);
			ColorsOut.outappl("testLoadok answer=" + answer , ColorsOut.GREEN);
			assertTrue(answer.contains("loadrejected"));

			CommUtils.delay(2000);
			assertTrue( !coapCheckWS("INDOOR"));
			assertTrue( !coapCheckWS("GENERIC"));
			assertTrue( !coapCheckLED("BLINK"));

			 truckRequestStr = "msg(waste_request, request, gino, wasteservice, waste_request(plastic,200),1)";
			 answer     = connTcp.request(truckRequestStr);
			ColorsOut.outappl("testLoadok answer=" + answer , ColorsOut.GREEN);
			assertTrue(answer.contains("loadaccept"));

			String truckFreeIndoor = "msg(free_request, request, gino, wasteservice,free_request(FREEEE),1)";
			answer     = connTcp.request(truckFreeIndoor);
			ColorsOut.outappl("testFreeIndoor answer=" + answer , ColorsOut.GREEN);
			assertTrue(answer.contains("free_indoor"));
			connTcp.close();

			CommUtils.delay(2000);
			assertTrue( coapCheckWS("INDOOR") );
			assertTrue( coapCheckWS("GENERIC") );
			assertTrue(coapCheckLED("BLINK"));

			CommUtils.delay(8000);
			assertTrue( coapCheckWS("CONTAINER_P") );
			assertTrue(coapCheckWS("WORKINGContainersWeight:200, 0"));
			//CommUtils.delay(3000);

			CommUtils.delay(16000);
			assertTrue( coapCheckWS("HOME") );
			assertTrue( coapCheckWS("IDLE") );
			assertTrue(coapCheckLED("OFF"));


		}catch(Exception e){
			ColorsOut.outerr("testLoadok ERROR:" + e.getMessage());

		}
	}
//---------------------------------------------------

protected boolean coapCheckWS(String check){
	//String answer = connWS.request("");
	String answer = wso.getHistory();
	ColorsOut.outappl("coapCheckWS answer=" + answer, ColorsOut.CYAN);
	return answer.contains(check);
}

	protected boolean coapCheckLED(String check){
		//String answer = connLED.request("");
		String answer = ledo.getHistory();
		ColorsOut.outappl("coapCheckLED answer=" + answer, ColorsOut.CYAN);
		return answer.contains(check);
	}

protected void startWSObserverCoap(String addr, CoapHandler handler){
		new Thread(){
			public void run(){
				try {
					String ctxqakdest       = "ctxwasteservice";
					String qakdestination 	= "wasteservice";
					String applPort         = "8025";
					String path             = ctxqakdest+"/"+qakdestination;
					connWS = new CoapConnection(addr+":"+applPort, path);
					connWS.observeResource( handler );
					ColorsOut.outappl("connected via Coap conn to WasteService:" + connWS, ColorsOut.CYAN);
				}catch(Exception e){
					ColorsOut.outerr("connectUsingCoap ERROR:"+e.getMessage());
				}
			}
		}.start();
}

	private void startLEDObserverCoap(String addr, CoapHandler handler) {
		new Thread(){
			public void run(){
				try {
					String ctxqakdest       = "ctxledqak22";
					String qakdestination 	= "led";
					String applPort         = "8030";
					String path             = ctxqakdest+"/"+qakdestination;
					connLED = new CoapConnection(addr+":"+applPort, path);
					connLED.observeResource( handler );
					ColorsOut.outappl("connected via Coap conn to led:" + connWS, ColorsOut.CYAN);
				}catch(Exception e){
					ColorsOut.outerr("connectUsingCoap ERROR:"+e.getMessage());
				}
			}
		}.start();
	}

}