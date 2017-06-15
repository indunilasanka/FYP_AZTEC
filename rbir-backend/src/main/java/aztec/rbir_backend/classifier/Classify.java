package aztec.rbir_backend.classifier;

import aztec.rbir_backend.globals.Global;

/**
 * Created by subhahs on 15/06/2017.
 */
public class Classify {
    public static String classify(String filePath){
        String category = Classifier.getCategory(filePath);
        if(!Global.getHashtableFiles().containsKey(filePath))
            Global.getHashtableFiles().put(filePath,category);
        return "success";
    }
}
