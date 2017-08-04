package aztec.rbir_backend.indexer;


import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.classifier.Learner;
import aztec.rbir_backend.logic.WordFrequency;

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

        // call the below method to expand the training model
        calculateMaxFreqWords(content);
        //

        Boolean result = Index.index(content,filePath, Global.getHashtableIndex());
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> test = Global.getHashtableIndex();
        if(result)
            return "success";
        return "fail";
    }

    public static void calculateMaxFreqWords(String content)
    {
        WordFrequency maxFrequency = new WordFrequency();
        Learner learner = new Learner();

        String[] wordsArray = new String[10];
        wordsArray = maxFrequency.getMostFrequentWords(content,10);
        learner.writeToArffFile(wordsArray);
    }
}
