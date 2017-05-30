package aztec.rbir_backend.document;

import aztec.rbir_backend.configurations.ElasticSearchClient;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by subhahs on 28/05/2017.
 */
public class Document {
    private static  TransportClient client = ElasticSearchClient.getClient();
    public static IndexResponse index(Map document, String company){
        IndexResponse response = client.prepareIndex(company,"document").setSource(document).get();
        return response;
    }

    public static GetResponse get(String company,String id){
        GetResponse response = client.prepareGet(company,"document",id).get();
        return response;
    }

    public static DeleteResponse delete(String company, String id){
        DeleteResponse response = client.prepareDelete(company,"document",id).get();
        return response;
    }

    public static UpdateResponse updata(String company, String id){
        UpdateResponse response = null;
        try {
           response  = client.prepareUpdate(company,"document",id).setDoc(XContentFactory.jsonBuilder().startObject().field("keys","[test]").endObject()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
