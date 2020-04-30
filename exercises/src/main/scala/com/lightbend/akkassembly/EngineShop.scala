package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Source

class EngineShop(shipmentSize: Int) {

  val shipments: Source[Shipment, NotUsed] =
    Source.cycle(() =>
      Iterator.continually(
        Shipment(collection.immutable.Seq.fill(shipmentSize)(Engine(SerialNumber())))
      )
    )

}
