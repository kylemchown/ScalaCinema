package controllers

import play.api._
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.duration._

import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
//import spray.json._
import play.api.libs.json._

class Application @Inject() (ws: WSClient) extends Controller {


  def showMovies = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.home("Home Page")(Json.parse(response.body)))
    }
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }




}