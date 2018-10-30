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

      var video: JsValue = Json.parse("null")
      val videoData = Json.parse(Source.fromURL(
        s"https://api.themoviedb.org/3/movie/$id/videos?api_key=487ca347f667dcb85f52d03ca278aded&language=en-UK"
      ).mkString)
      (videoData \\ "key").length match {
        case x if x > 0 => video = (videoData \\ "key").head
        case _ => video
      }

      var map = scala.collection.immutable.Seq((movieData \\ "title").head, (movieData \\ "overview").head,
        (movieData \\ "poster_path").head, (movieData \\ "release_date").head, (movieData \\ "runtime").head,
        video, (movieData \\ "backdrop_path").head)
      (movieData \ "genres" \\ "name").foreach(x => map = map :+ x)
      Json.toJson(map).toString()
    } catch {
      case _: Throwable => Json.toJson("No Movie").toString()
    }
  }
}