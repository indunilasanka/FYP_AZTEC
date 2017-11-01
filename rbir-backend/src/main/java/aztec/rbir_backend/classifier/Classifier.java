package aztec.rbir_backend.classifier;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import aztec.rbir_backend.indexer.Terms;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;


public class Classifier {

    /**
     * String that stores the text to classify
     */
    String text;
    /**
     * Object that stores the instance.
     */
    Instances instances;
    /**
     * Object that stores the classifier.
     */
    FilteredClassifier classifier;


    public void load(String content) {
        text = Terms.getProcessedContent(content);
        System.out.println(text);
        System.out
                .println("===== Loaded text data: " + text + " =====");

        /*try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            text = "";
            while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
            System.out
                    .println("===== Loaded text data: " + fileName + " =====");
            reader.close();
            System.out.println(text);
        } catch (IOException e) {
            System.out.println("Problem found when reading: " + fileName);
        }*/
    }


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



    public void makeInstance() {
        // Create the attributes, class and text
        FastVector fvNominalVal = new FastVector(2);
        fvNominalVal.addElement("Security_Level_1");
        fvNominalVal.addElement("Security_Level_2");
        fvNominalVal.addElement("Security_Level_3");
        fvNominalVal.addElement("Security_Level_4");
        fvNominalVal.addElement("Security_Level_5");
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

    /**
     * This method performs the classification of the instance. Output is done
     * at the command-line.
     */
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


    public static String getCategory(String content) {

        Classifier classifier;
        //String testFile = "rbir-backend/src/main/resources/document.txt";
        String dataModel = "rbir-backend/src/main/resources/myClassifier.dat";
        classifier = new Classifier();
        classifier.load(content);
        classifier.loadModel(dataModel);
        classifier.makeInstance();
        return classifier.classify();

    }
}
