package org.example.ktz.server.http

import java.net.InetSocketAddress

import com.twitter.app.Flag
import com.twitter.finagle.http.{ Request, Response }
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.filters._
import org.example.ktz.server.http.controller.ExampleController
import org.example.ktz.server.http.module.ProvideThriftModule

/**
 * Created by ktz on 2016. 12. 5..
 */

object FinatraExampleHttpServerMain extends FinatraExampleHttpServer

class FinatraExampleHttpServer extends HttpServer {
  override val name: String = "ktzExample"

  override val modules = Seq(ProvideThriftModule)

  override val defaultFinatraHttpPort: String = ":8080"

  override val disableAdminHttpServer = true

  override val adminPort: Flag[InetSocketAddress] =
    flag("admin.port", new InetSocketAddress(8080), "Admin Http server port")

  //admin page: http://localhost:8080/admin

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .add[ExampleController]
  }
}
