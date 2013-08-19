package controllers

import play.api.mvc.Controller
import play.api.data.Form
import play.api.data.Forms.nonEmptyText
import play.api.mvc.Action
import models.Employee
object MyOfficeController extends Controller {

  /**
   * Defining the employee form
   */
  val employeeForm = Form(
    "name" -> nonEmptyText)

  /**
   * Total Employees In Office
   */
  def employees = Action {
    Ok(views.html.employee(Employee.allEmployees(), employeeForm))
  }

  /**
   * Add A New Employee
   */
  def newEmployee = Action { implicit request =>
    employeeForm.bindFromRequest.fold(
      errors => BadRequest(views.html.employee(Employee.allEmployees(),
        employeeForm)),
      name => {
        Employee.newEmployee(name)
        Redirect(routes.MyOfficeController.employees)
      })
  }
  /**
   * Remove An Employee
   */
  def deleteEmployee(id: Long) = Action {
    Employee.delete(id)
    Redirect(routes.MyOfficeController.employees)
  }

}