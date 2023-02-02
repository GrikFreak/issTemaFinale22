/* Generated by AN DISI Unibo */ 
package it.unibo.wasteservice

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wasteservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var MAXPB 	= 500L;
				var MAXGB 	= 500L;
				var CurrentPB = 0L;
				var CurrentGB = 0L;
				var Material = "";
				var PathHome = "";
				var PathIndoor = "";
				var PathContainer = "";
				val XIndoor = 0;
				val YIndoor = 4;
				val XContainerP = 6;
				val YContainerP = 4;
				val XContainerG = 6;
				val YContainerG = 0;
				val XHome = 0;
				val YHome = 0;
				
				var TrolleyStatus = "IDLE"; //IDLE, WORKING, STOPPED
				var TrolleyLastState = "TO_INDOOR"; //TO_HOME, TO_CONTAINER, TO_INDOOR, PICKING, STORAGING
				var TrolleyPosition = "HOME"; //HOME, CONTAINER_P, CONTAINER_G, INDOOR, GENERIC
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | Start.")
						unibo.kotlin.planner22Util.initAI(  )
						unibo.kotlin.planner22Util.showCurrentRobotState(  )
						forward("led_status", "led_status(OFF)" ,"led" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | is waiting for a request..")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="go_to_Indoor",cond=whenRequest("waste_request"))
					transition(edgeName="t03",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("go_to_Indoor") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Material = payloadArg(0);
												var TruckLoad 	= payloadArg(1).toLong();
								println("WASTESERVICE | arrived request: $Material with load $TruckLoad")
								if(  Material.equals("plastic")  
								 ){if(  TruckLoad + CurrentPB <= MAXPB  
								 ){answer("waste_request", "loadaccept", "loadaccept($Material,$TruckLoad)"   )  
								 CurrentPB += TruckLoad  
								println("WASTESERVICE | current container plastic weight: $CurrentPB")
								unibo.kotlin.planner22Util.setGoal( XIndoor, YIndoor  )
								 PathIndoor = unibo.kotlin.planner22Util.doPlan().toString()
														.replace(" ","")
														.replace(",","")
														.replace("[","")
														.replace("]","")
								request("transfer_request", "transfer_request($PathIndoor)" ,"transporttrolley" )  
								 
														TrolleyStatus = "WORKING";
														TrolleyLastState = "TO_INDOOR";
														TrolleyPosition = "GENERIC";
								forward("led_status", "led_status(BLINKS)" ,"led" ) 
								updateResourceRep( "TROLLEY_POS:GENERIC"  
								)
								updateResourceRep( "TROLLEY_STATUS:WORKING"  
								)
								println("WASTESERVICE | Sent pickup request to TransportTrolley, indoor path $PathIndoor.")
								}
								else
								 {answer("waste_request", "loadrejected", "loadrejected($Material,$TruckLoad)"   )  
								 }
								}
								else
								 {if(  TruckLoad + CurrentGB <= MAXGB  
								  ){answer("waste_request", "loadaccept", "loadaccept($Material,$TruckLoad)"   )  
								  CurrentGB += TruckLoad  
								 println("WASTESERVICE | current container glass weight: $CurrentGB")
								 unibo.kotlin.planner22Util.setGoal( XIndoor, YIndoor  )
								  PathIndoor = unibo.kotlin.planner22Util.doPlan().toString()
								 						.replace(" ","")
								 						.replace(",","")
								 						.replace("[","")
								 						.replace("]","")
								 request("transfer_request", "transfer_request($PathIndoor)" ,"transporttrolley" )  
								  
								 						TrolleyStatus = "WORKING";
								 						TrolleyLastState = "TO_INDOOR";
								 						TrolleyPosition = "GENERIC";
								 forward("led_status", "led_status(BLINKS)" ,"led" ) 
								 updateResourceRep( "TROLLEY_POS:GENERIC"  
								 )
								 updateResourceRep( "TROLLEY_STATUS:WORKING"  
								 )
								 println("WASTESERVICE | Sent pickup request to TransportTrolley, first path $PathIndoor")
								 }
								 else
								  {answer("waste_request", "loadrejected", "loadrejected($Material,$TruckLoad)"   )  
								  }
								 }
								updateResourceRep( "ContainersWeight:$CurrentPB, $CurrentGB"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t14",targetState="handle_pickup",cond=whenReply("transfer_done"))
					transition(edgeName="t15",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("handle_pickup") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						 TrolleyPosition = "INDOOR"  
						updateResourceRep( "TROLLEY_POS:INDOOR"  
						)
						if( checkMsgContent( Term.createTerm("transfer_done(DONE)"), Term.createTerm("transfer_done(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								request("pickup_request", "pickup_request($Material)" ,"transporttrolley" )  
						}
						 TrolleyLastState = "PICKING" 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t26",targetState="handle_free_truck",cond=whenRequest("free_request"))
				}	 
				state("handle_free_truck") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("free_request(ARG)"), Term.createTerm("free_request(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("WASTESERVICEeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee | Sent message to WasteTruck to leave indoor area.")
								answer("free_request", "free_indoor", "free_indoor(done)"   )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t37",targetState="go_to_Container",cond=whenReply("pickup_done"))
					transition(edgeName="t38",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("go_to_Container") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						unibo.kotlin.planner22Util.updateMapWithPath( PathIndoor  )
						unibo.kotlin.planner22Util.showCurrentRobotState(  )
						if(  Material.equals("plastic")  
						 ){unibo.kotlin.planner22Util.setGoal( XContainerP, YContainerP  )
						 PathContainer = unibo.kotlin.planner22Util.doPlan().toString()
										.replace(" ","")
										.replace(",","")
										.replace("[","")
										.replace("]","")
						 TrolleyLastState = "TO_CONTAINER"  
						}
						else
						 {unibo.kotlin.planner22Util.setGoal( XContainerG, YContainerG  )
						  PathContainer = unibo.kotlin.planner22Util.doPlan().toString()
						 				.replace(" ","")
						 				.replace(",","")
						 				.replace("[","")
						 				.replace("]","")
						  TrolleyLastState = "TO_CONTAINER"  
						 }
						println("WASTESERVICE | send request to store to the transport trolley.")
						updateResourceRep( "TROLLEY_POS:GENERIC"  
						)
						request("transfer_request", "transfer_request($PathContainer)" ,"transporttrolley" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t49",targetState="handle_storage",cond=whenReply("transfer_done"))
					transition(edgeName="t410",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("handle_storage") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if(  Material.equals("plastic")  
						 ){ TrolleyPosition = "CONTAINER_P"  
						updateResourceRep( "TROLLEY_POS:CONTAINER_P"  
						)
						}
						else
						 { TrolleyPosition = "CONTAINER_G"  
						 updateResourceRep( "TROLLEY_POS:CONTAINER_G"  
						 )
						 }
						if( checkMsgContent( Term.createTerm("transfer_done(DONE)"), Term.createTerm("transfer_done(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								unibo.kotlin.planner22Util.updateMapWithPath( PathContainer  )
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
								request("storage_request", "storage_request($Material)" ,"transporttrolley" )  
						}
						 TrolleyLastState = "STORAGING"  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t511",targetState="waiting_request",cond=whenReply("storage_done"))
					transition(edgeName="t512",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("waiting_request") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		//sysaction { //it:State
				 	 		  stateTimer = TimerActor("timer_waiting_request", 
				 	 			scope, context!!, "local_tout_wasteservice_waiting_request", 100.toLong() )
				 	 		//}
					}	 	 
					 transition(edgeName="t613",targetState="go_to_Home",cond=whenTimeout("local_tout_wasteservice_waiting_request"))   
					transition(edgeName="t614",targetState="go_to_Indoor",cond=whenRequest("waste_request"))
					transition(edgeName="t615",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("go_to_Home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | No Queue, send Transport trolley to HOME")
						unibo.kotlin.planner22Util.setGoal( XHome, YHome  )
						
								PathHome = unibo.kotlin.planner22Util.doPlan().toString() 
									.replace(" ","")
									.replace(",","")
									.replace("[","")
									.replace("]","")
						 
									TrolleyLastState = "TO_HOME";
						updateResourceRep( "TROLLEY_POS:GENERIC"  
						)
						request("home_request", "home_request($PathHome)" ,"transporttrolley" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t716",targetState="back_to_Home",cond=whenReply("home_done"))
					transition(edgeName="t717",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
				state("back_to_Home") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | the trolley is arrived at HOME")
						 
									TrolleyStatus = "IDLE"
									TrolleyPosition = "HOME"
						updateResourceRep( "TROLLEY_POS:HOME"  
						)
						updateResourceRep( "TROLLEY_STATUS:IDLE"  
						)
						if( checkMsgContent( Term.createTerm("home_done(DONE)"), Term.createTerm("home_done(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("led_status", "led_status(OFF)" ,"led" ) 
								unibo.kotlin.planner22Util.updateMapWithPath( PathHome  )
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handle_stop") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | Trolley stopped by the sonar, wait for resume..")
						 TrolleyStatus = "STOPPED"  
						updateResourceRep( "TROLLEY_STATUS:STOPPED"  
						)
						forward("stop_trolley", "stop_trolley(STOP)" ,"transporttrolley" ) 
						forward("led_status", "led_status(ON)" ,"led" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t818",targetState="handle_resume",cond=whenDispatch("resume"))
				}	 
				state("handle_resume") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | resume trolley after sonar resume message.")
						 TrolleyStatus = "WORKING"  
						updateResourceRep( "TROLLEY_STATUS:WORKING"  
						)
						forward("led_status", "led_status(BLINKS)" ,"led" ) 
						forward("resume_trolley", "resume_trolley($TrolleyLastState)" ,"transporttrolley" ) 
						delay(300) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t919",targetState="handle_pickup",cond=whenReplyGuarded("transfer_done",{ TrolleyLastState == "TO_INDOOR"  
					}))
					transition(edgeName="t920",targetState="handle_storage",cond=whenReplyGuarded("transfer_done",{ TrolleyLastState == "TO_CONTAINER"  
					}))
					transition(edgeName="t921",targetState="back_to_Home",cond=whenReplyGuarded("transfer_done",{ TrolleyLastState == "TO_HOME"  
					}))
					transition(edgeName="t922",targetState="go_to_Container",cond=whenReply("pickup_done"))
					transition(edgeName="t923",targetState="waiting_request",cond=whenReply("storage_done"))
					transition(edgeName="t924",targetState="handle_stop",cond=whenDispatch("stop"))
				}	 
			}
		}
}
