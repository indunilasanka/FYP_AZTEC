package aztec.rbir_backend;

import aztec.rbir_backend.configurations.ElasticSearchClient;
import aztec.rbir_backend.logic.DocumentSeeker;
import aztec.rbir_backend.logic.FileReaderAndIndexer;
import aztec.rbir_backend.logic.FileReaderFactory;
import aztec.rbir_backend.logic.MyFileReader;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//           FileReaderAndIndexer fri = new FileReaderAndIndexer();
//           fri.indexDocument("aztec","E://Exercise-03.docx");
        DocumentSeeker seeker = new DocumentSeeker();
        String[] keys = {"ns",
                "time"};
        seeker.searchDocument("aztec",keys);
    }
}
