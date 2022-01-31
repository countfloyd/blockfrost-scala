package io.blockfrost.sdk.util

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import sttp.client3.circe.*
import sttp.client3.*

import scala.concurrent.{ExecutionContext, Future}

object FutureResponseConverter {
  implicit class FutureResponseOps[A](response: Future[Response[Either[ResponseException[String, Exception], A]]])(implicit ec: ExecutionContext) {
    def extract: Future[A] = response.flatMap {
      _.body match {
        case Right(body) => Future.successful(body)
        case Left(error) => Future.failed(error)
      }
    } recoverWith {
      case HttpError(body: String, _) =>
        parse(body).map(_.as[ApiError]) match {
          case Left(ParsingFailure(msg: String, e: Throwable)) => Future.failed(e)
          case Right(Left(x: DecodingFailure)) => Future.failed(x)
          case Right(Right(x: ApiError)) => Future.failed(ApiException(x))
        }
    }
  }

  case class ApiError(status_code: Int, error: String, message: String)
  case class ApiException(error: ApiError) extends RuntimeException(error.message)
}
