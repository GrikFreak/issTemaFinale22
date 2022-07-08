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
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
				var MAXPB 	= 500L;
				var MAXGB 	= 500L;
				var CurrentPB = 0L;
				var CurrentGB = 0L;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the WasteService is waiting..")
					}
					 transition(edgeName="t03",targetState="handle_request",cond=whenRequest("waste_request"))
				}	 
				state("handle_request") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var Material 	= payloadArg(0);
												var TruckLoad 	= payloadArg(1).toLong();
								println("WASTESERVICE | arrived request: $Material load with weight $TruckLoad")
								if(  Material.equals("plastic")  
								 ){if(  TruckLoad + CurrentPB <= MAXPB  
								 ){answer("waste_request", "loadaccept", "loadaccept($Material,$TruckLoad)"   )  
								 CurrentPB += TruckLoad  
								println("WASTESERVICE | current plastic weight: $CurrentPB")
								request("pickup_request", "pickup_request($Material,$TruckLoad)" ,"transporttrolley" )  
								println("WASTESERVICE | Sent pickup request to TransportTrolley")
								}
								else
								 {answer("waste_request", "loadrejected", "loadrejected($Material,$TruckLoad)"   )  
								 }
								}
								else
								 {if(  TruckLoad + CurrentGB <= MAXGB  
								  ){answer("waste_request", "loadaccept", "loadaccept($Material,$TruckLoad)"   )  
								  CurrentGB += TruckLoad  
								 println("WASTESERVICE | current glass weight: $CurrentGB")
								 request("pickup_request", "pickup_request($Material,$TruckLoad)" ,"transporttrolley" )  
								 println("WASTESERVICE | Said to TransportTroleey to execute")
								 }
								 else
								  {answer("waste_request", "loadrejected", "loadrejected($Material,$TruckLoad)"   )  
								  }
								 }
								emit("containers_weight", "containers_weight(CurrentGB,CurrentPB)" ) 
						}
					}
					 transition(edgeName="t14",targetState="pickup_Done",cond=whenReply("pickup_done"))
				}	 
				state("pickup_Done") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("free_Indoor", "free_Indoor(FREE)" ,"wastetruck" ) 
						println("WASTESERVICE | Sent message to WasteTruck to leave indoor area.")
					}
					 transition(edgeName="t25",targetState="withdrawal_Done",cond=whenDispatch("withdrawal_done"))
				}	 
				state("withdrawal_Done") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("WASTESERVICE | Handle queue if it is.")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
