package com.lightbend.akkassembly

import akka.stream.Materializer
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

class Factory(bodyShop: BodyShop,
              paintShop: PaintShop,
              engineShop: EngineShop,
              wheelShop: WheelShop,
              qualityAssurance: QualityAssurance,
              upgradeShop: UpgradeShop)
             (implicit mat: Materializer) {

  /** Builds a @quantity of cars using a stream that performs all the steps of building a car
   *  and returns a sequence of the built cars (future) */
  def orderCars(quantity: Int): Future[Seq[Car]] = {
    bodyShop.cars
      .via(paintShop.paint)
      .via(engineShop.installEngine)
      .async
      .via(wheelShop.installWheels)
      .async
      .via(upgradeShop.installUpgrades)
      .via(qualityAssurance.inspect)
      .take(quantity)
      .runWith(Sink.seq)
  }

}
