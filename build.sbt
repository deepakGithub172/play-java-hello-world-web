name := """play-java-hello-world-web"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "com.couchbase.client" % "java-client" % "2.7.0"
libraryDependencies ++= Seq(
  javaWs  withSources()
)
