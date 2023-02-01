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
		return "wait_cmd"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var Cmd = ""
				var Blink = false
		return { //this:ActionBasciFsm
				state("wait_cmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("LED | WAIT CMD")
						println("LED | OFF")
						updateResourceRep( "LED:OFF"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="esegui_cmd",cond=whenDispatch("led_status"))
				}	 
				state("esegui_cmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("led_status(STATUS)"), Term.createTerm("led_status(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Cmd = payloadArg(0)
												println(Cmd)
												Blink = false
													
											
											if(Cmd == "BLINKS") {
												Blink = true
											}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="blink", cond=doswitchGuarded({ Blink  
					}) )
					transition( edgeName="goto",targetState="onoff", cond=doswitchGuarded({! ( Blink  
					) }) )
				}	 
				state("onoff") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="on", cond=doswitchGuarded({ Cmd  == "ON"  
					}) )
					transition( edgeName="goto",targetState="off", cond=doswitchGuarded({! ( Cmd  == "ON"  
					) }) )
				}	 
				state("on") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("LED | ON")
						updateResourceRep( "LED:ON"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t21",targetState="esegui_cmd",cond=whenDispatch("led_status"))
				}	 
				state("off") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("LED | OFF")
						updateResourceRep( "LED:OFF"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t32",targetState="esegui_cmd",cond=whenDispatch("led_status"))
				}	 
				state("blink") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						updateResourceRep( "LED:BLINK"  
						)
						
									//Runtime.getRuntime().exec("sudo bash led25GpioTurnOn.sh")
									
						delay(300) 
						
									//Runtime.getRuntime().exec("sudo bash led25GpioTurnOff.sh")
										
						println("LED | BLINKS")
						updateResourceRep( "LED:BLINKS"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		//sysaction { //it:State
				 	 		  stateTimer = TimerActor("timer_blink", 
				 	 			scope, context!!, "local_tout_led_blink", 300.toLong() )
				 	 		//}
					}	 	 
					 transition(edgeName="t13",targetState="blink",cond=whenTimeout("local_tout_led_blink"))   
					transition(edgeName="t14",targetState="esegui_cmd",cond=whenDispatch("led_status"))
				}	 
			}
		}
}
