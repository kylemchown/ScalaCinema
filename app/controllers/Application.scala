package controllers

import controllers.DatabaseConnection.{db, seatTable}
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import slick.lifted.TableQuery
import models.Seat
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.concurrent.Future
import scala.language.postfixOps
import scala.language.postfixOps
import scala.util.{Failure, Success}


class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {
  val seatTable = TableQuery[Seat]
  val db = Database.forConfig("mysqlDB")

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
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



}