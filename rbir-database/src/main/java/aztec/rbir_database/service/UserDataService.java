package aztec.rbir_database.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import aztec.rbir_database.Entities.User;
import aztec.rbir_database.Entities.UserRole;
import aztec.rbir_database.configurations.HibernateUtil;

@Service
public class UserDataService {

	public User retrieveFromUserName(String userName) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		User user = null;
		try {
			session.beginTransaction();			
			String hql = "from User user where user.username = :userName";
			user = (User) session.createQuery(hql)
			                    .setString("userName", userName)
			                    .uniqueResult();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}		
		return user;
	}
	
	
	public static void addUser(String userName, String password, String email, long roleId){}
	
	
	@SuppressWarnings("unchecked")
	public static List<UserRole>  getAllUserRoles(User user){
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		List<UserRole> roles = null;
		try {
			session.beginTransaction();			
			String hql = "from UserRole ur where ur.user.userId= :user";
			roles = (List<UserRole>) session.createQuery(hql)
			                    .setParameter("user", user.getUserId())
			                    .list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}		
		
		return roles;
	}
	
	public static void deactivateUser(){}
	
	public void addUserRole(String userName, String newRole){}
	
	public void deleteUserRole(String userName, String newRole){}
	
}
