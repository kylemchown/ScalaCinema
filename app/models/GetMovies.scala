package models

import play.api.libs.json.{JsValue, Json}
import scala.io.Source

/**
  * Get movie details depending on the ID passed in
  *
  * @author: Nelvin
  */
object GetMovies {
  def movieDetails(id: String): String = {
    try {
      val movieData = Json.parse(Source.fromURL(
        s"https://api.themoviedb.org/3/movie/$id?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK"
      ).mkString)
      val videoData = Json.parse(Source.fromURL(
        s"https://api.themoviedb.org/3/movie/$id/videos?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK"
      ).mkString)

      val title = (movieData \\ "title").head
      val genre = (movieData \ "genres" \\ "name")
      val overview = (movieData \\ "overview").head
      val poster = (movieData \\ "poster_path").head
      val release = (movieData \\ "release_date").head
      val runtime = (movieData \\ "runtime").head
      val backdrop = (movieData \\ "backdrop_path").head
      val video = (videoData \\ "key").head

      var map = scala.collection.immutable.Seq(title, overview, poster, release, runtime, video, backdrop)
      genre.foreach(x => map = map :+ x)
      Json.toJson(map).toString()
    } catch {
      case _: Throwable => Json.toJson("No Movie").toString()
    }
  }


}
