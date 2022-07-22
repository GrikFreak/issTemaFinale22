//package rx
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.IApplMessage
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/*
-------------------------------------------------------------------------------------------------
 
-------------------------------------------------------------------------------------------------
 */

class sonarSimulator ( name : String ) : ActorBasic( name ) {
	var goon = true
	val data = sequence<Int>{
		var v0 = 80
		yield(v0)
		/* while(true){
			v0 = v0 - 5
			yield( v0 )
		} */
	}

    override suspend fun actorBody(msg : IApplMessage){
  		//println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
		if( msg.msgId() == "sonaractivate") startDataReadSimulation(   )
		if( msg.msgId() == "sonardeactivate") goon=false
	}

	suspend fun startDataReadSimulation(    ){
  			var i = 0
			var myRandomValues = 0
			delay ( 2000 )
			while( i < 100 && goon ){

				myRandomValues = Random.nextInt(1, 110)

 	 			val m1 = "distance( $myRandomValues )"
				i++
 				val event = MsgUtil.buildEvent( name,"sonar",m1)								
  				emitLocalStreamEvent( event )
				if(myRandomValues <= 20) delay ( 3000 )
 				//println("$tt $name | generates $event")
 				//emit(event)  //APPROPRIATE ONLY IF NOT INCLUDED IN A PIPE
 				delay( 500 )
  			}			
			terminate()
	}

} 

