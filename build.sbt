name := "learning"

version := "1.0.0"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-unchecked", "-deprecation")

net.virtualvoid.sbt.graph.Plugin.graphSettings

libraryDependencies ++= Seq(
  "joda-time"                 % "joda-time"                % "2.1",
  "org.joda"                  % "joda-convert"             % "1.2",
  "org.scribe"                % "scribe"                   % "1.2.1",
  "org.seleniumhq.selenium"   % "selenium-java"            % "2.25.0",
  "org.seleniumhq.selenium"   % "selenium-htmlunit-driver" % "2.25.0",
  "org.apache.httpcomponents" % "httpclient"               % "4.2.1" ,
  "net.liftweb"              %% "lift-json"                % "2.4",
  "net.liftweb"              %% "lift-json-ext"            % "2.4",
  "org.scalatra"              % "scalatra"                 % "2.1.0",
  "org.eclipse.jetty"         % "jetty-webapp"             % "8.1.0.RC5",
  "org.scalatra"              % "scalatra-scalatest"       % "2.1.0"    % "test",
  "org.specs2"               %% "specs2"                   % "1.11"     % "test",
  "org.scalatest"            %% "scalatest"                % "1.8"      % "test",
  "org.hamcrest"              % "hamcrest-all"             % "1.3"      % "test",
  "junit"                     % "junit"                    % "4.7"      % "test",
  "ch.qos.logback"            % "logback-classic"          % "1.0.0"    % "runtime",
  "org.eclipse.jetty.orbit"   % "javax.servlet"            % "3.0.0.v201112011016" artifacts (
    Artifact("javax.servlet", "jar", "jar")
  )
)
