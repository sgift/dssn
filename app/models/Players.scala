package models

import models.database.PlayerTable
import securesocial.core.{PasswordInfo, AuthenticationMethod, BasicProfile}
import securesocial.core.providers.MailToken
import service.User

import play.api.Play.current
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._

object Players {
  val players = TableQuery[PlayerTable]

  def getUser(userId: String): Option[User] = {
    DB.withSession{
      implicit session => players.filter(_.id === userId).firstOption match {
        case Some(result) => Some(new User(BasicProfile("", result.id, None, None, None, Some(result.email), None, AuthenticationMethod.UserPassword, passwordInfo = Some(PasswordInfo(result.hasher, result.password, result.salt)))))
        case None => None
      }
    }
  }

  def getUserByEMail(email: String): Option[User] = {
    None
  }

  def saveUser(user: User): Option[BasicProfile] = {
    None
  }

  def saveMailToken(token: MailToken) : MailToken = { token }

  def getMailToken(uuid: String): Option[MailToken] = None

  def deleteMailToken(uuid: String) : Option[MailToken] = None

  def deleteExpiredMailTokens() = Unit
}
