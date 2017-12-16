name := "land-registry-search"

version := "0.1"

scalaVersion := "2.12.4"

val circeVersion = "0.8.0"
val log4jVersion = "2.10.0"

libraryDependencies ++= Seq(
  // Try Java client for now - could also use elastic4s
  "org.elasticsearch.client" %  "transport"       % "6.0.1",
  "org.apache.logging.log4j" %  "log4j-api"       % log4jVersion,
  "org.apache.logging.log4j" %  "log4j-core"      % log4jVersion,
  "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0",
  "com.github.tototoshi"     %% "scala-csv"       % "1.3.5",
  "io.circe"                 %% "circe-core"      % circeVersion,
  "io.circe"                 %% "circe-generic"   % circeVersion,
  "io.circe"                 %% "circe-parser"    % circeVersion,
  "org.scalatest"            %% "scalatest"       % "3.0.4" % "test"
)

scalacOptions ++= Seq(
  "-target:jvm-1.8",        // Target Java 8
  "-explaintypes",          // Explain type errors with more detail
  "-deprecation",           // Emit deprecation warnings
  "-feature",               // Emit warnings where feature needs explicit import
  "-unchecked",             // Emit warnings related to type erasure
  "-Ywarn-unused:imports",  // Warn on unused imports
  "-Xfatal-warnings"        // Make warnings fatal
)

// Filter options that don't play well with the scala console.
// See https://tpolecat.github.io/2017/04/25/scalac-flags.html
scalacOptions in (Compile, console) ~= (_.filterNot(Set(
  "-Ywarn-unused:imports",
  "-Xfatal-warnings"
)))

