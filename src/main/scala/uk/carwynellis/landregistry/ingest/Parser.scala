package uk.carwynellis.landregistry.ingest

/**
  * Initial attempt at a CSV parser for the Land Reg file format.
  *
  * For now just takes a file path.
  */
class Parser[T](filePath: String) {

  def next(): Option[T] = Option.empty[T]

}

object Parser {

  def apply[T](filePath: String) = new Parser[T](filePath)

}
