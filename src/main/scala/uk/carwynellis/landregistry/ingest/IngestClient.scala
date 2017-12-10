package uk.carwynellis.landregistry.ingest

import java.net.InetAddress

import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.transport.client.PreBuiltTransportClient
import uk.carwynellis.landregistry.serialization.JsonSerialization

/**
  * Client providing ingest support for elasticsearch.
  *
  * Will start with trying the java api to see how far I can go with that.
  *
  * TODO - implement bulk api support for indexing
  */
class IngestClient extends JsonSerialization {

  private val IndexName = "land-registry"
  private val TypeName  = "sold-price"

  // TODO - consider passing client to constructor to allow mocking
  private val client = new PreBuiltTransportClient(Settings.EMPTY)
    // TODO - get this from config
    .addTransportAddress(new TransportAddress(InetAddress.getLoopbackAddress, 9300))

  def indexJson(id: String, json: String): IndexResponse = {
    client.prepareIndex(IndexName, TypeName, id)
      .setSource(json, XContentType.JSON)
      .get()
  }

}
