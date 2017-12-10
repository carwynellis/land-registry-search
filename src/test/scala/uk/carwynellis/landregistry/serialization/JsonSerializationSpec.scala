package uk.carwynellis.landregistry.serialization

import org.scalatest.{Matchers, WordSpec}
import uk.carwynellis.landregistry.fixtures.PricePaidEntryFixtures

class JsonSerializationSpec extends WordSpec with Matchers {

  "JsonSerialization" should {

    "serialize and deserialize a PricePaidEntry" in {

      val entry = PricePaidEntryFixtures.SingleLine.entry

      val json = JsonSerialization.toJson(entry)

      val deserialized = JsonSerialization.fromJson(json)

      deserialized shouldBe Right(entry)

    }

  }

}
