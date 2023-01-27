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
				var LastState = "";
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the TransportTrolley is waiting..")
					}
					 transition(edgeName="t027",targetState="move_to_INDOOR",cond=whenRequest("transfer_request"))
					transition(edgeName="t028",targetState="trolley_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("move_to_INDOOR") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("transfer_request(PATH_TO_DO)"), Term.createTerm("transfer_request(PATH_TO_INDOOR)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										 		PathIndoor = payloadArg(0)
								println("TRANSPORT TROLLEY | execute the path $PathIndoor to INDOOR.")
								request("dopath", "dopath($PathIndoor)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived to INDOOR.")
								answer("transfer_request", "transfer_done", "transfer_done(DONE)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var PathToDo = payloadArg(0)
								if(  PathToDo.length != 0  && PathToDo.length != 1  
								 ){println("TRANSPORT TROLLEY | RESUME, path to do: $PathToDo")
								request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								}
								else
								 { PathToDo = ""  
								 println("TRANSPORT TROLLEY | RESUME")
								 request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t129",targetState="move_to_INDOOR",cond=whenReply("dopathdone"))
					transition(edgeName="t130",targetState="move_to_INDOOR",cond=whenReply("dopathfail"))
					transition(edgeName="t131",targetState="handle_path",cond=whenDispatch("stop_trolley"))
					transition(edgeName="t132",targetState="pickup_action",cond=whenRequest("pickup_request"))
				}	 
				state("pickup_action") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("pickup_request(MATERIAL)"), Term.createTerm("pickup_request(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(1000) 
								answer("pickup_request", "pickup_done", "pickup_done(arg)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived to Container")
								answer("pickup_request", "pickup_done", "pickup_done(arg)"   )  
						}
					}
					 transition(edgeName="t233",targetState="move_to_CONTAINER",cond=whenRequest("transfer_request"))
					transition(edgeName="t234",targetState="trolley_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("move_to_CONTAINER") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("transfer_request(PATH_TO_DO)"), Term.createTerm("transfer_request(PATH_TO_CONTAINER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										 		PathContainer = payloadArg(0)
								println("TRANSPORT TROLLEY | execute the path $PathContainer to $Material container ")
								request("dopath", "dopath($PathContainer)" ,"pathexec" )  
								 LastState = "TO_CONTAINER"  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived to Container")
								answer("transfer_request", "transfer_done", "transfer_done(DONE)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var PathToDo = payloadArg(0)
								if(  PathToDo.length != 0  && PathToDo.length != 1  
								 ){request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								}
								else
								 { PathToDo = ""  
								 request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t335",targetState="move_to_CONTAINER",cond=whenReply("dopathdone"))
					transition(edgeName="t336",targetState="move_to_CONTAINER",cond=whenReply("dopathfail"))
					transition(edgeName="t337",targetState="handle_path",cond=whenDispatch("stop_trolley"))
					transition(edgeName="t338",targetState="storage_action",cond=whenRequest("storage_request"))
				}	 
				state("storage_action") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("storage_request(ARG)"), Term.createTerm("storage_request(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(1000) 
								answer("storage_request", "storage_done", "storage_done(arg)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived to Container")
								answer("storage_request", "storage_done", "storage_done(arg)"   )  
						}
					}
					 transition(edgeName="t439",targetState="move_to_HOME",cond=whenRequest("home_request"))
					transition(edgeName="t440",targetState="move_to_INDOOR",cond=whenRequest("transfer_request"))
					transition(edgeName="t441",targetState="trolley_stopped",cond=whenDispatch("stop_trolley"))
				}	 
				state("move_to_HOME") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("home_request(PATH_TO_HOME)"), Term.createTerm("home_request(PATH_TO_HOME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										 		PathHome = payloadArg(0)
								println("TRANSPORT TROLLEY | execute the path $PathHome to HOME.")
								request("dopath", "dopath($PathHome)" ,"pathexec" )  
						}
						if( checkMsgContent( Term.createTerm("dopathdone(ARG)"), Term.createTerm("dopathdone(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("TRANSPORT TROLLEY | arrived HOME.")
								answer("home_request", "home_done", "home_done(DONE)"   )  
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var PathToDo = payloadArg(0)
								if(  PathToDo.length != 0  && PathToDo.length != 1  
								 ){answer("home_request", "home_done", "home_done(DONE)"   )  
								}
								else
								 {request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t542",targetState="move_to_HOME",cond=whenReply("dopathdone"))
					transition(edgeName="t543",targetState="move_to_HOME",cond=whenReply("dopathfail"))
					transition(edgeName="t544",targetState="handle_path",cond=whenDispatch("stop_trolley"))
					transition(edgeName="t545",targetState="move_to_INDOOR",cond=whenRequest("transfer_request"))
				}	 
				state("handle_path") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("TRANSPORT TROLLEY | SEND ALARM TO BASICROBOt.")
						emit("alarm", "alarm(STOP)" ) 
					}
					 transition(edgeName="t746",targetState="savepath",cond=whenReply("dopathfail"))
					transition(edgeName="t747",targetState="trolley_stopped",cond=whenReply("dopathdone"))
				}	 
				state("savepath") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(PATH)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												PathToDo = payloadArg(0);
						}
					}
					 transition( edgeName="goto",targetState="trolley_stopped", cond=doswitch() )
				}	 
				state("trolley_stopped") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition(edgeName="t748",targetState="handle_resume",cond=whenDispatch("resume_trolley"))
				}	 
				state("handle_resume") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("resume_trolley(LASTSTATE)"), Term.createTerm("resume_trolley(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												LastState = payloadArg(0);
								println("TRANSPORT TROLLEY | resume_trolley, need to do: $PathToDo.")
								if(  PathToDo.equals("none")  
								 ){ PathToDo = ""  
								request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								}
								else
								 {request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								 }
						}
						if( checkMsgContent( Term.createTerm("dopathfail(ARG)"), Term.createTerm("dopathfail(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var PathToDo = payloadArg(0)
								if(  PathToDo.length != 0 && PathToDo.length != 1  
								 ){request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								}
								else
								 { PathToDo = ""  
								 request("dopath", "dopath($PathToDo)" ,"pathexec" )  
								 }
						}
					}
					 transition(edgeName="t849",targetState="move_to_INDOOR",cond=whenReplyGuarded("dopathdone",{ LastState == "TO_INDOOR"  
					}))
					transition(edgeName="t850",targetState="pickup_action",cond=whenReplyGuarded("dopathdone",{ LastState == "PICKING"  
					}))
					transition(edgeName="t851",targetState="move_to_CONTAINER",cond=whenReplyGuarded("dopathdone",{ LastState == "TO_CONTAINER"  
					}))
					transition(edgeName="t852",targetState="storage_action",cond=whenReplyGuarded("dopathdone",{ LastState == "STORAGING"  
					}))
					transition(edgeName="t853",targetState="move_to_HOME",cond=whenReplyGuarded("dopathdone",{ LastState == "TO_HOME"  
					}))
					transition(edgeName="t854",targetState="handle_resume",cond=whenReply("dopathfail"))
					transition(edgeName="t855",targetState="trolley_stopped",cond=whenDispatch("stop_trolley"))
				}	 
			}
		}
}
