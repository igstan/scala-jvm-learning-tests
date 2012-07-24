name := "learning"

version := "1.0.0"

scalaVersion := "2.9.1"

classpathTypes ~= (_ + "orbit")

resolvers += "Sonatype" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies ++= Seq(
  "org.scalatra"      % "scalatra"           % "2.1.0.M2",
  "org.scalatra"      % "scalatra-scalatest" % "2.1.0.M2" % "test",
  "ch.qos.logback"    % "logback-classic"    % "1.0.0"    % "runtime",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" artifacts (
    Artifact("javax.servlet", "jar", "jar")
  ),
  "org.eclipse.jetty" % "jetty-webapp"       % "8.1.0.RC5",
  "net.liftweb"      %% "lift-json"          % "2.4",
  "org.specs2"       %% "specs2"             % "1.11"     % "test",
  "org.scalatest"    %% "scalatest"          % "1.8"      % "test",
  "junit"             % "junit"              % "4.7"      % "test"
)
