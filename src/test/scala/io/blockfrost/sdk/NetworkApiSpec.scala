package io.blockfrost.sdk

import io.blockfrost.sdk.api.NetworkApi.{NetworkInfo, Stake, Supply}
import io.blockfrost.sdk.api.NetworkApiImpl
import io.blockfrost.sdk.converter.FutureResponseConverter.FutureResponseOps
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

class NetworkApiSpec extends AsyncFlatSpec with Matchers with TestContextSupport {

  "getProtocolParameters" should "return EpochProtocolParameters" in genericTestContext[TestContext] { ctx =>
    ctx.api
      .getNetworkInformation
      .extract
      .map(body => {
        body should matchPattern { case NetworkInfo(Supply("45000000000000000", _, _), Stake(_, _)) => }
        succeed
      })
  }

  trait TestContext {
    val api = new NetworkApiImpl[Future, Any] with TestApiClient
  }

  implicit val testContext: TestContext = new TestContext {}
}
