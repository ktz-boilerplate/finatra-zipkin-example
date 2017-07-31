package com.example.ktz

import com.twitter.inject.app.TestInjector
import com.twitter.inject.{Injector, IntegrationTest}
import com.twitter.util.Await
import org.example.ktz.thriftscala.{TUserCar, TUserInfo}

/**
  * Created by ktz on 2017. 5. 25..
  */
class ExampleClientTest extends IntegrationTest{
  override protected def injector: Injector = TestInjector().create

  val exampleClient = new ExampleClient("testClient", "localhost", 9090)

  test("getAllUserInfo - Well") {
    val userInfo: List[TUserInfo] = Await.result(exampleClient.getAllUserInfo())

    (userInfo.size > 1) shouldBe true
  }

  test("getUserInfoById - Well") {
    val userInfo: Option[TUserInfo] = Await.result(exampleClient.getUserInfoById(1))

    userInfo.isDefined shouldBe true
  }

  test("setUserInfoById - Well") {
    val tUserInfo: Option[TUserInfo] = Await.result(exampleClient.setUserInfoById(TUserInfo(
      3,
      "Liam",
      40,
      true,
      TUserCar(
        "K9",
        12314
      ),
      Some("Father"))))

    tUserInfo.isDefined shouldBe true
  }

  test("getCarInfoById - Well") {
    val tUserCar: Option[TUserCar] = Await.result(exampleClient.getCarInfoById(1))

    tUserCar.isDefined shouldBe true
  }

  test("optionalParameterTest - Well") {
    val test1: Int = Await.result(exampleClient.optionalParameterTest(1L))
    val test2: Int = Await.result(exampleClient.optionalParameterTest(1L, Some(1)))
    val test3: Int = Await.result(exampleClient.optionalParameterTest(1L, Some(1), Some("1")))
    val test4: Int = Await.result(exampleClient.optionalParameterTest(1L, pa4 = Some("1")))

    (test1, test2, test3, test4) shouldBe (1, 2, 3, 2)
  }

}
