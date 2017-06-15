package aztec.rbir_backend.indexer;


import aztec.rbir_backend.globals.Global;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Indexer {
    public static String indexFile(String filePath){
        String content = Terms.getTerms(filePath);
        if (content == "Invalid File Type")
            return content;
        System.out.println(content);
        Boolean result = Index.index(content,filePath, Global.getHashtableIndex());
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> test = Global.getHashtableIndex();
        if(result)
            return "success";
        return "fail";
    }
}
