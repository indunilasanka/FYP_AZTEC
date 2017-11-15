package aztec.rbir_database.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aztec.rbir_database.Entities.Request;
import aztec.rbir_database.Entities.SearchResultToConfirm;
import aztec.rbir_database.Entities.User;
import aztec.rbir_database.configurations.HibernateUtil;

@Service
public class ResultService {
	
	@Autowired
	UserDataService uds;
	
	@Autowired
	RequestService rqs;

	public List<SearchResultToConfirm> getResults(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<SearchResultToConfirm> resultstoconfirm = null;
		
		try {
			session.beginTransaction();
			
			String hql = "from SearchResultToConfirm srtc";
			
			//session.createQuery(hql).setFirstResult(0);
			//session.createQuery(hql).setMaxResults(10);
			
			resultstoconfirm = (List<SearchResultToConfirm>)session.createCriteria(SearchResultToConfirm.class).list();
			
			session.getTransaction().commit();
			session.close();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		
		return resultstoconfirm;
	}

	public void addResultsToConfirm(String adminUserEmail, int reqId, String searchId, String securityLevel) {
		
		User user = uds.retrieveFromUserName(adminUserEmail);		
		Request request = rqs.getRequest(reqId);
		
		
		SearchResultToConfirm searchResult =  new  SearchResultToConfirm();
		searchResult.setUser(user);
		searchResult.setRequest(request);
		searchResult.setResultId(searchId);
		searchResult.setSecurityLevel(securityLevel);
		
        Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			session.save(searchResult);
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}
	
    public void deleteRequest (int requestId) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		//Request request = null;
		try {
			session.beginTransaction();			
			String hql = "delete SearchResultToConfirm srtc where srtc.requestId = :requestId";
			session.createQuery(hql)
			                    .setInteger("requestId", requestId)
			                    .executeUpdate();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}		
	}
	
	
	
}
