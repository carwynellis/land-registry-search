package uk.carwynellis.landregistry

import uk.carwynellis.landregistry.ingest.{IngestClient, Parser}
import uk.carwynellis.landregistry.model.PricePaidEntry

import scala.annotation.tailrec
import uk.carwynellis.landregistry.serialization.JsonSerialization

/**
  * Initial Ingest runner.
  *
  * Just prints parsed rows for now.
  */
object Ingest {

  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) => ingestFile(filename)
      case _               => println(s"A single filename must be specified")
    }
  }

  private def ingestFile(path: String): Unit = {

    println(s"Ingesting file: $path")

    val client = new IngestClient

    def store(entry: PricePaidEntry): Unit = {
      import JsonSerialization.toJson
      val result = client.indexJson(entry.id, toJson(entry))
      println(s"Got ingest result: $result")
    }

    val parser = Parser(path)

    @tailrec
    def loop(): Unit = parser.next() match {
      case Some(entry) =>
        store(entry)
        loop()
      case None =>
        parser.close()
        println(s"Finished")
    }



    loop()

  }

}
