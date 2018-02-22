name := "Ref"

organization := "org.glavo"

version := "0.5.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.11", "2.12.4")

// https://mvnrepository.com/artifact/org.scala-lang/scala-reflect
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
