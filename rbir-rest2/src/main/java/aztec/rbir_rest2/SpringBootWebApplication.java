package aztec.rbir_rest2;

import aztec.rbir_backend.classifier.Learner;
import aztec.rbir_backend.globals.Global;
import aztec.rbir_database.Entities.Role;
import aztec.rbir_database.Entities.User;
import aztec.rbir_database.Entities.UserRole;
import aztec.rbir_database.configurations.HibernateUtil;

import java.io.File;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootWebApplication {

	
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {

        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}
