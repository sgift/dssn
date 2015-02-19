package models.database

import java.sql.Timestamp
import play.api.db.slick.Config.driver.simple._

import scala.slick.lifted.Tag

case class MailToken(
  uuid: String,
  email: String,
  creationTime: Timestamp,
  expirationTime: Timestamp,
  isSignUp: Boolean
)

class MailTokenTable(tag: Tag) extends Table[MailToken](tag, "PLAYER_MAIL_TOKEN") {
  def uuid = column[String]("PLAYER_MAIL_TOKEN_ID", O.PrimaryKey)
  def email = column[String]("EMAIL", O.NotNull)
  def creationTime = column[Timestamp]("CREATION_TIME", O.NotNull)
  def expirationTime = column[Timestamp]("EXPIRATION_TIME", O.NotNull)
  def isSignUp = column[Boolean]("IS_SIGN_UP", O.NotNull)

  override def * = (uuid, email, creationTime, expirationTime, isSignUp) <> (MailToken.tupled, MailToken.unapply)
}
