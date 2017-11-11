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

    public static String localPath = "E:/FYP/";
    public static String serverPath = "/home/rbir/";


    public Global(){
            try {
                ObjectInputStream in = new ObjectInputStream(loader.getResourceAsStream("classifierName.dat"));
                this.classificationAlgo = (String) in.readObject();
                System.out.println(classificationAlgo);
                in.close();
                in = new ObjectInputStream(loader.getResourceAsStream("lastDocId.dat"));
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
            File file = new File(loader.getResourceAsStream("classifierName.dat").toString());
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(classificationAlgo);
            out.close();
            file = new File(loader.getResourceAsStream("lastDocId.dat").toString());
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(lastDocId);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
