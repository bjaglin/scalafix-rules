addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1")
resolvers += Resolver.sonatypeRepo("snapshots")
dependencyOverrides += "ch.epfl.scala" % "scalafix-interfaces" % " 0.11.1+150-c1d8a345-SNAPSHOT"

addSbtPlugin("com.eed3si9n" % "sbt-projectmatrix" % "0.9.2")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.21")

addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.1")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2")

addSbtPlugin("com.github.sbt" % "sbt-release" % "1.4.0")
