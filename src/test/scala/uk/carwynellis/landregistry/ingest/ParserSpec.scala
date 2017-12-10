package uk.carwynellis.landregistry.ingest

import org.scalatest.{Matchers, WordSpec}
import uk.carwynellis.landregistry.fixtures.PricePaidEntryFixtures

class ParserSpec extends WordSpec with Matchers {

  "Parser" should {

    "return a parsed line for a valid data file" in {
      import PricePaidEntryFixtures.SingleLine._

      val underTest = Parser(resourcePath)

      underTest.next() shouldBe Some(entry)

      underTest.close()
    }

    "batch input data correctly" in {

      import PricePaidEntryFixtures.MultiLine._

      val underTest = Parser(resourcePath)

      // Input file has 10 lines so, if batch size is 4 successive calls to
      // nextBatch should yield batche sizes of 4, 4 and 2.
      val batchSize = 4

      val batch1 = underTest.nextBatch(batchSize)
      batch1.length shouldBe 4
      batch1.map(_.id.toInt) shouldBe (0 until 4)

      val batch2 = underTest.nextBatch(batchSize)
      batch2.length shouldBe 4
      batch2.map(_.id.toInt) shouldBe (4 until 8)

      val batch3 = underTest.nextBatch(batchSize)
      batch3.length shouldBe 2
      batch3.map(_.id.toInt) shouldBe Seq(8, 9)

      underTest.close()

    }

  }

}
