/*
 -------------------------------------------------------------------------------------------------
 */

import it.unibo.kactor.ActorBasic
import org.json.JSONObject
import unibo.comm22.utils.CommSystemConfig
import java.io.File

 


object sonarSupport{
	var simulate  :  Boolean = false

	fun create(configFileName: String){
 		val config = File("${configFileName}").readText(Charsets.UTF_8)
		val jsonObject   = JSONObject( config )
		simulate        = jsonObject.getBoolean("simulate") 
		println( "		--- sonarSupport | CREATING for $simulate" )

	}

}