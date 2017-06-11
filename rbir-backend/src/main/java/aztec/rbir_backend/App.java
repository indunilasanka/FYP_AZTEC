package aztec.rbir_backend;

import aztec.rbir_backend.crawler.URLParser;
import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.indexer.Index;
import aztec.rbir_backend.indexer.Indexer;
import aztec.rbir_backend.indexer.Terms;
import aztec.rbir_backend.logic.DocumentSeeker;
import aztec.rbir_backend.logic.FileReaderAndIndexer;
import aztec.rbir_backend.queryprocess.Searcher;
import io.swagger.models.auth.In;

import java.io.*;
import java.util.*;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("In shutdown hook");
                Global.writeToFile();
            }
        }, "Shutdown-thread"));
//           FileReaderAndIndexer fri = new FileReaderAndIndexer();
//           fri.indexDocument("aztec","E://Application for TV.pdf");
  /*      DocumentSeeker seeker = new DocumentSeeker();
        String[] keys = {"ns",
                "time"};
        seeker.searchDocument("aztec",keys);*/
        Global global = new Global();
//       Indexer.indexFile("E:/tested.txt");
//        Indexer.indexFile("E:/R.txt");
        Set<String> set = Searcher.searchPQ("documents1 will gives you");
        System.out.println(set);

    }
}
