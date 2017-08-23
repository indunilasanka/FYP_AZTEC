package aztec.rbir_database.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import aztec.rbir_database.Entities.User;
import aztec.rbir_database.configurations.HibernateUtil;


public class UserDataService {

	public static User retrieveFromUserName(String userName) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		User user = null;
		try {
			session.beginTransaction();			
			String hql = "select user from User user where user.username = :userName";
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
	
	
	public static List<String>  getAllUserRoles(){	
		return null;
	}
	
	public static void deactivateUser(){}
	
	public void addUserRole(String userName, String newRole){}
	
	public void deleteUserRole(String userName, String newRole){}
	
}
