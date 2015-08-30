import org.scalatest._
import scalaj.http._
import panop._
import akka.actor._

class SlaveSpec extends FlatSpec {
  import panop.com._
  import Enrichments._

  val asys = ActorSystem.create("SlaveSpecSys")
  "A Slave" should "extract proper links and check query" in {
    val slave = asys.actorOf(Props(new Slave))
    slave !? Search(Url("https://www.google.ch/", 0), Query("Google", 0)) match {
      case res: Result =>
        println(res)
        assert(res.isPositive)
      case _ => sys.error("Wrong result type")
    }
  }
  it should "do the samething on other simpler websites" in {
    val slave = asys.actorOf(Props(new Slave))
    slave !? Search(Url("http://www.euronews.com/", 0), Query("euronews" :: Nil, 0)) match {
      case res: Result =>
        println(res)
        assert(res.isPositive)
      case _ => sys.error("Wrong result type")
    }
  }
}