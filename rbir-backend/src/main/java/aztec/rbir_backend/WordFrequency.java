package aztec.rbir_backend;

import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;

/**
 * Hello world!
 *
 */
public class WordFrequency
{
    public static void main( String[] args )
    {
        RConnection connection = null;

        try {
            /* Create a connection to Rserve instance running on default port
             * 6311
             */
            connection = new RConnection();

            System.out.println("* Test string and list retrieval");

            /* Note four slashes (\\\\) in the path */
            connection.eval("source('E:TextMining.R')");
           // connection.eval("source('E:Semister 7\\FYP\\FYP_AZTEC\\rbir-backend\\src\\main\\resources\\TextMining.R')");

            System.out.println("* Test string and list retrieval");

            RList z = connection.eval("myFunc()").asList();

            String[] lx = z.at(0).asStrings();
            double[] ly = z.at(1).asDoubles();

            System.out.println(z);


        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
