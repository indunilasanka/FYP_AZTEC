package aztec.rbir_backend.logic;

import aztec.rbir_backend.configurations.ElasticSearchClient;
import org.apache.commons.vfs2.cache.OnCallRefreshFileObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsLookupQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * Created by subhahs on 29/05/2017.
 */
public class DocumentSeeker {
    public void searchDocument(String company,String[] keys){
        TransportClient client = ElasticSearchClient.getClient();
        QueryBuilder qb = termsQuery(
                "keys",
                keys
        );

        SearchResponse response = client.prepareSearch(company).setTypes("document").setQuery(qb).get();

        System.out.println(response.toString());

    }
}
