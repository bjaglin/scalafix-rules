addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1")
resolvers += Resolver.sonatypeRepo("snapshots")
dependencyOverrides += "ch.epfl.scala" % "scalafix-interfaces" % " 0.11.1+150-c1d8a345-SNAPSHOT"

// this does not take into account custom resolvers
// https://github.com/xuwei-k/sbt-warning-diff/blob/07a7fa3/scalafix/src/main/scala/warning_diff/WarningDiffScalafixPlugin.scala#L144-L151
addSbtPlugin("com.github.xuwei-k" % "warning-diff-scalafix-plugin" % "0.2.1")
