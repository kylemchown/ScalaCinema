name := "Cinema"
 
version := "1.0" 
      
lazy val `cinema` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test,
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "mysql" % "mysql-connector-java" % "8.0.12",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      