package uk.carwynellis.landregistry.ingest

import java.net.InetAddress

import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.{IndexRequestBuilder, IndexResponse}
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.transport.client.PreBuiltTransportClient
import uk.carwynellis.landregistry.ingest.IngestClient.BulkItem
import uk.carwynellis.landregistry.serialization.JsonSerialization

/**
  * Client providing ingest support for elasticsearch.
  *
  * Will start with trying the java api to see how far I can go with that.
  */
class IngestClient extends JsonSerialization {

  private val IndexName = "land-registry"
  private val TypeName  = "sold-price"

  // TODO - consider passing client to constructor to allow mocking
  private val client = new PreBuiltTransportClient(Settings.EMPTY)
    // TODO - get this from config
    .addTransportAddress(new TransportAddress(InetAddress.getLoopbackAddress, 9300))

  def indexJson(id: String, json: String): IndexResponse =
    createIndexRequest(id, json).get

  def indexBulk(data: Seq[BulkItem]): BulkResponse = {
    val requestBuilder = client.prepareBulk()

    data foreach { item =>
      requestBuilder.add(createIndexRequest(item.id, item.json))
    }

    requestBuilder.get
  }

  private def createIndexRequest(id: String, json: String): IndexRequestBuilder =
    client.prepareIndex(IndexName, TypeName, id)
      .setSource(json, XContentType.JSON)

}

object IngestClient {

  case class BulkItem(id: String, json: String)

}
