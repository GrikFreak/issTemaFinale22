/* Generated by AN DISI Unibo */ 
package it.unibo.ctxWasteService
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint2.pl", "sysRules.pl"
	)
}

