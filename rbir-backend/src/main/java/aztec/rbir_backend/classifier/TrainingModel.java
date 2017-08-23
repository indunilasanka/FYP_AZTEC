package aztec.rbir_backend.classifier;

import aztec.rbir_backend.clustering.Document;
import aztec.rbir_backend.clustering.DocumentsList;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static com.google.common.primitives.Ints.min;

/**
 * Created by subhahs on 22/08/2017.
 */
public class TrainingModel {

    public static void calculateTrainingWords(DocumentsList newDocList)
    {
        int numFeatures = 1000;
        Learner learner = new Learner();
        ArrayList<String> finalWordList  = new ArrayList<String>();
        ArrayList<String> categoryList  = new ArrayList<String>();

        TfIdfEncoderClassifier Feature_Matrix_Creator = new TfIdfEncoderClassifier(numFeatures);

        for(Document doc:newDocList)
        {
            if(!categoryList.contains(doc.getCategory()))
            {
                categoryList.add(doc.getCategory());
            }
        }

        for (String category : categoryList)
        {
            ArrayList<Document> CategorizedList = new ArrayList<>(Collections2.filter(newDocList, doc -> doc.getCategory().equals(category)));
            finalWordList = Feature_Matrix_Creator.encode(CategorizedList);

            for(String word:finalWordList)
            {
                word = "\n"+category+","+"'"+word+"'";
            }

            learner.writeToArffFile(finalWordList);
        }
    }
}
