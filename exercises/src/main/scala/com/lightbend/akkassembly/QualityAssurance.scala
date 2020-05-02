package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow

class QualityAssurance {

  /** Decider to use in the inspect flow
   *  If a car fails the inspect flow must continue, the failed cars is dropped */
  val inspectDecider: Supervision.Decider = {
    case _: QualityAssurance.CarFailedInspection => Supervision.Resume
    case _ => Supervision.Stop
  }

  /** Flow that inspects an unfinished car and produces a car */
  val inspect: Flow[UnfinishedCar, Car, NotUsed] =
    Flow[UnfinishedCar]
    .collect {
      case unfinishedCar if meetsSpecifications(unfinishedCar)=>
        Car(SerialNumber(), unfinishedCar.color.get, unfinishedCar.engine.get, unfinishedCar.wheels, None)
      case unfinishedCar =>
        throw new QualityAssurance.CarFailedInspection(unfinishedCar)
    }
    .withAttributes(ActorAttributes.supervisionStrategy(inspectDecider))

  /** Checks if an unfinished car meets the specifications of a complete car */
  def meetsSpecifications(car: UnfinishedCar): Boolean = {
    if (car.engine.isDefined && car.color.isDefined && car.wheels.size == WheelShop.NumberOfWheelsPerCar) true
    else false
  }

}

object QualityAssurance {

  /** Custom exception to be thrown when a the inspection of car fails */
  class CarFailedInspection(car: UnfinishedCar) extends IllegalStateException(s"Car $car failed inspection")

}
