package org.example.ktz.server.http.module

import com.example.ktz.ExampleClient
import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule

object ProvidedModule extends TwitterModule {
  @Provides @Singleton
  def provideExampleThriftClient: ExampleClient = new ExampleClient("httpClient")
}
