package aztec.rbir_backend.document;

import aztec.rbir_backend.configurations.ElasticSearchClient;
import aztec.rbir_backend.indexer.Terms;
import org.dom4j.swing.DocumentTreeModel;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static aztec.rbir_backend.indexer.Terms.getTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * Created by subhahs on 28/05/2017.
 */
public class Document {
    private static  TransportClient client = ElasticSearchClient.getClient();

    public static BulkProcessor getBulkProcessor(){
        TransportClient client = ElasticSearchClient.getClient();

        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {}

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                        System.out.println("Last Execution Id : " + executionId);
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {}
                })
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        return bulkProcessor;
    }

    public static IndexResponse create(Map document, String category){
        IndexResponse response = client.prepareIndex(category,"document").setSource(document).get();
        return response;
    }

    public static UpdateResponse update(String category, String id){
        UpdateResponse response = null;
        try {
            response  = client.prepareUpdate(category,"document",id).setDoc(XContentFactory.jsonBuilder().startObject().field("category",category).endObject()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static DeleteResponse delete(String category, String id){
        DeleteResponse response = client.prepareDelete(category,"document",id).get();
        return response;
    }

    public static Set<SearchHit> freeTextSearch(String query){
        //GetResponse response = client.prepareGet(category,"document",id).get();
        String preproceQuery = Terms.getTermsQuery(query);
        String[] terms = preproceQuery.split(" ");

        SearchResponse response = client.prepareSearch("security_level_1","security_level_2")
                .setTypes("document")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termsQuery("content",terms))                 // Query
                .get();

        Set<SearchHit> result = new HashSet<SearchHit>();
        for (SearchHit hit : response.getHits()) {
            result.add(hit);
        }

        return result;
    }

    public static Set<SearchHit> phraseTextSearch(String query){
        String preproceQuery = Terms.getTermsQuery(query);

        SearchResponse response = client.prepareSearch("security_level_1","security_level_2")
                .setTypes("document")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhraseQuery("content",preproceQuery))                 // Query
                .get();

        Set<SearchHit> result = new HashSet<SearchHit>();
        for (SearchHit hit : response.getHits()) {
            result.add(hit);
        }

        return result;
    }

}
