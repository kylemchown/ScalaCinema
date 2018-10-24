package models

import play.api.libs.json.{JsValue, Json}
import scala.io.Source

object GetMovies {

  def main(args: Array[String]): Unit = {
    println(movieDetails("335983"))
  }

  def nowRunning(): Unit = {
    val html = Source.fromURL(
      "https://api.themoviedb.org/3/movie/now_playing?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK&page=1")
    val s: JsValue = Json.parse(html.mkString)
    println(s \\ "title")
  }

  def movieDetails(id: String): String = {
    val movieData = Json.parse(Source.fromURL(
      s"https://api.themoviedb.org/3/movie/$id?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK"
    ).mkString)
    val videoData = Json.parse(Source.fromURL(
      s"https://api.themoviedb.org/3/movie/$id/videos?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK"
    ).mkString)


    //    println(movieData)
    println((movieData \\ "title") (0))
    val title = (movieData \\ "title").head
    println(movieData \ "genres" \\ "name")
    val genre = (movieData \ "genres" \\ "name")
    println(movieData \\ "overview")
    val overview = (movieData \\ "overview").head
    println(movieData \\ "poster_path")
    val poster = (movieData \\ "poster_path").head
    println(movieData \\ "release_date")
    val release = (movieData \\ "release_date").head
    println(movieData \\ "runtime")
    val runtime = (movieData \\ "runtime").head

    println((videoData \\ "key"))
    val video = (videoData \\ "key").head
    println(video)

    //    val map = Map("title" -> title, "genre" -> genre, "overview" -> overview,
    //      "poster" -> poster, "release" -> release, "runtime" -> runtime, "video" -> video)

    var map = scala.collection.immutable.Seq(title, overview, poster, release, runtime, video, genre.head)
    //    val map = (title, overview)
    if (genre.length > 1) map += title
    Json.toJson(map).toString()
  }

}
