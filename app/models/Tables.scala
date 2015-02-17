package models

import org.joda.time.DateTime
import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.{ProvenShape, Tag}

class PlayersTable(tag: Tag) extends Table[(String, String, String)](tag, "PLAYER") {
  def id = column[String]("PLAYER_ID", O.PrimaryKey)
  def email = column[String]("EMAIL", O.NotNull)
  def password = column[String]("PASS_HASH", O.NotNull)

  override def * = (id, email, password)
}

class MailTokenTable(tag: Tag) extends Table[(String, String, DateTime, DateTime, Boolean)](tag, "PLAYER_MAIL_TOKEN") {
  def uuid = column[String]("PLAYER_MAIL_TOKEN_ID", O.PrimaryKey)
  def email = column[String]("EMAIL", O.NotNull)
  def creationTime = column[DateTime]("CREATION_TIME", O.NotNull)
  def expirationTime = column[DateTime]("EXPIRATION_TIME", O.NotNull)
  def isSignUp = column[Boolean]("IS_SIGN_UP", O.NotNull)

  override def * = (uuid, email, creationTime, expirationTime, isSignUp)
}
