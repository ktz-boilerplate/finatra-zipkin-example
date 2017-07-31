package org.example.ktz.server.http.controller

import com.example.ktz.ExampleClient
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Future
import org.example.ktz.core._

/**
 * Created by ktz on 2016. 12. 5..
 */
class ExampleController @Inject() (thriftClient: ExampleClient) extends Controller {

  get("/users") { request: Request =>
    thriftClient.getAllUserInfo().map(_.map(_.toUserInfo))
  }

  get("/user/:userId") { request: Request =>
    thriftClient.getUserInfoById(request.getLongParam("userId"))
  }

  post("/user/:userId") { request: Request =>
    Future.value("")
  }

  get("/user/car/:carId") { request: Request =>
    thriftClient.getUserInfoById(request.getLongParam("userId"))
  }
}