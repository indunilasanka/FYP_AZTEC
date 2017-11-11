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
	
	Session session;
	
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User> users = null;
		try {
			session.beginTransaction();			
			String hql = "from User";
			users = (List<User>) session.createQuery(hql).list();
			
			
			if(users.size()==0){
				User user = new User();
				user.setUsername("Aztec");
				user.setPassword("AztecPass");
				session.save(user);
				
				Role role = new Role();
				
				role.setRoleName("ADMIN_USER");
				role.setRoleDescription("admin user for test purposes");
				session.save(role);
				
				UserRole userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUser(user);
				userRole.setGrantedUser(user);
				session.save(userRole);			
				session.getTransaction().commit();
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}
