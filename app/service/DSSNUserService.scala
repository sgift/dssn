package service

import models.Player
import play.api.Logger
import securesocial.core._
import securesocial.core.providers.MailToken
import scala.concurrent.Future
import securesocial.core.services.{ UserService, SaveMode }

class DSSNUserService extends UserService[User] {
  val logger = Logger("application.controllers.InMemoryUserService")

  def find(providerId: String, userId: String): Future[Option[BasicProfile]] = {
    val result: Option[BasicProfile] = Player.getUser(userId) match {
      case None   => None
      case player => Some(player.get.main)
    }

    Future.successful(result)
  }

  def findByEmailAndProvider(email: String, providerId: String): Future[Option[BasicProfile]] = {
    val result: Option[BasicProfile] = Player.getUserByEMail(email) match {
      case None   => None
      case player => Some(player.get.main)
    }

    Future.successful(result)
  }

  def save(user: BasicProfile, mode: SaveMode): Future[User] = {
    val newUser = new User(user)
    mode match {
      case SaveMode.SignUp =>
        Player.saveUser(newUser)
        Future.successful(newUser)
      //Nothing to do, we only support one profile, so just note that he's logged in now and be finished ... maybe we should log something here?
      case SaveMode.LoggedIn => Future.successful(newUser)
      case SaveMode.PasswordChange => {
        updatePasswordInfo(newUser, user.passwordInfo.get)
        Future.successful(newUser)
      }
    }
  }

  def saveToken(token: MailToken): Future[MailToken] = {
    Future.successful(Player.saveMailToken(token))
  }

  def findToken(token: String): Future[Option[MailToken]] = {
    Future.successful(Player.getMailToken(token))
  }

  def deleteToken(uuid: String): Future[Option[MailToken]] = {
    Future.successful(Player.deleteMailToken(uuid))
  }

  def deleteExpiredTokens() {
    Player.deleteExpiredMailTokens()
  }

  override def updatePasswordInfo(user: User, info: PasswordInfo): Future[Option[BasicProfile]] = {
    val updatedUser = new User(user.main.copy(passwordInfo = Some(info)))
    Future.successful(Player.saveUser(updatedUser))
  }

  override def passwordInfoFor(user: User): Future[Option[PasswordInfo]] = {
    Future.successful {
      Player.getUser(user.main.userId) match {
        case None => None
        case Some(result) => Some(result).get.main.passwordInfo
      }
    }
  }

  override def link(current: User, to: BasicProfile): Future[User] = {
    Future.successful(current)
  }
}
