package models

import models.database.{Player, PlayerTable}
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import securesocial.core.{AuthenticationMethod, BasicProfile, PasswordInfo}
import service.User

object Players {
  val players = TableQuery[PlayerTable]

  def getUser(userId: String): Option[User] = {
    getUserByCondition(players.filter(_.id === userId))
  }

  def getUserByEMail(email: String): Option[User] = {
    getUserByCondition(players.filter(_.email === email))
  }

  def getUserByCondition(condition: Query[PlayerTable, PlayerTable#TableElementType, Seq]) : Option[User] = {
    DB.withSession{
      implicit session => condition.firstOption match {
        case Some(result) => Some(new User(BasicProfile("", result.id, None, None, None, Some(result.email), None, AuthenticationMethod.UserPassword, passwordInfo = Some(PasswordInfo(result.hasher, result.password, result.salt)))))
        case None => None
      }
    }
  }

  def saveUser(user: User): Option[BasicProfile] = {
    DB.withSession{
      implicit session => players += Player(user.main.userId, user.main.email.get, user.main.passwordInfo.get.password, user.main.passwordInfo.get.hasher, user.main.passwordInfo.get.salt)
    }

    Some(user.main)
  }
}
