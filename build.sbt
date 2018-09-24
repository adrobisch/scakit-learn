name := "scakit-learn"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"

resolvers += Resolver.bintrayRepo("cibotech", "public")

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "0.13.2",

  // Native libraries are not included by default. add this if you want them (as of 0.7)
  // Native libraries greatly improve performance, but increase jar sizes.
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "0.13.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.cibo" %% "evilplot" % "0.5.0" // Use %%% instead of %% if you're using ScalaJS
)