package uk.carwynellis.landregistry.ingest

import org.scalatest.{Matchers, WordSpec}

class ParserTest extends WordSpec with Matchers {

  "Parser" should {

    "return a parsed line for a valid data file" in new SpecContext {
      val underTest = Parser(SingleLine)

      // TODO - match on parsed result
      underTest.next() shouldBe defined
    }
  }

  trait SpecContext {

    // TODO - define resource path properly
    val SingleLine = getResourcePath("/csv-fixtures/single-line.csv")

    private def getResourcePath(path: String) = {
      val r = getClass.getResource(path)
      r.getPath
    }
  }

}
