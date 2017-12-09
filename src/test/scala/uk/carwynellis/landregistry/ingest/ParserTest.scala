package uk.carwynellis.landregistry.ingest

import org.scalatest.{Matchers, WordSpec}

class ParserTest extends WordSpec with Matchers {

  "Parser" should {

    "return a parsed line for a valid data file" in new SpecContext {
      val underTest = Parser(SingleLine)

      underTest.next() shouldBe defined
    }
  }

  trait SpecContext {

    // TODO - define resource path properly
    val SingleLine = "csv-fixtures/single-line.csv"

  }

}
