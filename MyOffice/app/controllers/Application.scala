package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
   * Calling web services
   */

  def jobs = Action {
    WS.url("http://www.scalajobz.com/getAllJobs").get().map {
      response => println(response.json)
    }
    Ok("Jobs Fetched")
  }

  /**
   * XML Demo
   */
  def sayHello = Action { request =>
    request.body.asXml.map { xml =>
      (xml \\ "name" headOption).map(_.text).map { name =>
        Ok("Hello " + name + "   ")
      }.getOrElse {
        BadRequest("Missing parameter [name]")
      }
    }.getOrElse {
      BadRequest("Expecting Xml data")
    }
  }

  /**
   * Json Demo
   */
  def greet = Action { request =>
    request.body.asJson.map { json =>
      (json \ "name").asOpt[String].map { name =>
        Ok("Hello " + name + " ")
      }.getOrElse {
        BadRequest("Missing parameter [name]")
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
}