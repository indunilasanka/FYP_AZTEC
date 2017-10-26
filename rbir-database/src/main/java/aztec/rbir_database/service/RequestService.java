package aztec.rbir_database.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import aztec.rbir_database.Entities.PublicUser;
import aztec.rbir_database.Entities.Request;
import aztec.rbir_database.configurations.HibernateUtil;

public class RequestService {

	
	public static PublicUser checkUserofEmail(String email) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		PublicUser user = null;
		try {
			session.beginTransaction();			
			String hql = "from PublicUser user where user.email = :email";
			user = (PublicUser) session.createQuery(hql)
			                    .setString("email", email)
			                    .uniqueResult();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}		
		return user;
	}
	
	
	public static void saveRequest(Request req){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			session.save(req);
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
	
	public static void saveUser(PublicUser pUser){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			session.save(pUser);
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
	
	
}
