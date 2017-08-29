package aztec.rbir_backend.classifier;

import aztec.rbir_backend.clustering.Document;
import aztec.rbir_backend.clustering.DocumentsList;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.math.NumberUtils;
import weka.core.converters.ArffLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

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
        HashSet<String> keys_set = new HashSet<String>();

        TfIdfEncoderClassifier Feature_Matrix_Creator = new TfIdfEncoderClassifier(numFeatures);

        for(Document doc:newDocList)
        {
            if(!categoryList.contains(doc.getCategory()))
            {
                categoryList.add(doc.getCategory());
            }
        }

        keys_set = read();
        for (String category : categoryList)
        {
            ArrayList<Document> CategorizedList = new ArrayList<>(Collections2.filter(newDocList, doc -> doc.getCategory().equals(category)));
            finalWordList = Feature_Matrix_Creator.encode(CategorizedList);
            finalWordList.removeAll(keys_set);
            keys_set.addAll(finalWordList);
            finalWordList.replaceAll(word -> "\n"+category+","+"'"+word+"'");
            writeToArffFile(finalWordList);
        }

        learner.trainModel();

        try {
            write(keys_set);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToArffFile(ArrayList<String> dataSet)  {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("rbir-backend/src/main/resources/keys.arff",true));

            for(String str : dataSet)
            {
                writer.append(str);
                writer.flush();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write (HashSet<String> keys_set) throws IOException{
        try {
            FileOutputStream fos = new FileOutputStream("rbir-backend/src/main/resources/file.bin");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(keys_set);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static HashSet<String> read () {

        HashSet<String> keys_set = null;
        try {
            ObjectInputStream input = new ObjectInputStream( new FileInputStream("rbir-backend/src/main/resources/file.bin"));
            keys_set = (HashSet<String>) (input.readObject());
        }
        catch (Exception e) {
            keys_set = new HashSet<String >();
            System.out.println(e);
        }

        return keys_set;
    }

    public void InitialProcess()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream arffFile = null;
        try {

            arffFile = classLoader.getResource("keys.arff").openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(arffFile));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            HashSet<String> keys_set = new HashSet<String>();
            reader.close();

            String arffContent = arff.getData().toString();
            String[] parts = arffContent.split("@data");
            String[] tags = parts[1].split("\n");

            for(int i=0;i<tags.length;i++)
            {
                tags[i]= tags[i].substring(tags[i].lastIndexOf(",") + 1);
                tags[i] = tags[i].replaceAll("[']","");
                keys_set.add(tags[i]);
            }
            write(keys_set);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
