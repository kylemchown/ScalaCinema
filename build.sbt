name := "Cinema"
 
version := "1.0" 
      
lazy val `cinema` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( jdbc , cache , ws , specs2 % Test )
libraryDependencies ++= Seq(ws)
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      