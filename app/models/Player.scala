package models

import securesocial.core.{PasswordInfo, AuthenticationMethod, BasicProfile}
import securesocial.core.providers.MailToken
import service.User

import play.api.Play.current
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._

object Player {
  val players = TableQuery[PlayersTable]

  def getUser(userId: String): Option[User] = {
    DB.withSession{
      implicit session => players.filter(_.id === userId).firstOption match {
        case Some(result) => Some(new User(BasicProfile("", result._1, None, None, None, Some(result._2), None, AuthenticationMethod.UserPassword, passwordInfo = Some(PasswordInfo(result._3, result._4, Some(result._5))))))
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
