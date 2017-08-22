package aztec.rbir_backend.indexer;


import aztec.rbir_backend.clustering.DocumentsList;
import aztec.rbir_backend.globals.Global;
import org.apache.commons.lang.math.NumberUtils;
import static com.google.common.primitives.Ints.min;
import java.util.*;
import aztec.rbir_backend.classifier.*;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Indexer {
    public static String indexFile(String filePath){
        String content = Terms.getTerms(filePath);
        if (content == "Invalid File Type")
            return content;

        // call the below method to expand the training model
        //calculateTrainingWords(content);
        //

        Boolean result = Index.index(content,filePath, Global.getHashtableIndex());
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> test = Global.getHashtableIndex();
        if(result)
            return "success";
        return "fail";
    }

}
