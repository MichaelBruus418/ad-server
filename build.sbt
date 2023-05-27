name := """ad-server"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10" // 2.13.10


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test


libraryDependencies += "com.typesafe.play" %% "routes-compiler" % "2.8.18"


// --- Database ---
libraryDependencies += jdbc
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.32"

// --- Slick ---
// Essentials
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0" // Don't update
// Extras
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.2" // Don't update
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.3.2" // Don't update
// libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2" // Don't update
// libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.5" // NO


// --- Archive ---
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
