package org.example.ktz.core.persistance

/**
 * Created by ktz on 2016. 12. 5..
 */
case class FakeDatabase(var userInfo: Map[Long, UserInfoDto]) {
  def set(tUserInfo: UserInfoDto): Option[UserInfoDto] = userInfo.get(tUserInfo.userId) match {
    case Some(_) =>
      userInfo.filter(_._1 != tUserInfo.userId) ++ Map(tUserInfo.userId -> tUserInfo)
      Some(tUserInfo)
    case None =>
      None
  }
}
