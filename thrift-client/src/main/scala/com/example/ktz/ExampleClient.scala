package com.example.ktz

import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thrift.ClientId
import com.twitter.util.Future
import org.example.ktz.thriftscala.{ TUserCar, TUserInfo, TUserService }

/**
 * Created by ktz on 2017. 5. 25..
 */
class ExampleClient(clientId: String, host: String = "localhost", port: Int = 9090) {
  val thriftClient: TUserService[Future] = ThriftMux.client.withClientId(ClientId(clientId)).newIface[TUserService[Future]](s"$host:$port", s"$clientId")

  def getAllUserInfo(): Future[List[TUserInfo]] = thriftClient.getAllUserInfo().map(_.toList)

  def getUserInfoById(userId: Long): Future[Option[TUserInfo]] = thriftClient.getUserInfoById(userId).map(_.headOption)

  def setUserInfoById(tUserInfo: TUserInfo): Future[Option[TUserInfo]] = thriftClient.setUserInfoById(tUserInfo).map(_.headOption)

  def getCarInfoById(userId: Long): Future[Option[TUserCar]] = thriftClient.getCarInfoById(userId).map(_.headOption)

  def optionalParameterTest(pa1: Long, pa2: Option[Int] = None, pa4: Option[String] = None): Future[Int] = thriftClient.optionalParameterTest(pa1, pa2, pa4)
}
