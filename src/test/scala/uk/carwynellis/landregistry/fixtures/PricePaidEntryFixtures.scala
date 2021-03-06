package uk.carwynellis.landregistry.fixtures

import uk.carwynellis.landregistry.model.{Address, PricePaidEntry}

sealed trait PricePaidEntryFixture {
  val resourcePath: String
}

trait PricePaidEntryFixtures {

  object SingleLine extends PricePaidEntryFixture {

    override val resourcePath = getResourcePath("/csv-fixtures/single-line.csv")

    val entry = PricePaidEntry(
      id = "SOME_UNIQUE_ID",
      price = "100000",
      timestamp = "2017-01-01 00:00",
      propertyType = "D",
      newBuild = "N",
      tenure = "F",
      address = Address(
        line1 = "1",
        line2 = "",
        street = "TEST STREET",
        locality = "",
        town = "SOME TOWN",
        district = "SOME DISTRICT",
        county = "SOME COUNTY",
        postcode = "AB12 3CD"
      )
    )
  }

  object MultiLine extends PricePaidEntryFixture {

    override val resourcePath = getResourcePath("/csv-fixtures/multi-line.csv")

  }

  private def getResourcePath(path: String) = {
    val r = getClass.getResource(path)
    r.getPath
  }

}

object PricePaidEntryFixtures extends PricePaidEntryFixtures
