package aztec.rbir_backend.indexer;

import aztec.rbir_backend.logic.FileReaderFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by subhahs on 21/08/2017.
 */
public class IndexerLucence {
    public static String indexFile(String filePath, String category){


        final String FIELD_PATH = "path";
        final String FIELD_CONTENTS = "contents";

        try {
            Analyzer analyzer = new StandardAnalyzer();
            Directory indexDirectory = FSDirectory.open(Paths.get(category));
            IndexWriterConfig conf = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(indexDirectory, conf);

            File file = new File(filePath);
            Document document = new Document();

            String path = file.getCanonicalPath();
            document.add(new Field(FIELD_PATH, path, TextField.TYPE_STORED));

            String content = FileReaderFactory.read(path);
            document.add(new Field(FIELD_CONTENTS, content, TextField.TYPE_STORED));
        } catch (IOException e) {
            e.printStackTrace();
        }

       // if(result)
            return "success";
      //  return "fail";
    }
}
