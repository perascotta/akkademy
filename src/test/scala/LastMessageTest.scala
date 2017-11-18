import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import com.akkademy.ex_1_1.LastMessage
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, FunSuite, Matchers}

import scala.concurrent.Await

class LastMessageTest extends FunSuite with Matchers with BeforeAndAfterEach {

  implicit val system: ActorSystem = ActorSystem()

  test("received string") {
    val value = "test"
    implicit val timeout: Timeout = Timeout(2 seconds)

    val actorRef = TestActorRef(new LastMessage)
    val future = actorRef ? value
    val result = Await.result(future, timeout.duration).asInstanceOf[String]

    val lastMessage = actorRef.underlyingActor

    assert(lastMessage.knowValueMsg.format(value) === result)
  }

  test("last received string") {
    val value1 = "test1"
    val value2 = "test2"

    val actorRef = TestActorRef(new LastMessage)
    actorRef ! value1
    actorRef ! value2

    val lastMessage = actorRef.underlyingActor

    assert(lastMessage.lastMessage.toString() === value2)
  }
}
