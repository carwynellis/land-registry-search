package uk.carwynellis.landregistry

import uk.carwynellis.landregistry.ingest.{IngestClient, Parser}
import uk.carwynellis.landregistry.model.PricePaidEntry
import uk.carwynellis.landregistry.serialization.JsonSerialization

import scala.annotation.tailrec

/**
  * Initial Ingest runner.
  */
object Ingest {

  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) => ingestFile(filename)
      case _               => println(s"A single filename must be specified")
    }
  }

  private def ingestFile(path: String): Unit = {

    // TODO - make this configurable
    val BatchSize = 1000

    println(s"Ingesting file: $path")

    val client = new IngestClient

    def store(entries: Seq[PricePaidEntry]): Unit = {
      import IngestClient.BulkItem
      import JsonSerialization.toJson
      val jsonBatch = entries.map(e => BulkItem(e.id, toJson(e)))
      // TODO - handle failed inserts...
      val response = client.indexBulk(jsonBatch)

      if (response.hasFailures) {
        println(s"Batch of ${entries.size} had some failures")
      }
      else {
        println(s"Indexed ${entries.size} entries successfully")
      }
    }

    val parser = Parser(path)

    @tailrec
    def loop(): Unit = parser.nextBatch(BatchSize) match {
      case Nil =>
        parser.close()
        println(s"Finished")
      case entries =>
        store(entries)
        loop()
    }

    loop()

  }

}
