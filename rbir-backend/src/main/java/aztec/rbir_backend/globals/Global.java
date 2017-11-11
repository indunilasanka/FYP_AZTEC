package aztec.rbir_backend.globals;

import groovy.transform.Synchronized;
import org.hibernate.annotations.Synchronize;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Global {
    private static String classificationAlgo;
    private static long lastDocId;

    public static ClassLoader loader = Global.class.getClassLoader();

    public static String path = "E:/FYP/";//"/home/rbir/";


    public Global(){
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(path+"classifierName.dat"));
                this.classificationAlgo = (String) in.readObject();
                System.out.println(classificationAlgo);
                in.close();
                in = new ObjectInputStream(new FileInputStream(path+"lastDocId.dat"));
                this.lastDocId = (long) in.readObject();
                System.out.println(lastDocId);
                in.close();
            } catch (FileNotFoundException e) {
                this.classificationAlgo = null;
                lastDocId = 0;
            } catch (IOException e) {
                this.classificationAlgo = null;
                lastDocId = 0;
            } catch (ClassNotFoundException e) {
                this.classificationAlgo = null;
                lastDocId = 0;
            }
    }
    public static String getClassificationAlgo(){
        return classificationAlgo;
    }

    public synchronized static long getLastDocId(){
        return lastDocId++;
    }

    public static void setClassificationAlgo(String classificationAlgoName){
        classificationAlgo = classificationAlgoName;
    }

    public static void writeToFile(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path+"classifierName.dat"));
            out.writeObject(classificationAlgo);
            out.close();
            out = new ObjectOutputStream(new FileOutputStream(path+"lastDocId.dat"));
            out.writeObject(lastDocId);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
