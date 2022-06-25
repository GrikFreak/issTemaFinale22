/* Generated by AN DISI Unibo */ 
package it.unibo.sonar

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonar ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var DLIMIT = 30L;
				var Stopped = false;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the sonar is active..")
						stateTimer = TimerActor("timer_s0", 
							scope, context!!, "local_tout_sonar_s0", 1.toLong() )
					}
					 transition(edgeName="t08",targetState="detect",cond=whenTimeout("local_tout_sonar_s0"))   
				}	 
				state("detect") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						 val Distance = kotlin.random.Random.nextLong(10,100)  
						if(  Distance <= DLIMIT && !Stopped  
						 ){forward("stop", "stop(Distance)" ,"transporttrolley" ) 
						 Stopped = true  
						}
						if(  Distance > DLIMIT && Stopped  
						 ){forward("resume", "resume(Distance)" ,"transporttrolley" ) 
						 Stopped = false  
						}
					}
				}	 
			}
		}
}
