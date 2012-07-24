name := "learning"

version := "1.0.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.liftweb"   %% "lift-json" % "2.4",
  "org.specs2"    %% "specs2"    % "1.11" % "test",
  "org.scalatest" %% "scalatest" % "1.8"  % "test",
  "junit"          % "junit"     % "4.7"  % "test"
)
