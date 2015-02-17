package models

import securesocial.core.{AuthenticationMethod, BasicProfile}
import securesocial.core.providers.{UsernamePasswordProvider, MailToken}
import service.User

import scala.slick.lifted.{Query, TableQuery}

object Player {
  var players = TableQuery[PlayersTable]

  def getUser(userId: String): Option[User] = {
    players.filter(_.id == userId)
  }

  def getUserByEMail(email: String): Option[User] = {
    None
  }

  def saveUser(user: User): Option[BasicProfile] = {
    None
  }

  def saveMailToken(token: MailToken) : MailToken = None

  def getMailToken(uuid: String): Option[MailToken] = None

  def deleteMailToken(uuid: String) : Option[MailToken] = None

  def deleteExpiredMailTokens() = Unit
}
