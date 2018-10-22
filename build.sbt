// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbt.Keys.resolvers
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

val sharedSettings = Seq(
  name := "scakit-learn-core",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.6",
  libraryDependencies ++= Seq(
    "com.cibo" %%% "evilplot" % "0.5.0",
    "org.scalatest" %%% "scalatest" % "3.0.5" % Test // Use %%% instead of %% if you're using ScalaJS
  ),
  resolvers += Resolver.bintrayRepo("cibotech", "public")
)

val jvmSettings = Seq(
  name := "scakit-learn-jvm",
  libraryDependencies  ++= Seq(
    "org.scalanlp" %% "breeze" % "0.13.2",

    // Native libraries are not included by default. add this if you want them (as of 0.7)
    // Native libraries greatly improve performance, but increase jar sizes.
    // It also packages various blas implementations, which have licenses that may or may not
    // be compatible with the Apache License. No GPL code, as best I know.
    "org.scalanlp" %% "breeze-natives" % "0.13.2",
  )
)

val jsSettings = Seq(
  name := "scakit-learn-js"
)

lazy val scakit =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full)
    .settings(sharedSettings)
    .jsSettings(jsSettings)
    .jvmSettings(jvmSettings)

lazy val scakitJS = scakit.js
lazy val scakitJVM = scakit.jvm

