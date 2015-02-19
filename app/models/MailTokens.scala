package models

import java.sql.Timestamp

import models.database.MailTokenTable
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import securesocial.core.providers.MailToken

object MailTokens {
  val mailTokens = TableQuery[MailTokenTable]

  def saveMailToken(token: MailToken) : MailToken = {
    DB.withSession {
      implicit session => mailTokens += socialSecureTokenToDBToken(token)
    }
    token
  }

  def getMailToken(uuid: String): Option[MailToken] = {
    DB.withSession {
      implicit session => mailTokens.filter(_.uuid === uuid).firstOption match {
        case None => None
        case Some(dbToken) => Some(dbTokenToSocialSecureToken(dbToken))
      }
    }
  }

  def deleteMailToken(uuid: String) : Option[MailToken] = None

  def deleteExpiredMailTokens() = Unit

  private def socialSecureTokenToDBToken(mailToken: MailToken) : models.database.MailToken = {
    models.database.MailToken(mailToken.uuid, mailToken.email, new Timestamp(mailToken.creationTime.getMillis), new Timestamp(mailToken.expirationTime.getMillis), mailToken.isSignUp)
  }

  private def dbTokenToSocialSecureToken(dbToken: models.database.MailToken) : MailToken = {
    MailToken(dbToken.uuid, dbToken.email, new DateTime(dbToken.creationTime), new DateTime(dbToken.expirationTime), dbToken.isSignUp)
  }
}
