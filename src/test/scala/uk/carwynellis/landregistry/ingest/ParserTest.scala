package uk.carwynellis.landregistry.ingest

import org.scalatest.{Matchers, WordSpec}
import uk.carwynellis.landregistry.fixtures.PricePaidEntryFixtures

class ParserTest extends WordSpec with Matchers {

  "Parser" should {

    "return a parsed line for a valid data file" in new SpecContext {
      import PricePaidEntryFixtures.SingleLine._

      val underTest = Parser(resourcePath)

      underTest.next() shouldBe Some(entry)
    }

  }

  trait SpecContext {
    // TODO - remove if not required
  }

}
