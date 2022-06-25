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
		 
				var MAXPB = 500L;
				var MAXGB = 500L;
				var CurrentPB = 0L;
				var CurrentGB = 0L;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the WasteService is waiting..")
					}
					 transition(edgeName="t02",targetState="handle_request",cond=whenRequest("waste_request"))
				}	 
				state("handle_request") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), Term.createTerm("waste_request(MATERIAL,TRUCKLOAD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var Material 	= payloadArg(0);
												var TruckLoad 	= payloadArg(1).toLong();
								println("WasteService | arrived request: $Material load with weight $TruckLoad")
								if(  Material.equals("plastic")  
								 ){if(  TruckLoad + CurrentPB <= MAXPB  
								 ){answer("waste_request", "loadaccept", "loadaccept(plastic,$TruckLoad)"   )  
								 CurrentPB += TruckLoad  
								println("WasteService | current plastic weight: $CurrentPB")
								forward("execute", "execute(plastic)" ,"transporttrolley" ) 
								}
								else
								 {answer("waste_request", "loadrejected", "loadrejected(plastic,$TruckLoad)"   )  
								 }
								}
								else
								 {if(  TruckLoad + CurrentGB <= MAXGB  
								  ){answer("waste_request", "loadaccept", "loadaccept(glass,$TruckLoad)"   )  
								  CurrentGB += TruckLoad  
								 println("WasteService | current glass weight: $CurrentGB")
								 forward("execute", "execute(glass)" ,"transporttrolley" ) 
								 }
								 else
								  {answer("waste_request", "loadrejected", "loadrejected(glass,$TruckLoad)"   )  
								  }
								 }
								emit("containers_weight", "containers_weight($CurrentGB,$CurrentPB)" ) 
						}
					}
					 transition(edgeName="t13",targetState="handle_led",cond=whenEvent("trolley_status"))
				}	 
				state("handle_led") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("trolley_status(TROLLEY_STATUS)"), Term.createTerm("trolley_status(TROLLEY_STATUS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Trolley_Status = payloadArg(0)  
								println("WasteService | Received new trolley_status: $Trolley_Status")
								if(  Trolley_Status.equals("IDLE")  
								 ){ var Led_Status = "LED_OFF"  
								emit("change_led", "change_led($Led_Status)" ) 
								}
								if(  Trolley_Status.equals("MOVING")  
								 ){ var Led_Status = "LED_BLINKS"  
								emit("change_led", "change_led($Led_Status)" ) 
								}
								if(  Trolley_Status.equals("STOPPED")  
								 ){ var Led_Status = "LED_ON"  
								emit("change_led", "change_led($Led_Status)" ) 
								}
						}
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
