package uk.carwynellis.landregistry

import org.apache.logging.log4j.scala.Logging
import uk.carwynellis.landregistry.ingest.{IngestClient, Parser}
import uk.carwynellis.landregistry.model.PricePaidEntry
import uk.carwynellis.landregistry.serialization.JsonSerialization

import scala.annotation.tailrec

/**
  * Initial Ingest runner.
  */
object Ingest extends Logging {

  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) => ingestFile(filename)
      case _               => println(s"A single filename must be specified")
    }
  }

  private def ingestFile(path: String): Unit = {

    // TODO - make this configurable
    val BatchSize = 1000

    logger.info(s"Ingesting file: $path")

    val client = new IngestClient

    def store(entries: Seq[PricePaidEntry]): Unit = {
      import IngestClient.BulkItem
      import JsonSerialization.toJson
      val jsonBatch = entries.map(e => BulkItem(e.id, toJson(e)))

      // TODO - handle failed inserts...
      val response = client.indexBulk(jsonBatch)

      if (response.hasFailures) {
       logger.error(s"Batch of ${entries.size} had some failures")
      }
      else {
        logger.info(s"Indexed ${entries.size} entries successfully")
      }
    }

    val parser = Parser(path)

    @tailrec
    def loop(): Unit = parser.nextBatch(BatchSize) match {
      case Nil =>
        parser.close()
        logger.info("File ingest completed")
      case entries =>
        store(entries)
        loop()
    }

    loop()

  }

}
