package aztec.rbir_backend;

import aztec.rbir_backend.logic.DocumentSeeker;
import aztec.rbir_backend.logic.FileReaderAndIndexer;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//           FileReaderAndIndexer fri = new FileReaderAndIndexer();
//           fri.indexDocument("aztec","E://Application for TV.pdf");
        DocumentSeeker seeker = new DocumentSeeker();
        String[] keys = {"ns",
                "time"};
        seeker.searchDocument("aztec",keys);
    }
}
