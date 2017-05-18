package aztec.rbir_backend;

import com.uttesh.exude.ExudeData;
import com.uttesh.exude.api.ExudeAPI;
import com.uttesh.exude.api.ExudeTextData;
import com.uttesh.exude.exception.InvalidDataException;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class WordFrequency
{
    public static void main( String[] args )
    {

        // --- find frequency with R --- //
        /*RConnection connection = null;

        try {
            connection = new RConnection();

            System.out.println("* Test string and list retrieval");

            connection.eval("source('E:TextMining.R')");
           // connection.eval("source('E:Semister 7\\FYP\\FYP_AZTEC\\rbir-backend\\src\\main\\resources\\TextMining.R')");

            System.out.println("* Test string and list retrieval");

            RList z = connection.eval("myFunc()").asList();

//            String[] lx = z.at(0).asStrings();
//            double[] ly = z.at(1).asDoubles();

            System.out.println(z);


        }

        catch (Exception e) {
            e.printStackTrace();
        } */

        String[] map = WordFrequencyWithJava.getMostFrequentWords("E:\\CV.pdf",10);

        System.out.println(map);

        System.out.println();

    }



}
