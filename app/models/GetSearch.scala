package models

import play.api.data.Form
import play.api.data.Forms._

case class GetSearch(name:String)
object GetSearch {
  val createSearchForm = Form(
    mapping(
      "name"-> nonEmptyText
    )(GetSearch.apply)(GetSearch.unapply)
  )
}
