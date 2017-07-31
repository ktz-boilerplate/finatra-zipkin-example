package org.example.ktz.core.module

import com.google.inject.{ Provides, Singleton }
import com.twitter.inject.{ Logging, TwitterModule }
import org.example.ktz.core.persistance.{ FakeDatabase, UserCarDto, UserInfoDto }

/**
 * Created by ktz on 2016. 12. 5..
 */
object DatabaseModule extends TwitterModule with Logging {

  @Provides
  @Singleton
  def provideDatabase: FakeDatabase = FakeDatabase(
    Map(
      1.toLong -> UserInfoDto(
        1,
        "martin",
        29,
        true,
        UserCarDto(
          "K5",
          123456
        ),
        None
      ),
      2.toLong -> UserInfoDto(
        2,
        "Lea",
        30,
        true,
        UserCarDto(
          "K3",
          34521
        ),
        Some("Mom")
      ),
      3.toLong -> UserInfoDto(
        3,
        "Liam",
        35,
        true,
        UserCarDto(
          "K9",
          12314
        ),
        Some("Father")
      )
    )
  )
}
