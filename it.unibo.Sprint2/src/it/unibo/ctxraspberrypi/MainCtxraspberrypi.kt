/* Generated by AN DISI Unibo */ 
package it.unibo.ctxraspberrypi
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "192.168.1.131", this, "sprint2.pl", "sysRules.pl"
	)
}

