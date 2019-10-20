name := """url-shortener"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  ehcache,
  javaWs,
  javaJdbc,
  "mysql" % "mysql-connector-java" % "5.1.46")


