package aztec.rbir_rest2.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import aztec.rbir_backend.document.Document;
import aztec.rbir_rest2.models.DocumentModel;
import aztec.rbir_rest2.models.DocumentsToConfirm;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import aztec.rbir_backend.email.MailClient;
import aztec.rbir_database.Entities.SearchResultToConfirm;
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
	public void addResultsToConfirm(String adminUserEmail,int reqId, String searchId, String securityLevel){

		rs.addResultsToConfirm(adminUserEmail,reqId,searchId, securityLevel);

	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public
	@ResponseBody
	ResponseEntity<List<DocumentsToConfirm>> getResultsToConfirm(){

		List<DocumentsToConfirm> documets = new ArrayList<DocumentsToConfirm>();
		List<SearchResultToConfirm> results =  rs.getResults();

		results.forEach(result->{
			String id = result.getResultId();
			String securityLevel = result.getSecurityLevel();
			String query = result.getRequest().getRequest();
			Set<SearchHit> searchHits = Document.freeTextSearch(securityLevel, query);
			for(SearchHit hit : searchHits){
				if(hit.getId().equals(id)){
					Text[] summary = hit.getHighlightFields().get("content").fragments();

					ArrayList<String> content = new ArrayList<String>();
					for(Text text: summary){
						content.add(text.toString().replace("\n","<br></br>"));
					}

					DocumentModel resultDoc = new DocumentModel(hit.getId(),hit.getSource().get("name").toString(),content,hit.getSource().get("type").toString(),hit.getSource().get("category").toString());
					File file = new File(hit.getSource().get("path").toString());
					resultDoc.setFile(file);
					documets.add(new DocumentsToConfirm(result.getId(),resultDoc));
				}
			}


		});

		return new ResponseEntity<List<DocumentsToConfirm>>(documets, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ResponseBody
	public void confirmResult(@RequestParam("publicUserEmail") String publicUserEmail, @RequestParam("searchResultId") int searchResultId, @RequestParam("filePath") String filePath){
		System.out.println("Sent mail");
		rs.deleteRequest(searchResultId);
	}
	
	
	
}
