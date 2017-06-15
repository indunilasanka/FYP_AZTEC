package aztec.rbir_rest2;

import aztec.rbir_backend.classifier.Learner;
import aztec.rbir_backend.globals.Global;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;


@SpringBootApplication
public class SpringBootWebApplication {
    public static void main(String[] args) {
        Global global = new Global();
        Learner learner = new Learner();
        learner.trainModel();
        SpringApplication.run(SpringBootWebApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("In shutdown hook");
                Global.writeToFile();
            }
        }, "Shutdown-thread"));
    }

}
