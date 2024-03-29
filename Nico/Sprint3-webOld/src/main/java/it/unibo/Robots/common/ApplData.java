package it.unibo.Robots.common;

import it.unibo.kactor.IApplMessage;
import unibo.actor22comm.utils.CommUtils;

public class ApplData {

	public static final String resumeCmd     = "resume";
	public static final String startSysCmdId = "activate";
	public static final String haltSysCmdId  = "halt";
	
	//Generali, usati dalla classe-base QakActor22Fsm
	public static final IApplMessage resumeCmd(String sender, String receiver)   {
		return CommUtils.buildDispatch(sender, resumeCmd, "do", receiver );
	}
	public static final IApplMessage startSysCmd(String sender, String receiver)   {
		return CommUtils.buildDispatch(sender, startSysCmdId, "do", receiver );
	}
	public static final IApplMessage haltSysCmd(String sender, String receiver)   {
		return CommUtils.buildDispatch(sender, haltSysCmdId, "do", receiver );
	}

	
	public static final String activateId     = "activate";
	public static final String robotName      = "robot";
	public static final String controllerName = "robotCtrl";
	
	public static IApplMessage startEv  = CommUtils.buildEvent("main", "maincmd", activateId);

	/*
	 * MESSAGGI in cril
	*/	
	protected static String crilCmd(String move, int time){
		String crilCmd  = "{\"robotmove\":\"" + move + "\" , \"time\": " + time + "}";
		//ColorsOut.out( "ClientNaiveUsingPost |  buildCrilCmd:" + crilCmd );
		return crilCmd;
	}
	public static final String moveForward(int duration)  { return crilCmd("moveForward", duration) ; }
	public static final String moveBackward(int duration) { return crilCmd("moveBackward", duration); }
	public static final String turnLeft(int duration)     { return crilCmd("turnLeft", duration);     }
	public static final String turnRight(int duration)    { return crilCmd("turnRight", duration);    }
	public static final String stop(int duration)         { return crilCmd("alarm", duration);        }
	public static final String stop( )                    { return crilCmd("alarm", 10);        }

	public static final String activate( )                { return crilCmd("alarm", 10);        }
	
	//Per prove
	public static final String moveCmdId = "move";
	public static final IApplMessage moveCmd(String sender, String receiver, String payload)   {
		return CommUtils.buildDispatch(sender, moveCmdId, payload, receiver );
	}
	public static final IApplMessage infoRequest(String sender, String receiver, String payload)   {
		return CommUtils.buildDispatch(sender, moveCmdId, payload, receiver );
	}

	//Per WEnv VirtualRobot
	public final static String robotCmdId = "move";
	public final static String aril_w = "moveForward(300)";
	public final static String aril_a = "turnLeft(300)";
	
	public static final IApplMessage w(String sender, String receiver)   {
		return CommUtils.buildDispatch(sender,robotCmdId,aril_w,receiver);
	}
	public static final IApplMessage a(String sender, String receiver)   {
		return CommUtils.buildDispatch(sender,robotCmdId,aril_a,receiver);
	}
	
	//Per Robot 
	public final static String wallDetectedId = "wallDetected";
	public static final IApplMessage wallDetected ( String sender, String wallName )   { 
		return CommUtils.buildDispatch(sender,wallDetectedId,wallName,robotName);
	}
}
