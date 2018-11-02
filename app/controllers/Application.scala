package controllers
import models.{GetMovies, GetSearch}
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.actor.Status.Success
import akka.japi.Option.Some
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import controllers.DatabaseConnection.{db, seatTable}
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}

import models.GetMovies
import play.api.libs.ws._

import play.api.mvc._
import slick.lifted.TableQuery
import models.Seat
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import scala.language.postfixOps
import scala.concurrent.Future
import scala.util.{Failure, Success}


class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment, ws: WSClient) extends Controller with I18nSupport {
  val seatTable = TableQuery[Seat]
  val db = Database.forConfig("mysqlDB")


  def showMovies = Action.async {
    ws.url("https://api.themoviedb.org/3/movie/now_playing?api_key=1c51d67c43ed71cbaa90f4a967f68650&language=en-US&page=1").get().map { response =>
      Ok(views.html.home("Home Page")(response.body))
    }
  }

  def drop = Action {
    DatabaseConnection.dropDB
    Ok("Success")
  }

  def book(seat: String) = Action {
    DatabaseConnection.bookSeat(seat.substring(0, seat.length -1).toInt, seat.charAt(seat.length-1))
    Ok("booked")
  }

  def list  = Action { implicit request =>

    Ok(views.html.bookingForm("hi"))

  }

  def lists = Action.async { implicit request =>
    val resultingUsers: Future[Seq[(Int, Char, Int, Boolean)]] = db.run(seatTable.result)
    resultingUsers.map(users => Ok(users.
      toArray
      .map { element =>
        if (!element._4) {
          element._1.toString + element._2.toString
        }
        else {
          element._1.toString + element._2.toString + "z"
        }
      }
      .mkString(",")))
  }


  def map = Action {
    Ok(views.html.map("Your new application is ready."))


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
    Thread.sleep(1000)
    Ok(views.html.searchResults("result page")(r)(name))
  }
}