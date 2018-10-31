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


  def list  = Action.async { implicit request =>

    DatabaseConnection.listSeats.map(result => Ok(views.html.bookingForm(result, SeatForm.createSeatForm)))


  }


  def bookSeat = Action { implicit request =>

    val formValidationResult = SeatForm.createSeatForm.bindFromRequest
    println(formValidationResult)

    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.bookingForm(Seq((0,'z',5,false)),formWithErrors))
    }, { seat =>

      DatabaseConnection.bookSeat(seat.seat.substring(0, seat.seat.length -1).toInt, seat.seat.charAt(seat.seat.length-1))
      Redirect(routes.Application.list())
    })
  }

}