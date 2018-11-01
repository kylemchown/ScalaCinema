package controllers
import models.{GetMovies, GetSearch}
import play.api.mvc._
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.libs.ws._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.actor.Status.Success
import akka.japi.Option.Some
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
class Application @Inject() (ws: WSClient) extends Controller {


  def showMovies = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.home("Home Page")(response.body))
    }
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def movieInfo(id: String) = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.test("Home Page")(id)(response.body))
    }
  }
   def movieDetails(id: String) = Action {
    Ok(views.html.movie(GetMovies.movieDetails(id)))
  }

  def searchResult=Action(parse.form(GetSearch.createSearchForm)){request =>
   val name:String = request.body.name
    var r:String =""
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map{response =>
      r = response.body
    }
    println("r is"+r+"name "+name)
    Ok(views.html.searchResults("result page")(r)(name))
  }
}