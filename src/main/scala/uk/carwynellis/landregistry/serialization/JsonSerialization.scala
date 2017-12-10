package uk.carwynellis.landregistry.serialization

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Error => CirceError}
import uk.carwynellis.landregistry.model.PricePaidEntry

/**
  * Simple wrapper around Circe asJson and decode.
  */
trait JsonSerialization {

  def toJson(entry: PricePaidEntry): String = entry.asJson.noSpaces

  def fromJson(json: String): Either[CirceError, PricePaidEntry] = decode[PricePaidEntry](json)

}

object JsonSerialization extends JsonSerialization
