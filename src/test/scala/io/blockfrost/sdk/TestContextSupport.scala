package io.blockfrost.sdk

import io.blockfrost.sdk.common.{Config, IPFS, Mainnet, Testnet}
import org.scalatest.Assertion
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import scala.concurrent.Future
import scala.language.implicitConversions

trait TestContextSupport {
  implicit val sdkConfig: Config = Config(5, 500, 30000)
  val backend: SttpBackend[Future, Any] = AsyncHttpClientFutureBackend()
  val TestnetEnv = "Testnet"
  val MainnetEnv = "Mainnet"

  def genericTestContext[A](test: A => Future[Assertion])(implicit context: A): Future[Assertion] = test(context)

  trait MainnetApiClient extends ApiClient[Future, Any] {
    override implicit val sttpBackend: SttpBackend[Future, Any] = backend

    override implicit val apiKey: String = sys.env.getOrElse("BF_MAINNET_PROJECT_ID", throw new RuntimeException("Api key not found in environment variable BF_MAINNET_PROJECT_ID"))

    override val host: String = Mainnet.url
  }

  trait TestnetApiClient extends ApiClient[Future, Any] {
    override implicit val sttpBackend: SttpBackend[Future, Any] = backend

    override implicit val apiKey: String = sys.env.getOrElse("BF_TESTNET_PROJECT_ID", throw new RuntimeException("Api key not found in environment variable BF_TESTNET_PROJECT_ID"))

    override val host: String = Testnet.url
  }

  trait TestIpfsApiClient extends ApiClient[Future, Any] {
    override implicit val sttpBackend: SttpBackend[Future, Any] = backend

    override implicit val apiKey: String = sys.env.getOrElse("BF_IPFS_PROJECT_ID", throw new RuntimeException("Api key not found in environment variable BF_IPFS_PROJECT_ID"))

    override val host: String = IPFS.url
  }
}

object TestContextSupport {
  implicit class RichInt(n: Int) {
    def some: Option[Int] = Some(n)
  }

  implicit class RichString(s: String) {
    def some: Option[String] = Some(s)
  }
}
