package controllers

import models.{MailTokens, Players}
import play.api.Logger
import play.api.mvc._
import play.api.db.slick.Config.driver.simple._

object Application extends Controller {
  def index = Action {
    Logger.debug(Players.players.ddl.createStatements.mkString)
    Logger.debug(MailTokens.mailTokens.ddl.createStatements.mkString)
    Ok("It works!")
  }
}
