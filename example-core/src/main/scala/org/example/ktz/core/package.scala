package org.example.ktz

import org.example.ktz.core.persistance.{ UserCarDto, UserInfoDto }
import org.example.ktz.thriftscala.{ TUserCar, TUserInfo }

package object core {

  implicit class RichUserInfoDto(userInfoDto: UserInfoDto) {
    def toTUserInfo: TUserInfo = TUserInfo(
      userInfoDto.userId,
      userInfoDto.userName,
      userInfoDto.userAge,
      userInfoDto.sex,
      userInfoDto.carName.toTUserCar,
      userInfoDto.motherName
    )
  }

  implicit class RichTUserInfoDto(userInfoDto: TUserInfo) {
    def toUserInfo: UserInfoDto = UserInfoDto(
      userInfoDto.userId,
      userInfoDto.userName,
      userInfoDto.userAge,
      userInfoDto.sex,
      userInfoDto.carName.toUserCar,
      userInfoDto.mothersName
    )
  }

  implicit class RicUserCarDto(userCar: UserCarDto) {
    def toTUserCar: TUserCar = TUserCar(
      userCar.carName,
      userCar.serialNumber
    )
  }

  implicit class RicTUserCarDto(userCar: TUserCar) {
    def toUserCar: UserCarDto = UserCarDto(
      userCar.carName,
      userCar.serialNumber
    )
  }
}
