package aztec.rbir_backend;

import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.logic.FileReaderFactory;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("In shutdown hook");
                Global.writeToFile();
            }
        }, "Shutdown-thread"));


    }
}
