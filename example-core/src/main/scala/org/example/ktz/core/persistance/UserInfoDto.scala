package org.example.ktz.core.persistance

case class UserInfoDto(userId: Long, userName: String, userAge: Int, sex: Boolean, carName: UserCarDto, motherName: Option[String])

case class UserCarDto(carName: String, serialNumber: Long)