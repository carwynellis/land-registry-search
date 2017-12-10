package uk.carwynellis.landregistry.ingest

import com.github.tototoshi.csv.CSVReader
import uk.carwynellis.landregistry.model.{Address, PricePaidEntry}

import scala.annotation.tailrec

/**
  * Initial attempt at a CSV parser for the Land Reg file format.
  *
  * For now just takes a file path.
  */
class Parser(filePath: String) {

  private val reader = CSVReader.open(filePath)

  def next(): Option[PricePaidEntry] = reader.readNext().map(convertValues)

  def nextBatch(batchSize: Int): Seq[PricePaidEntry] = {

    @tailrec
    def loop(acc: Seq[PricePaidEntry]): Seq[PricePaidEntry] = {
      if (acc.size == batchSize) acc
      else next() match {
        case Some(entry) => loop(acc :+ entry)
        case None        => acc
      }
    }

    loop(Seq.empty[PricePaidEntry])
  }

  def close(): Unit = reader.close()

  // TODO - conversion of list of values to instance might be nicer with shapeless...
  private def convertValues(values: Seq[String]) = values match {
    case Seq(id, price, timestamp, postcode, propertyType, newBuild, tenure, line1, line2, street, locality, town, district, county, _*) =>
      PricePaidEntry(
        id           = parseId(id),
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

  // ID value is wrapped in {} which should be removed.
  private val bracesRegex = """[{}]""".r

  private def parseId(id: String) = {
    bracesRegex.replaceAllIn(id, "")
  }

}

object Parser {

  def apply(filePath: String) = new Parser(filePath)

}
