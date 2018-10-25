package models


import play.api.data._
import play.api.data.Forms._
import slick.jdbc.MySQLProfile.api._


case class Seat(tag: Tag) extends Table[(Int, Char, Int, Boolean)](tag, "SEAT") {

  def row = column[Int]("SEAT_ROW")
  def column1 = column[Char]("SEAT_COLUMN")
  def room = column[Int]("SEAT_ROOM")
  def isTaken = column[Boolean]("SEAT_STATUS")
  def * = (row,column1,room,isTaken)

}

