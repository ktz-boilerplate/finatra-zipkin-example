import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import sbt.Keys._

import scalariform.formatter.preferences._

lazy val versions = new {
  val finatra = "2.11.0"
  val guice = "4.1.0"
  val logback = "1.2.3"
  val mockito = "1.9.5"
  val scalatest = "3.0.2"
  val scalacheck = "1.13.5"
  val specs2 = "3.7"
  val finagle = "6.45.0"
}

lazy val baseSetting = Seq(
  scalaVersion := "2.11.8",
  version := "1.0",
  libraryDependencies ++= Seq(
    "com.twitter" %% "finatra-thrift" % versions.finatra,
    "com.twitter" %% "finagle-zipkin" % versions.finagle,
    "ch.qos.logback"  % "logback-classic" % versions.logback,
    "ch.qos.logback" % "logback-classic" % versions.logback % "test",

    "com.twitter" %% "finatra-http" % versions.finatra,
    "com.twitter" %% "finatra-http" % versions.finatra % "test",
    "com.twitter" %% "inject-server" % versions.finatra % "test",
    "com.twitter" %% "inject-app" % versions.finatra % "test",
    "com.twitter" %% "inject-core" % versions.finatra % "test",
    "com.twitter" %% "inject-modules" % versions.finatra % "test",
    "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

    "com.twitter" %% "finatra-thrift" % versions.finatra % "test" classifier "tests",
    "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
    "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
    "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
    "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

    "org.mockito" % "mockito-core" % versions.mockito % "test",
    "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
    "org.scalatest" %% "scalatest" % versions.scalatest % "test",
    "org.specs2" %% "specs2" % versions.specs2 % "test"
  ),
  fork in run := true,
  fork in Test := true
)

lazy val scalariformSetting = Seq(
  scoverage.ScoverageKeys.coverageMinimum := 100,
  scoverage.ScoverageKeys.coverageFailOnMinimum := false,
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignParameters, true)
    .setPreference(CompactStringConcatenation, false)
    .setPreference(IndentPackageBlocks, true)
    .setPreference(FormatXml, true)
    .setPreference(PreserveSpaceBeforeArguments, false)
    .setPreference(DoubleIndentClassDeclaration, false)
    .setPreference(RewriteArrowSymbols, false)
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 40)
    .setPreference(SpaceBeforeColon, false)
    .setPreference(SpaceInsideBrackets, false)
    .setPreference(SpaceInsideParentheses, false)
    .setPreference(IndentSpaces, 2)
    .setPreference(IndentLocalDefs, false)
    .setPreference(SpacesWithinPatternBinders, true)
    .setPreference(SpacesAroundMultiImports, false)
)


lazy val assemblySetting = Seq(
  aggregate in assembly := false,
  assemblyMergeStrategy in assembly := {
    case "BUILD" => MergeStrategy.discard
    case "META-INF/io.netty.versions.properties" => MergeStrategy.last
    case PathList("org", "apache", "commons", "logging", xs @ _*)   => MergeStrategy.last
    case PathList("org", "apache", "thrift", xs @ _*)   => MergeStrategy.last
    case other => MergeStrategy.defaultMergeStrategy(other)
  },
  test in assembly := {},
  aggregate in assembly := false,
  assemblyOutputPath in assembly := file("target/scala-2.11/thrift-server.jar")
) ++ scalariformSettings

scalaVersion := "2.11.8"

lazy val `example-core` = (project in file("example-core")).settings(baseSetting).settings(assemblySetting).settings(
  name:= "example-core"
).dependsOn(`thrift-client`)

lazy val `thrift-server` = (project in file("thrift-server")).settings(baseSetting).settings(assemblySetting).settings(
  name:= "thrift-server"
).dependsOn(`example-core`).dependsOn(`thrift-idl`)

lazy val `http-server` = (project in file("http-server")).settings(baseSetting).settings(assemblySetting).settings(
  name:= "http-server"
).dependsOn(`example-core`)

lazy val `thrift-client` = (project in file("thrift-client")).settings(baseSetting).settings(
  name:= "thrift-client"
).dependsOn(`thrift-idl`)

lazy val `thrift-idl` = (project in file("thrift-idl")).settings(baseSetting).settings(
  name:= "thrift-idl",
  scroogeLanguages in Compile := Seq("scala"),
  scroogeThriftOutputFolder in Compile <<= (sourceManaged in Compile) (_ / ""),
  unmanagedResourceDirectories in Compile += {baseDirectory.value / "src/main/thrift"}
).disablePlugins(sbtassembly.AssemblyPlugin)