package aztec.rbir_backend.classifier;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.io.FileWriter;
import java.util.Scanner;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * Created by subhahs on 14/06/2017.
 */
public class Learner {
    /**
     * Object that stores training data.
     */
    Instances trainData;
    /**
     * Object that stores the filter
     */
    StringToWordVector filter;
    /**
     * Object that stores the classifier
     */
    FilteredClassifier classifier;

    /**
     * This method loads a dataset in ARFF format. If the file does not exist,
     * or it has a wrong format, the attribute trainData is null.
     *
     * @param fileName
     *            The name of the file that stores the dataset.
     */
    public void loadDataset(InputStream fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileName));
            ArffReader arff = new ArffReader(reader);
            trainData = arff.getData();
            System.out.println("===== Loaded dataset: " + fileName + " =====");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem found when reading: " + fileName);
        }
    }

    /**
     * This method evaluates the classifier. As recommended by WEKA
     * documentation, the classifier is defined but not trained yet. Evaluation
     * of previously trained classifiers can lead to unexpected results.
     */
    public void evaluate() {
        try {
            trainData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");

            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());

            Evaluation eval = new Evaluation(trainData);
            int folds = 4;
            eval.crossValidateModel(classifier, trainData, folds , new Random(1));

            System.out.println("===== Evaluating on filtered (training) dataset =====");
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());

        } catch (Exception e) {
            System.out.println("Problem found when evaluating");
        }
    }

    /**
     * This method trains the classifier on the loaded dataset.
     */
    public void learn() {
        try {
            trainData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());


            classifier.buildClassifier(trainData);
            // Uncomment to see the classifier
            System.out.println("===== Training on filtered (training) dataset =====");
            //System.out.println(classifier);
        } catch (Exception e) {
            System.out.println("Problem found when training");
        }
    }

    public void saveModel() {

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("/myClassifier.dat"));
            out.writeObject(classifier);
            out.close();
            System.out.println("===== Saved model: "  + " =====");
        } catch (IOException e) {
            System.out.println("Problem found when writing: ");
        }
    }

    public void trainModel() {

        Learner learner;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream arffFile = null;
        try {
            arffFile = classLoader.getResource("keys.arff").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        learner = new Learner();
        learner.loadDataset(arffFile);
        // Evaluation mus be done before training
        // More info in:
        // http://weka.wikispaces.com/Use+WEKA+in+your+Java+code
        learner.evaluate();
        learner.learn();
        learner.saveModel();
    }

    public void writeToArffFile(String[] dataSet)  {

        //InitialProcess();
        HashSet<String> keys_set = new HashSet<String>();
        BufferedWriter writer = null;
        //Scanner scan = new Scanner(System.in);
        //String File_Type = scan.next();

        try {
            writer = new BufferedWriter(new FileWriter("rbir-backend/src/main/resources/keys.arff",true));
            keys_set = read();

            for(String str : dataSet)
            {
                if(!keys_set.contains(str))
                {
                    System.out.println(str);
                    keys_set.add(str);
                    writer.append("\nLow-Sensitive,"+"'"+str+"'");
                    writer.flush();
                }
            }
            write(keys_set);
            writer.close();
            trainModel(); // train the model after appending new keywords to the keys file

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write (HashSet<String> keys_set) throws IOException{
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

    public HashSet<String> read () {

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
            ArffReader arff = new ArffReader(reader);
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
