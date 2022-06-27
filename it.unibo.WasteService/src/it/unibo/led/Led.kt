/* Generated by AN DISI Unibo */ 
package it.unibo.led

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Led_Status	= "LED_OFF";
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("the Led is ready..")
					}
					 transition(edgeName="t010",targetState="change_status",cond=whenEvent("change_led"))
				}	 
				state("change_status") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("change_led(LED_STATUS)"), Term.createTerm("change_led(LED_STATUS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Led_Status = payloadArg(0)  
								println("Led | led status changed in: $Led_Status")
								emit("led_status", "led_status($Led_Status)" ) 
						}
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
