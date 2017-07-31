package org.example.ktz.server.http.controller

import com.example.ktz.ExampleClient
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.json.FinatraObjectMapper
import org.example.ktz.core._
import org.example.ktz.core.persistance.UserInfoDto

/**
 * Created by ktz on 2016. 12. 5..
 */
class ExampleController @Inject() (thriftClient: ExampleClient, mapper: FinatraObjectMapper) extends Controller {

  get("/users") { request: Request =>
    thriftClient.getAllUserInfo().map(_.map(_.toUserInfo))
  }

  get("/user/:userId") { request: Request =>
    thriftClient.getUserInfoById(request.getLongParam("userId"))
  }

  post("/user") { request: Request =>
    val userInfo: UserInfoDto = mapper.parse[UserInfoDto](request.contentString)
    thriftClient.setUserInfoById(userInfo.toTUserInfo)
  }

  get("/user/:userId/car") { request: Request =>
    thriftClient.getCarInfoById(request.getLongParam("userId"))
  }
}