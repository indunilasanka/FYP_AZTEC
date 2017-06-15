package aztec.rbir_backend.globals;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Global {
    private static  Hashtable<String,Hashtable<String,ArrayList<Integer>>> hashtableIndex;
    private static  Hashtable<String,String> hashtableFiles;

    public Global(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("E:/index.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.hashtableIndex = (Hashtable<String,Hashtable<String,ArrayList<Integer>>>)ois.readObject();
            System.out.println(hashtableIndex);
            ois.close();
        } catch (FileNotFoundException e) {
            this.hashtableIndex = new Hashtable<String,Hashtable<String, ArrayList<Integer>>>();
        } catch (IOException e) {
            this.hashtableIndex = new Hashtable<String,Hashtable<String, ArrayList<Integer>>>();
        } catch (ClassNotFoundException e) {
            this.hashtableIndex = new Hashtable<String,Hashtable<String, ArrayList<Integer>>>();
        }

        try {
            fis = new FileInputStream("E:/fileCategories.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.hashtableFiles = (Hashtable<String,String>)ois.readObject();
            System.out.println(hashtableFiles);
            ois.close();
        } catch (FileNotFoundException e) {
            this.hashtableFiles = new Hashtable<String,String>();
        } catch (IOException e) {
            this.hashtableFiles = new Hashtable<String,String>();
        } catch (ClassNotFoundException e) {
            this.hashtableFiles = new Hashtable<String,String>();
        }
    }
    public static Hashtable<String,Hashtable<String,ArrayList<Integer>>> getHashtableIndex(){
        return hashtableIndex;
    }
    public static Hashtable<String,String> getHashtableFiles(){return hashtableFiles;}
    public static void writeToFile(){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("E:/index.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashtableIndex);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos = new FileOutputStream("E:/fileCategories.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashtableFiles);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
