package com.akkademy.ex_1_1

import akka.actor.Actor
import akka.event.Logging

class LastMessage extends Actor {

  val lastMessage: StringBuilder = StringBuilder.newBuilder

  val knowValueMsg = "received string: %s"
  val unknowValueMsg = "received unknow object: %s"

  override def receive: PartialFunction[Any, Unit] = {
    case s: String =>
      lastMessage.setLength(0)
      lastMessage.append(s)
      sender ! knowValueMsg.format(s)
    case u =>
      sender ! unknowValueMsg.format(u)
  }
}
