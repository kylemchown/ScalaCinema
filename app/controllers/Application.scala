package controllers

import javax.inject.Inject
import models.SeatForm
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps


class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def drop = Action {
    DatabaseConnection.dropDB
    Thread.sleep(1000)
    Ok("Success")
  }

  def book(row: Int, column: Char) = Action {
    DatabaseConnection.bookSeat(row, column)
    Ok("Booking Successful")
  }

  def list() = Action.async {


    DatabaseConnection.listSeats.map(result => Ok(views.html.bookingForm(result, SeatForm.createPersonForm)))

  }

}