package aztec.rbir_rest2.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aztec.rbir_database.Entities.Document;
import aztec.rbir_database.Entities.Keyword;
import aztec.rbir_database.dataacess.Data;


@RestController
@RequestMapping("/documents")
public class DocumentController {
	
	 
	
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity list(){
        
   
        Data data = new Data();
        
        Document doc = new Document();
        
        doc.setName("fyp");
        doc.setPath("c:/");
        doc.setType("pdf");
        
        ArrayList<Keyword> keywords = new ArrayList<Keyword>();
        
        Keyword keyword =  new Keyword();
        keyword.setName("education");
        keywords.add(keyword);
        
        Keyword keyword1 =  new Keyword();
        keyword.setName("free");
        keywords.add(keyword1);
        
        Keyword keyword2 =  new Keyword();
        keyword.setName("higher");
        keywords.add(keyword2);
        
        data.insert(doc, keywords);
        
        return new ResponseEntity("Product saved successfully", HttpStatus.OK);
    }
}
