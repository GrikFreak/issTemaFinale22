package it.unibo.Robots.cleaner;


/*
 * 
 */

import it.unibo.kactor.IApplMessage;

import it.unibo.Robots.common.VRobotMoves;
import it.unibo.Robots.common.WsConnApplObserver;
import unibo.actor22comm.SystemData;
import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.ws.WsConnection;
import unibo.actor22.QakActor22FsmAnnot;
import unibo.actor22.annotations.*;


public class RobotCleanerInterrupt extends QakActor22FsmAnnot{
	private Interaction2021 conn;

	private int numIter     = 0;
	private int numIterOk   = 5;
	private int turnStep    = 800;   //600 => too fast
 
	public RobotCleanerInterrupt(String name) {
		super(name);
	}

	protected void init() {
 		ColorsOut.outappl(getName() + " | ws connecting ...." ,  ColorsOut.BLUE);
		conn = WsConnection.create("localhost:8091" ); 
		IObserver robotMoveObserver = new WsConnApplObserver(getName(), false);

		((WsConnection)conn).addObserver(robotMoveObserver);
 		ColorsOut.outappl(getName() + " | conn:" + conn,  ColorsOut.BLUE);
	}

	@State( name = "activate", initial=true)
	@Transition( state = "start",   msgId= SystemData.startSysCmdId  )
	protected void activate( IApplMessage msg ) {
		outInfo(""+msg);
		init();
		numIter++;
	}

	@State( name = "start" )
	@Transition( state = "stopped", msgId= SystemData.stopSysCmdId, interrupt = true   )
	@Transition( state = "goingDown",   msgId="endMoveOk"  )
	@Transition( state = "endJob",      msgId="endMoveKo"  )
	protected void start( IApplMessage msg ) {
		outInfo(""+msg);
     	VRobotMoves.step(getName(), conn );
	}
	
	@State( name = "goingDown" )
	@Transition( state = "stopped",   msgId= SystemData.stopSysCmdId, interrupt = true  )
	@Transition( state = "goingDown",     msgId="endMoveOk"  )
	@Transition( state = "turnGoingDown", msgId="endMoveKo"  )
	protected void goingDown( IApplMessage msg ) {
		outInfo(""+msg);
		VRobotMoves.step(getName(), conn );
	}
	
	@State( name = "turnGoingDown" ) //potrebbe collidere col wallRight
	@Transition( state = "goingUp",     msgId="endMoveOk"  )
	@Transition( state = "lastColumn",  msgId="endMoveKo"  )
 	protected void turnGoingDown( IApplMessage msg ) {
		outInfo(""+msg);
		VRobotMoves.turnLeftAndStep(getName(), turnStep, conn);
	}

	@State( name = "goingUp" )
	@Transition( state = "stopped",   msgId= SystemData.stopSysCmdId, interrupt = true  )
	@Transition( state = "goingUp",     msgId="endMoveOk"  )
	@Transition( state = "turnGoingUp", msgId="endMoveKo"  )  //if numIter
	protected void goingUp( IApplMessage msg ) {
		outInfo(""+msg);		
		VRobotMoves.step(getName(), conn );
	}

	@State( name = "turnGoingUp" )   //potrebbe collidere col wallRight
	@Transition( state = "goingDown",   msgId="endMoveOk"  )
	@Transition( state = "lastColumn",  msgId="endMoveKo"  )  //if numIter
 	protected void turnGoingUp( IApplMessage msg ) {
		outInfo(""+msg);
		numIter++;
		if( numIter == numIterOk ) ColorsOut.outappl(getName() + " | DONE " ,  ColorsOut.BLUE);
		else VRobotMoves.turnRightAndStep(getName(), turnStep, conn);
	}

	@State( name = "lastColumn" )
	@Transition( state = "stopped",  msgId= SystemData.stopSysCmdId, interrupt = true  )
	@Transition( state = "lastColumn",   msgId="endMoveOk"  )
	@Transition( state = "completed",    msgId="endMoveKo"  )
	protected void lastColumn( IApplMessage msg ) {
		outInfo(""+msg);
		//outInfo("numIter="+numIter);
		VRobotMoves.step(getName(), conn ); 
	}
	
	@State( name = "completed" )
	@Transition( state = "endJob",    msgId="endMoveOk"  )
	@Transition( state = "endJob",    msgId="endMoveKo"  )
	protected void completed( IApplMessage msg ) {
		outInfo(""+msg);
		numIter++;
		outInfo("numIter="+numIter);
		if( numIter == numIterOk ) ColorsOut.outappl(getName() + " | DONE " ,  ColorsOut.MAGENTA);  
		else ColorsOut.outerr(getName() + " | COMPLETED TOO FAST "  );  
		VRobotMoves.turnLeftAndHome(getName(), conn ); 
	}
	
	
	@State( name = "endJob" )
	protected void endJob( IApplMessage msg ) {
		outInfo("BYE" );
		VRobotMoves.turnLeft(getName(), conn);
   	}

  	//--------------------------------------------------

	@State( name = "stopped" )
	@Transition( state = "doresume",  msgId= SystemData.resumeSysCmdId  )
 	protected void stopped( IApplMessage msg ) {
		outInfo("" + msg);
	}

	@State( name = "doresume" )
 	protected void doresume( IApplMessage msg ) {
		outInfo("" + msg);
		//RESUME: faccio le addTransition che avrebbe fatto lo stato interrupted senza la parte di interrupt
		resume();
	}

 
 
}


 
