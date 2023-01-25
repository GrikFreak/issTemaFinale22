/* Generated by AN DISI Unibo */ 
package it.unibo.distancefilter

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Distancefilter ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val DistanceLimit = 20
				var Stopped = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("DistanceFilter STARTS")
					}
					 transition(edgeName="t13",targetState="handleSonarEvent",cond=whenEvent("sonardata"))
				}	 
				state("handleSonarEvent") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("distance(V)"), Term.createTerm("distance(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												var Distance = payloadArg(0).toInt()
								println("emitted distance: $Distance")
								if(  Distance < DistanceLimit && !Stopped  
								 ){ Stopped=!Stopped  
								forward("stop", "stop($Distance)" ,"wasteservice" ) 
								}
								else
								 {if(  Distance >= DistanceLimit && Stopped  
								  ){ Stopped=!Stopped  
								 forward("resume", "resume($Distance)" ,"wasteservice" ) 
								 }
								 }
						}
					}
					 transition(edgeName="t24",targetState="handleSonarEvent",cond=whenEvent("sonardata"))
				}	 
			}
		}
}
