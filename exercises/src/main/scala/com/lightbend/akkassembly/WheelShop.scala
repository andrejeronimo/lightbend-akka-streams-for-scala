package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

class WheelShop {

  /** Source to produce wheels */
  val wheels: Source[Wheel, NotUsed] =
    Source.repeat(Wheel())

  /** Flow to install wheels on an unfinished car */
  val installWheels: Flow[UnfinishedCar, UnfinishedCar, NotUsed] =
    Flow[UnfinishedCar]
    .zip(wheels.grouped(WheelShop.NumberOfWheelsPerCar))
    .map {
      case (unfinishedCar, wheels) => unfinishedCar.installWheels(wheels)
    }

}

object WheelShop {
  val NumberOfWheelsPerCar = 4
}
