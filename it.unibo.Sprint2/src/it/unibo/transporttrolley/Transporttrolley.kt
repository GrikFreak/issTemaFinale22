/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Status	= "IDLE";	
				var Position = "HOME";
				var Material = "";
				var PathHome = "";
				var PathIndoor = "";
				var PathContainer = "";
				
				var PathToDo = "";
				var LastState = ""; //to_indoor, to_container, to_home
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the TransportTrolley is waiting..")
					}
					 transition(edgeName="t016",targetState="move_to_INDOOR",cond=whenRequest("pickup_request"))
					transition(edgeName="t017",targetState="home_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("home_stopped") { //this:State
					action { //it:State
						println("TRANSPORT TROLLEY | Stopped at home")
					}
					 transition(edgeName="t118",targetState="s0",cond=whenDispatch("resume_trolley"))
				}	 
				state("move_to_INDOOR") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("pickup_request(PATH_TO_INDOOR,MATERIAL)"), Term.createTerm("pickup_request(PATH_TO_INDOOR,MATERIAL)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										 		PathIndoor = payloadArg(0)
										 		Material = payloadArg(1)
						}
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY | execute the path $PathIndoor to INDOOR.")
						request("dopath", "dopath($PathIndoor)" ,"pathexec" )  
						 Status = "WORKING"  
						 Position = "GENERIC"  
						 LastState = "to_indoor"  
					}
					 transition(edgeName="t119",targetState="pickup_action",cond=whenReply("dopathdone"))
					transition(edgeName="t120",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t121",targetState="handle_path",cond=whenDispatch("stop_trolley"))
				}	 
				state("pickup_action") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(3000) 
								unibo.kotlin.planner22Util.updateMapWithPath( PathIndoor  )
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
								println("TRANSPORT TROLLEY | arrived to INDOOR.")
								 Position = "INDOOR"  
								answer("pickup_request", "pickup_done", "pickup_done(DONE)"   )  
								println("TRANSPORT TROLLEY | pick up done.")
						}
					}
					 transition(edgeName="t222",targetState="move_to_Container",cond=whenRequest("storage_request"))
					transition(edgeName="t223",targetState="indoor_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("indoor_stopped") { //this:State
					action { //it:State
						println("TRANSPORT TROLLEY | Stopped at Indoor")
					}
					 transition(edgeName="t324",targetState="pickup_action",cond=whenDispatch("resume_trolley"))
				}	 
				state("pathfail") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY| Error: Path fail!")
					}
				}	 
				state("move_to_Container") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("storage_request(PATH_TO_CONTAINER)"), Term.createTerm("storage_request(PATH_TO_CONTAINER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										 		PathContainer = payloadArg(0)
						}
						println("TRANSPORT TROLLEY | execute the path $PathContainer to $Material container ")
						 Status = "WORKING"  
						request("dopath", "dopath($PathContainer)" ,"pathexec" )  
						 LastState = "to_container"  
					}
					 transition(edgeName="t425",targetState="settle_action",cond=whenReply("dopathdone"))
					transition(edgeName="t426",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t427",targetState="handle_path",cond=whenDispatch("stop_trolley"))
				}	 
				state("settle_action") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(3000) 
								unibo.kotlin.planner22Util.updateMapWithPath( PathContainer  )
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
								println("TRANSPORT TROLLEY | arrived to $Material container")
								if(  Material.equals("plastic") 
								 ){ Position = "CONTAINERP"  
								}
								else
								 { Position = "CONTAINERG"  
								 }
								 Status = "IDLE"  
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
								delay(1500) 
								println("TRANSPORT TROLLEY | settled $Material on the Container.")
								answer("storage_request", "storage_done", "storage_done(DONE)"   )  
						}
					}
					 transition(edgeName="t528",targetState="move_to_INDOOR",cond=whenRequest("pickup_request"))
					transition(edgeName="t529",targetState="move_to_HOME",cond=whenRequest("home_request"))
					transition(edgeName="t530",targetState="container_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("container_stopped") { //this:State
					action { //it:State
						println("TRANSPORT TROLLEY | Stopped at container")
					}
					 transition(edgeName="t631",targetState="settle_action",cond=whenDispatch("resume_trolley"))
				}	 
				state("move_to_HOME") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY | coming back to HOME ")
						if( checkMsgContent( Term.createTerm("home_request(PATH_TO_HOME)"), Term.createTerm("home_request(PATH_TO_HOME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												PathHome = payloadArg(0)
								request("dopath", "dopath($PathHome)" ,"pathexec" )  
								delay(1500) 
						}
						 LastState = "to_home"  
					}
					 transition(edgeName="t532",targetState="back_to_home",cond=whenReply("dopathdone"))
					transition(edgeName="t533",targetState="pathfail",cond=whenReply("dopathfail"))
					transition(edgeName="t534",targetState="handle_path",cond=whenDispatch("stop_trolley"))
				}	 
				state("back_to_home") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived HOME.")
								 Position = "HOME"  
								unibo.kotlin.planner22Util.showCurrentRobotState(  )
								answer("home_request", "home_done", "home_done(DONE)"   )  
						}
					}
					 transition(edgeName="t635",targetState="move_to_INDOOR",cond=whenRequest("pickup_request"))
				}	 
				state("handle_path") { //this:State
					action { //it:State
						println("TRANSPORT TROLLEY | Trolley stopped by the sonar, stop the pathexec..")
						emit("alarm", "alarm(STOP)" ) 
					}
					 transition(edgeName="t736",targetState="trolley_stopped",cond=whenReply("dopathdone"))
					transition(edgeName="t737",targetState="savepath",cond=whenReply("dopathfail"))
				}	 
				state("savepath") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												PathToDo = payloadArg(0);
						}
						println("TRANSPORT TROLLEY | SAVE PATH: $PathToDo")
					}
					 transition( edgeName="goto",targetState="trolley_stopped", cond=doswitch() )
				}	 
				state("trolley_stopped") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY | STOPPED")
					}
					 transition(edgeName="t738",targetState="handle_resume",cond=whenDispatch("resume_trolley"))
				}	 
				state("handle_resume") { //this:State
					action { //it:State
						println("TRANSPORT TROLLEY | resume trolley after sonar resume message.")
					}
					 transition( edgeName="goto",targetState="trolley_resumed", cond=doswitch() )
				}	 
				state("trolley_resumed") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY | RESUME")
						println("TRASNSPORT TROLLEY | Last state of trolley: $LastState")
						request("dopath", "dopath($PathToDo)" ,"pathexec" )  
						 PathToDo = ""  
					}
					 transition(edgeName="t839",targetState="pickup_action",cond=whenReplyGuarded("dopathdone",{ LastState == "to_indoor"  
					}))
					transition(edgeName="t840",targetState="settle_action",cond=whenReplyGuarded("dopathdone",{ LastState == "to_container"  
					}))
					transition(edgeName="t841",targetState="back_to_home",cond=whenReplyGuarded("dopathdone",{ LastState == "to_home"  
					}))
					transition(edgeName="t842",targetState="pathfail",cond=whenReply("dopathfail"))
				}	 
			}
		}
}
