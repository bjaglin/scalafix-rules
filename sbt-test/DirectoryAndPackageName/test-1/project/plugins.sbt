addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1")
resolvers += Resolver.sonatypeRepo("snapshots")
dependencyOverrides += "ch.epfl.scala" % "scalafix-interfaces" % " 0.11.1+150-c1d8a345-SNAPSHOT"