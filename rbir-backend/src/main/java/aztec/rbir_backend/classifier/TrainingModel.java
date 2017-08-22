package aztec.rbir_backend.classifier;

import aztec.rbir_backend.clustering.DocumentsList;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static com.google.common.primitives.Ints.min;

/**
 * Created by subhahs on 22/08/2017.
 */
public class TrainingModel {

    public static void calculateTrainingWords(DocumentsList documentsList)
    {
        int numFeatures = 1000;
        String [][] termFreqMatrix,inverseDocFreqMatrix = null;
        ArrayList<String[]> finalTFMatrix  = new ArrayList<String[]>();
        ArrayList<String[]> finalIDFMatrix  = new ArrayList<String[]>();

        TfIdfEncoderClassifier  Feature_Matrix_Creator = new TfIdfEncoderClassifier(numFeatures);
        termFreqMatrix = inverseDocFreqMatrix = Feature_Matrix_Creator.encode(documentsList);

        //sorting the 2d array on TF column
        Arrays.sort(termFreqMatrix, new Comparator<String[]>() {
            @Override
            public int compare( String[] entry1, String[] entry2) {
                double tf1 = Double.parseDouble(entry1[1]);
                double tf2 = Double.parseDouble(entry2[1]);
                return -Double.compare(tf1,tf2);
            }
        });

        for(String[] s : termFreqMatrix)
        {
            if((s[0]!=null)&(s[1]!="0.0"))
            {
                finalTFMatrix.add(new String[]{s[0],s[1]});
            }
        }

        //sorting the 2d array on IDF column
        Arrays.sort(inverseDocFreqMatrix, new Comparator<String[]>() {
            @Override
            public int compare( String[] entry1, String[] entry2) {
                double idf1 = Double.parseDouble(entry1[2]);
                double idf2 = Double.parseDouble(entry2[2]);
                return -Double.compare(idf1,idf2);
            }
        });

        for(String[] s : inverseDocFreqMatrix)
        {
            if((s[0]!=null)&(s[2]!="0.0")&(!NumberUtils.isNumber(s[0])))
            {
                finalIDFMatrix.add(new String[]{s[0],s[1]});
            }
        }

        ArrayList<String> wordsList  = new ArrayList<String>();
        int minVal = min(finalIDFMatrix.size(),finalTFMatrix.size());

        for(int s = 0; s < minVal; s++)
        {
            if(!wordsList.contains(finalTFMatrix.get(s)[0]))
            {
                wordsList.add(finalTFMatrix.get(s)[0]);
            }
            if(!wordsList.contains(finalIDFMatrix.get(s)[0]))
            {
                wordsList.add(finalIDFMatrix.get(s)[0]);
            }
        }

        wordsList.removeIf(str->((NumberUtils.isNumber(str))|(str.length() == 1)));

        Learner learner = new Learner();
        learner.writeToArffFile(wordsList);
        //WordFrequency maxFrequency = new WordFrequency();
        //wordsArray = maxFrequency.getMostFrequentWords(content,10);
    }
}
