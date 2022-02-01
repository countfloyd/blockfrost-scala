scalaVersion := "3.1.1"
organization := "com.g1.blockfrost"
name := "blockfrost-scala"
version := "1.0.0-SNAPSHOT"

val circeVersion = "0.14.1"
val json4sVersion = "4.0.4"
val sttpVersion = "3.4.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
  "com.softwaremill.sttp.client3" %% "circe" % sttpVersion,
//  ("com.softwaremill.sttp.client3" %% "json4s" % sttpVersion).cross(CrossVersion.for3Use2_13),
//  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",

  "org.scalatest" %% "scalatest" % "3.2.11" % Test,
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion % Test,
  ("com.avsystem.commons" %% "commons-core" % "2.5.1" % Test).cross(CrossVersion.for3Use2_13)
)

scalacOptions ++= Seq("-Xmax-inlines", "35")

