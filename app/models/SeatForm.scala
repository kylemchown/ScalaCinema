package models

import play.api.data._
import play.api.data.Forms._

case class SeatForm(seat: String)

  object SeatForm {
    val createPersonForm = Form(
      mapping(
        "seat" -> nonEmptyText
      )(SeatForm.apply)(SeatForm.unapply)
    )

}
