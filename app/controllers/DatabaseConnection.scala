package controllers


import models.Seat
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.language.postfixOps
import scala.util.{Failure, Success}

object DatabaseConnection {

  val db = Database.forConfig("mysqlDB")

  val seatTable = TableQuery[Seat]

  val dropSeatCmd = DBIO.seq(seatTable.schema.drop)

  val initSeatsCmd = DBIO.seq(seatTable.schema.create)


  def dropDB = {
    val dropFuture = Future{db.run(dropSeatCmd)}
    dropFuture.onComplete {
      case Success(_) =>  initialiseSeats
      case Failure(error) => println("Dropping the table failed due to: " + error.getMessage)
        initialiseSeats
    }
  }

  def initialiseSeats = {
    val setupFuture =  Future {
      db.run(initSeatsCmd)
    }

    setupFuture.onComplete{
      case Success(_) => initialiseRoom
      case Failure(error) => println("Initialising the table failed due to: " + error.getMessage)
    }

  }


  def addSeat(row: Int, column: Char, room: Int, isTaken: Boolean): Unit = {
    val insertSeat = Future {

      val query = seatTable += (row, column, room, isTaken)


      db.run(query)
    }
    insertSeat.onComplete {
      case Success(_) =>
      case Failure(error) => println("Welp! Something went wrong! " + error.getMessage)
    }
  }

  def initialiseRoom: Unit = {

    val alphabet = ('a' to 'z').toArray

    for(i<- 1 to 10; j<- 0 to 9) {
      addSeat(i, alphabet(j), 3, false)
    }
  }

  def bookSeat(row: Int, column: Char) = {
    val bookFuture = Future {
      val q = for {c <- seatTable if c.row === row && c.column1 === column} yield c.isTaken
      val updateAction = q.update(true)

      db.run(updateAction)
    }
    bookFuture.onComplete {
      case Success(_) =>  db.close()
        println("Booking successful")
      case Failure(error) => println("Booking failed due to: " + error.getMessage)
    }
  }

  def listSeats = {

    db.run(seatTable.sortBy(_.row).result)

  }


}
