name := "dssn"

version := "1.0"

lazy val `dssn` = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( cache , ws, javaEbean)

libraryDependencies += "com.typesafe.play" %% "play-mailer" % "2.4.0"

libraryDependencies += "ws.securesocial" %% "securesocial" % "master-SNAPSHOT"

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1103-jdbc41"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "0.8.1"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  