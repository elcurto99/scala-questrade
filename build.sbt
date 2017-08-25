name := "scalaquestrade"

version := "0.1.4"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.http4s" %% "http4s-core" % "0.17.0-M3",
  "org.http4s" %% "http4s-dsl" % "0.17.0-M3",
  "org.http4s" %% "http4s-json4s-jackson" % "0.17.0-M3",
  "org.json4s" %% "json4s-ext" % "3.5.2",
  "org.slf4j" % "slf4j-simple" % "1.7.25" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.typesafe" % "config" % "1.2.1"
)


pomExtra :=
  <url>https://github.com/elcurto99/scala-questrade</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:elcurto99/scala-questrade.git</url>
      <connection>scm:git:git@github.com:elcurto99/scala-questrade.git</connection>
    </scm>
    <developers>
      <developer>
        <id>elcurto99</id>
        <name>Curtis Muller</name>
      </developer>
    </developers>