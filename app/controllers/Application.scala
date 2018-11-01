package controllers

import javax.inject.Inject
import models.GetMovies
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (ws: WSClient) extends Controller {


  def showMovies = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.home("Home Page")(response.body))
    }
  }

  def movieInfo(id: String) = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.test("Home Page")(id)(response.body))
    }
  }

   def movieDetails(id: String) = Action {
    Ok(views.html.movie(GetMovies.movieDetails(id)))
  }
}