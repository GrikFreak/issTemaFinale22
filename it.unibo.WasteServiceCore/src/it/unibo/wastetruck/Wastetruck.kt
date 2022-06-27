/* Generated by AN DISI Unibo */ 
package it.unibo.wastetruck

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wastetruck ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Material 	= "";
				var TruckLoad 	= 0L;
				var Materials = arrayListOf<String>("glass", "plastic");
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the WasteTruck is waiting..")
						delay(3000) 
						 
									val Load = kotlin.random.Random.nextLong(10,100) 
									val Mat = Materials[kotlin.random.Random.nextInt(1,2)]
						request("waste_request", "waste_request($Mat,$Load)" ,"wasteservice" )  
						println("WASTETRUCK | sent request of $Mat load $Load")
					}
					 transition(edgeName="t00",targetState="accepted",cond=whenReply("loadaccept"))
					transition(edgeName="t01",targetState="rejected",cond=whenReply("loadrejected"))
				}	 
				state("accepted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("loadaccept(MATERIAL,TRUCKLOAD)"), Term.createTerm("loadaccept(MATERIAL,TRUCKLOAD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Material 	= payloadArg(0);
												TruckLoad 	= payloadArg(1).toLong();
								println("WasteTruck | accepted $Material load with weight $TruckLoad")
						}
					}
					 transition(edgeName="t12",targetState="leave_Indoor",cond=whenDispatch("free_Indoor"))
				}	 
				state("rejected") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("loadrejected(MATERIAL,TRUCKLOAD)"), Term.createTerm("loadrejected(MATERIAL,TRUCKLOAD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Material 	= payloadArg(0);
												TruckLoad 	= payloadArg(1).toLong();
								println("WasteTruck | rejected $Material load with weight $TruckLoad")
						}
					}
					 transition( edgeName="goto",targetState="leave_Indoor", cond=doswitch() )
				}	 
				state("leave_Indoor") { //this:State
					action { //it:State
						println("WASTETRUCK | WasteTruck left Indoor area.")
					}
				}	 
			}
		}
}
