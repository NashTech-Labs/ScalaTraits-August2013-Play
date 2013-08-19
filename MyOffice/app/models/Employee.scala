package models

import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.Play.current
case class Employee(id: Long, name: String)

object Employee {

  val employee = {
    get[Long]("id") ~
      get[String]("name") map {
        case id ~ name =>
          Employee(id, name)
      }
  }

  /**
   *  All Employees In Office
   */
  def allEmployees(): List[Employee] = DB.withConnection { implicit c =>
    SQL("select * from employee").as(employee *)
  }

  /**
   * Adding A New Employee
   */
  def newEmployee(nameOfEmployee: String) {
    DB.withConnection { implicit c =>
      SQL("insert into employee (name) values ({name})").on(
        'name -> nameOfEmployee).executeUpdate()
    }
  }

  /**
   * Delete Employee
   * @param id  : id Of The Employee To Be Deleted
   */
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from employee where id = {id}").on(
        'id -> id).executeUpdate()
    }
  }

}