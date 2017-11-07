package aztec.rbir_backend.globals;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Global {
    private static String classificationAlgo;

    public Global(){
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("rbir-backend/src/main/resources/classifierName.dat"));
                this.classificationAlgo = (String) in.readObject();
                System.out.println(classificationAlgo);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    public static String getClassificationAlgo(){
        return classificationAlgo;
    }

    public static void setClassificationAlgo(String classificationAlgoName){
        classificationAlgo = classificationAlgoName;
    }
    public static void writeToFile(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rbir-backend/src/main/resources/classifierName.dat"));
            out.writeObject(classificationAlgo);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
