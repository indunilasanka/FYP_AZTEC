package aztec.rbir_rest2.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import aztec.rbir_database.Entities.PublicUser;
import aztec.rbir_database.Entities.Request;
import aztec.rbir_database.service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {

	
	
	@RequestMapping(value = "/public-request", method = RequestMethod.POST)
	@ResponseBody
	public boolean handlePublicRequest(@RequestParam("e-mail") String email,@RequestParam("username") String username,@RequestParam("request") String request){
		
		PublicUser pUser;
		
		pUser= RequestService.checkUserofEmail(email);
		
		if(pUser==null){
			
			pUser = new PublicUser();
			pUser.setUsername(username);
			pUser.setEmail(email);
			pUser.setReputation(1);
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
}
