package uk.carwynellis.landregistry.ingest

import org.scalatest.{Matchers, WordSpec}
import uk.carwynellis.landregistry.fixtures.PricePaidEntryFixtures

class ParserSpec extends WordSpec with Matchers {

  "Parser" should {

    "return a parsed line for a valid data file" in new SpecContext {
      import PricePaidEntryFixtures.SingleLine._

      val underTest = Parser(resourcePath)

      underTest.next() shouldBe Some(entry)

      underTest.close()
    }

  }

  trait SpecContext {
    // TODO - remove if not required
  }

}
