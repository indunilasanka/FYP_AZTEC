package aztec.rbir_rest2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import aztec.rbir_backend.email.MailClient;
import aztec.rbir_database.Entities.PublicUser;
import aztec.rbir_database.Entities.Request;
import aztec.rbir_database.Entities.SearchResultToConfirm;
import aztec.rbir_database.Entities.User;
import aztec.rbir_database.service.RequestService;
import aztec.rbir_database.service.ResultService;
import aztec.rbir_database.service.UserDataService;

@RestController
@RequestMapping("/result")
public class ResultController {

	@Autowired
	MailClient mc;
	
	@Autowired
	ResultService rs;
	
	@Autowired
	UserDataService uds;
	
	@Autowired
	RequestService rqs;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addResultsToConfirm(String adminUserEmail,int reqId,String searchId){
		
		
		rs.addResultsToConfirm(adminUserEmail,reqId,searchId);
		
		
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public List<SearchResultToConfirm> getResultsToConfirm(){
		
		List<SearchResultToConfirm> results =  rs.getResults();
		return results;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ResponseBody
	public void confirmResult(String publicUserEmail,String searchResultId){
		
	      //implement confirm method	
	}
	
	
	
}
