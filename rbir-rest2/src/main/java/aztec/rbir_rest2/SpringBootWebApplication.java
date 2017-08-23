package aztec.rbir_rest2;

import aztec.rbir_backend.classifier.Learner;
import aztec.rbir_backend.globals.Global;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootWebApplication {
    public static void main(String[] args) {
<<<<<<< HEAD
        //Global global = new Global();
        Learner learner = new Learner();
        learner.trainModel();
=======
>>>>>>> b1891e193f451fc89a6db5f7ae199e5f04f44e00
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
    
    

}
