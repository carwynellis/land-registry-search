package uk.carwynellis.landregistry.ingest

import com.github.tototoshi.csv.CSVReader
import uk.carwynellis.landregistry.model.{Address, PricePaidEntry}

/**
  * Initial attempt at a CSV parser for the Land Reg file format.
  *
  * For now just takes a file path.
  */
class Parser(filePath: String) {

  val reader = CSVReader.open(filePath)

  def next(): Option[PricePaidEntry] = reader.readNext().map(convertValues)

  // TODO - conversion of list of values to instance might be nicer with shapeless...
  private def convertValues(values: List[String]) = values match {
    case List(id, price, timestamp, postcode, propertyType, newBuild, tenure, line1, line2, street, locality, town, district, county, _*) =>
      PricePaidEntry(
        id           = id,
        timestamp    = timestamp,
        price        = price,
        propertyType = propertyType,
        newBuild     = newBuild,
        tenure       = tenure,
        address      = Address(
          line1    = line1,
          line2    = line2,
          street   = street,
          locality = locality,
          town     = town,
          district = district,
          county   = county,
          postcode = postcode
        )
      )
  }

}

object Parser {

  def apply(filePath: String) = new Parser(filePath)

}
