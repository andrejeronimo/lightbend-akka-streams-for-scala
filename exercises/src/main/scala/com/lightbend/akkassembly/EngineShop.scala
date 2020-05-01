package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

class EngineShop(shipmentSize: Int) {

  /** Source to produce shipments of engines, each shipment has @shipmentSize number of engines */
  val shipments: Source[Shipment, NotUsed] =
    Source.cycle(() =>
      Iterator.continually(
        Shipment(collection.immutable.Seq.fill(shipmentSize)(Engine(SerialNumber())))
      )
    )

  /** Source of engines produced from the @shipments source */
  val engines: Source[Engine, NotUsed] =
    shipments
    .mapConcat(_.engines)

  /** Flow to install an engine on an unfinished car */
  val installEngine: Flow[UnfinishedCar, UnfinishedCar, NotUsed] =
    Flow[UnfinishedCar]
    .zip(engines)
    .map {
      case (unfinishedCar, engine) => unfinishedCar.installEngine(engine)
    }

}
