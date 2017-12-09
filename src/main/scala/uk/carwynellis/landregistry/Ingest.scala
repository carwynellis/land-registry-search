package uk.carwynellis.landregistry

import uk.carwynellis.landregistry.ingest.Parser
import uk.carwynellis.landregistry.model.PricePaidEntry

import scala.annotation.tailrec

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

    def store(entry: PricePaidEntry): Unit = {
      println(s"Processing: $entry")
    }

    loop()

  }

}
