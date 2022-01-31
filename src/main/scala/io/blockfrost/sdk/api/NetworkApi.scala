package io.blockfrost.sdk.api

import io.blockfrost.sdk.ApiClient
import io.blockfrost.sdk.api.NetworkApi.NetworkInfo
import io.blockfrost.sdk.common.{Config, SttpSupport}
import io.circe._
import io.circe.generic.auto._
import sttp.client3.UriContext

trait NetworkApi[F[_], P] extends SttpSupport {
  this: ApiClient[F, P] =>

  def getNetworkInformation(implicit config: Config): F[ApiResponse[NetworkInfo]]
}

trait NetworkApiImpl[F[_], P] extends NetworkApi[F, P] {
  this: ApiClient[F, P] =>

  override def getNetworkInformation(implicit config: Config): F[ApiResponse[NetworkInfo]] =
    get(uri"$host/network")
}

object NetworkApi {
  case class NetworkInfo(supply: Supply, stake: Stake)
  case class Stake(live: String, active: String)
  case class Supply(max: String, total: String, circulating: String, locked: String)
}
