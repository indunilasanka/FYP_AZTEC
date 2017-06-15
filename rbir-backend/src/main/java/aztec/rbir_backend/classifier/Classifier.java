package aztec.rbir_backend.classifier;

/**
 * Created by subhahs on 14/06/2017.
 */
import java.io.*;

import com.google.common.io.Resources;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;


public class Classifier {

    /**
     * String that stores the text to classify
     */
    String text;

    Instances instances;

    FilteredClassifier classifier;


    public void load(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            text = "";
            while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
            System.out
                    .println("===== Loaded text data: " + filePath + " =====");
            reader.close();
            System.out.println(text);
        } catch (IOException e) {
            System.out.println("Problem found when reading: " + filePath);
        }
    }

    /**
     * This method loads the model to be used as classifier.
     *
     * @param fileName
     *            The name of the file that stores the text.
     */
    public void loadModel(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    fileName));
            Object tmp = in.readObject();
            classifier = (FilteredClassifier) tmp;
            in.close();
            System.out.println("===== Loaded model: " + fileName + " =====");
        } catch (Exception e) {
            // Given the cast, a ClassNotFoundException must be caught along
            // with the IOException
            System.out.println("Problem found when reading: " + fileName);
        }
    }

    /**
     * This method creates the instance to be classified, from the text that has
     * been read.
     */
    public void makeInstance() {
        // Create the attributes, class and text
        FastVector fvNominalVal = new FastVector(2);
        fvNominalVal.addElement("personal");
        fvNominalVal.addElement("security");
        fvNominalVal.addElement("financial");
        Attribute attribute1 = new Attribute("class", fvNominalVal);
        Attribute attribute2 = new Attribute("text", (FastVector) null);
        // Create list of instances with one element
        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute1);
        fvWekaAttributes.addElement(attribute2);
        instances = new Instances("Test relation", fvWekaAttributes, 1);
        // Set class index
        instances.setClassIndex(0);
        // Create and add the instance

        Instance instance = new DenseInstance(2);

        // DenseInstance instance = new DenseInstance(2);

        instance.setValue(attribute2, text);
        // Another way to do it:
        // instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
        instances.add(instance);
        System.out
                .println("===== Instance created with reference dataset =====");
        System.out.println(instances);
    }


    public String classify() {
        try {
            double pred = classifier.classifyInstance(instances.instance(0));
            System.out.println("===== Classified instance =====");
            System.out.println("Class predicted: "+ instances.classAttribute().value((int) pred));
            return instances.classAttribute().value((int) pred);
        } catch (Exception e) {
            System.out.println("Problem found when classifying the text");
            return "fail";
        }
    }

    public static String getCategory(String filePath) {

        Classifier classifier;


        String dataModel = "/myClassifier.dat";
        System.out.println(dataModel);
        classifier = new Classifier();
        classifier.load(filePath);
        classifier.loadModel(dataModel);
        classifier.makeInstance();
        return classifier.classify();
    }
}
