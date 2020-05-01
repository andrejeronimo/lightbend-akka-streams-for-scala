package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

class PaintShop(colorSet: Set[Color]) {

  /** Source to produce colors in cycle from a @colorSet */
  val colors: Source[Color, NotUsed] =
    Source.cycle(() => colorSet.iterator)

  /** Flow to paint an unfinished car */
  val paint: Flow[UnfinishedCar, UnfinishedCar, NotUsed] =
    Flow[UnfinishedCar]
    .zip(colors)
    .map {
      case (unfinishedCar, color) => unfinishedCar.paint(color)
    }

}
