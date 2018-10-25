package controllers

import controllers.DatabaseConnection.db
import javax.inject.Inject
import models.Seat
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.{Failure, Success}


class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def drop = Action {
    DatabaseConnection.dropDB
    Ok("Success")
  }

  def book(row: Int, column: Char) = Action {
    DatabaseConnection.bookSeat(row, column)
    Ok("Booking Successful")
  }

  def list() = Action.async {

    DatabaseConnection.listSeats.map(result => Ok(views.html.bookingForm(result)))

  }

}