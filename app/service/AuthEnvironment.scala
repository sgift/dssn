package service

import securesocial.core.RuntimeEnvironment
import securesocial.core.providers.UsernamePasswordProvider
import securesocial.core.services.UserService

import scala.collection.immutable.ListMap

//Based on the securesocial example
class AuthEnvironment extends RuntimeEnvironment.Default[User] {
  override val userService: UserService[User] = new DSSNUserService()
  override lazy val providers = ListMap(
    include(new UsernamePasswordProvider[User](userService, avatarService, viewTemplates, passwordHashers))
  )
}