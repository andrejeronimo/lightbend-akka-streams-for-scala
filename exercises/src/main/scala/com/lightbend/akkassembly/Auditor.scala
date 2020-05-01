package com.lightbend.akkassembly

import akka.{Done, NotUsed}
import akka.event.LoggingAdapter
import akka.stream.scaladsl.{Flow, Sink}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

class Auditor {

  /** Sink to count the number of cars being produced */
  val count: Sink[Any, Future[Int]] =
    Sink.fold(0)((sum, _) => sum + 1)

  /** Sink to log all messages */
  def log(implicit logger: LoggingAdapter): Sink[Any, Future[Done]] = {
    Sink.foreach(elem => logger.debug(elem.toString))
  }

  /** Flow that takes a sample of cars within a @sampleSize period */
  def sample(sampleSize: FiniteDuration): Flow[Car, Car, NotUsed] = {
    Flow[Car]
      .takeWithin(sampleSize)
  }

}
