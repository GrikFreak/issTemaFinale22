/* Generated by AN DISI Unibo */ 
package it.unibo.ctxwasteservice
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "192.168.1.114", this, "sonarqak22.pl", "sysRules.pl","ctxwasteservice"
	)
}

