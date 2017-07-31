package org.example.ktz.server.thrift.controller

import com.google.inject.Inject
import com.twitter.finagle.Service
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future
import org.example.ktz.thriftscala.TUserService.{ GetAllUserInfo, GetCarInfoById, GetUserInfoById, OptionalParameterTest, SetUserInfoById }
import org.example.ktz.thriftscala.{ TUserCar, TUserInfo, TUserService }
import org.example.ktz.core.persistance.FakeDatabase
import org.example.ktz.core._

/**
 * Created by ktz on 2016. 12. 5..
 */
class ExampleController @Inject() (fakeDatabase: FakeDatabase) extends Controller with TUserService.BaseServiceIface {
  override val getAllUserInfo: Service[GetAllUserInfo.Args, Seq[TUserInfo]] = handle(GetAllUserInfo) { _ =>
    Future.value(fakeDatabase.userInfo.toList.map(_._2.toTUserInfo))
  }

  override val getUserInfoById: Service[GetUserInfoById.Args, Seq[TUserInfo]] = handle(GetUserInfoById) { args =>
    Future.value(fakeDatabase.userInfo.get(args.userId).toSeq.map(_.toTUserInfo))
  }

  override val setUserInfoById: Service[SetUserInfoById.Args, Seq[TUserInfo]] = handle(SetUserInfoById) { args =>
    Future.value(fakeDatabase.set(args.userInfoToSet.toUserInfo).toSeq.map(_.toTUserInfo))
  }

  override val getCarInfoById: Service[GetCarInfoById.Args, Seq[TUserCar]] = handle(GetCarInfoById) { args =>
    Future.value(fakeDatabase.userInfo.get(args.userId).map(_.carName).toSeq.map(_.toTUserCar))
  }

  override val optionalParameterTest: Service[OptionalParameterTest.Args, Int] = handle(OptionalParameterTest) { args =>
    Future.value((args.pa2.isDefined, args.pa4.isDefined) match {
      case (true, true) => 3
      case (true, false) => 2
      case (false, true) => 2
      case (false, false) => 1
    })
  }
}