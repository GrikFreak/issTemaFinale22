/*
 -------------------------------------------------------------------------------------------------
 */

import it.unibo.kactor.ActorBasic
import org.json.JSONObject
import unibo.comm22.utils.CommSystemConfig
import java.io.File

 


object ledSupport{
	lateinit var ledKind  :  String

	fun create(configFileName: String){
 		val config = File("${configFileName}").readText(Charsets.UTF_8)
		val jsonObject   = JSONObject( config )
		ledKind        = jsonObject.getString("type") 
		println( "		--- ledtSupport | CREATING for $ledKind" )

	}

}