package aztec.rbir_rest2.controllers;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import aztec.rbir_database.Entities.PublicUser;
import aztec.rbir_database.Entities.Request;
import aztec.rbir_database.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import aztec.rbir_backend.email.MailClient;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@RestController
@RequestMapping("/request")
public class RequestController {

	@Autowired
	MailClient mc;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/public-request", method = RequestMethod.POST)
	@ResponseBody
	public boolean handlePublicRequest(@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("request") String request,@RequestParam("image_url") String imgUrl){
		
		PublicUser pUser;
		
		pUser= RequestService.checkUserofEmail(email);
		
		if(pUser==null){
			
			pUser = new PublicUser();
			pUser.setUsername(username);
			pUser.setEmail(email);
			pUser.setReputation(1);
			pUser.setImage(imgUrl);
			RequestService.saveUser(pUser);

		}else{
			
			int currentReputation = pUser.getReputation();
			pUser.setReputation(currentReputation+1);
			

		}
		
		
		Request req = new Request();		
		req.setpUser(pUser);
		req.setRequest(request);
		RequestService.saveRequest(req);
		
		return true;
	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(value = "/get-requests", method = RequestMethod.GET)
	@ResponseBody
	public List<Request> getRequests(){
		
		List<Request> requests =  RequestService.getRequests();
		return requests;
		
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/delete-request", method = RequestMethod.POST)
	@ResponseBody
	public void deleteRequest(@RequestParam("email") String email,@RequestParam("request") String req, @RequestParam("requestid") int reqId){
		
		RequestService.deleteRequest(reqId);
  		try {
 			mc.generateAndSendRejectEmail(email, req);
 		} catch (AddressException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (MessagingException e) {
     		// TODO Auto-generated catch block
 			e.printStackTrace();
    	}

		
	}
	
	
}
