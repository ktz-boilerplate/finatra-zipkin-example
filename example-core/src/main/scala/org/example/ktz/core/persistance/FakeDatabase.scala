package org.example.ktz.core.persistance

/**
 * Created by ktz on 2016. 12. 5..
 */
case class FakeDatabase(var userInfo: Map[Long, UserInfoDto]) {

  def set(tUserInfo: UserInfoDto): Option[UserInfoDto] = {
    userInfo = userInfo + (tUserInfo.userId -> tUserInfo)
    userInfo.get(tUserInfo.userId)
  }
}
