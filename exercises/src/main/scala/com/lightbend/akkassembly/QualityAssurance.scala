package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Flow

class QualityAssurance {

  /** Flow that inspects an unfinished car and produces a car */
  val inspect: Flow[UnfinishedCar, Car, NotUsed] =
    Flow[UnfinishedCar]
    .collect {
      case unfCar if unfCar.engine.isDefined && unfCar.color.isDefined && unfCar.wheels.size == WheelShop.NumberOfWheelsPerCar =>
        Car(SerialNumber(), unfCar.color.get, unfCar.engine.get, unfCar.wheels, None)
    }

}
