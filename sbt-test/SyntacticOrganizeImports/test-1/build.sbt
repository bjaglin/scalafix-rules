ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % sys.props("scalafix-rules.version")
ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")
