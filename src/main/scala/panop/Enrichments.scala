package panop

import akka.util.Timeout
import akka.actor.{ ActorRef, Props }
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

/**
 * Enrichments for application-specific Akka interactions.
 * Courtesy of CrossStream.ch
 * @author Mathieu Demarne (mathieu.demarne@gmail.com)
 */
object Enrichments {
  val defaultTimeoutDuration = Settings.timeout
  implicit val defaultTimeout = Timeout(defaultTimeoutDuration)

  implicit class RichActorRef(actorRef: ActorRef) {
    /** Send a message and retrieve the answer in a blocking manner,
     * using default timeout. */
    def !?(mess: Any) = {
      val resultProm = actorRef ? mess
      Await.result(resultProm, defaultTimeoutDuration)
    }
  }
}
