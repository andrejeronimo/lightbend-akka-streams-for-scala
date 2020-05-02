package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.FlowShape
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge}

class UpgradeShop {

  /** Flow to install upgrades on an unfinished car
   *  It should install the upgrade types (DX, Sport and Standard) on a balanced way */
  val installUpgrades: Flow[UnfinishedCar, UnfinishedCar, NotUsed] =
    Flow.fromGraph(
      GraphDSL.create() {
        implicit builder =>
          import GraphDSL.Implicits._

          val balance = builder.add(Balance[UnfinishedCar](3))
          val merge = builder.add(Merge[UnfinishedCar](3))

          val upgradeDX = Flow[UnfinishedCar].map(_.installUpgrade(Upgrade.DX))
          val upgradeSport = Flow[UnfinishedCar].map(_.installUpgrade(Upgrade.Sport))
          val upgradeStandard = Flow[UnfinishedCar]

          balance ~> upgradeDX       ~> merge
          balance ~> upgradeSport    ~> merge
          balance ~> upgradeStandard ~> merge

          FlowShape(balance.in, merge.out)
      }
    )
}
