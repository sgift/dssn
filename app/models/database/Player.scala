package models.database

import play.api.db.slick.Config.driver.simple._

import scala.slick.lifted.Tag

case class Player(
  id: String,
  email: String,
  password: String,
  hasher: String,
  salt: Option[String]
)

class PlayerTable(tag: Tag) extends Table[Player](tag, "PLAYER") {
  def id = column[String]("PLAYER_ID", O.PrimaryKey)
  def email = column[String]("EMAIL", O.NotNull)
  def password = column[String]("PASS_HASH", O.NotNull)
  def hasher = column[String]("PASS_HASHER", O.NotNull)
  def salt = column[String]("PASS_SALT", O.Nullable)

  override def * = (id, email, password, hasher, salt.?) <> (Player.tupled, Player.unapply)
}
