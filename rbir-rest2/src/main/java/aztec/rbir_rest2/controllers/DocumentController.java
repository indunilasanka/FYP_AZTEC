package aztec.rbir_rest2.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aztec.rbir_database.Entities.Document;
import aztec.rbir_database.Entities.Keyword;
import aztec.rbir_database.dataacess.Data;
import aztec.rbir_rest2.models.SuggestedKeys;


@RestController
@RequestMapping("/documents")
public class DocumentController {
	
	 
	@CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/list", method = RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody ResponseEntity<String> list(){
        
   
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
        
        return new ResponseEntity<String>("Product saved successfully", HttpStatus.OK);
    
    
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/upload", method=RequestMethod.POST,produces = "application/json")
    public @ResponseBody SuggestedKeys handleFileUpload( 
            @RequestParam("file") MultipartFile file){
            String name = "test11";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                System.out.print(file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                ArrayList<String> maxFreqKeywords = new ArrayList<String> ();
                maxFreqKeywords.add("Education");
                maxFreqKeywords.add("Free");
                maxFreqKeywords.add("Price");
                maxFreqKeywords.add("Government");
                maxFreqKeywords.add("Reduce");
                maxFreqKeywords.add("Tax");
                maxFreqKeywords.add("Minister");
                maxFreqKeywords.add("Saitam");
                maxFreqKeywords.add("Pecident");
                maxFreqKeywords.add("Nevil");
                
                SuggestedKeys sk =  new SuggestedKeys();
                sk.setMaxFreqKeywords(maxFreqKeywords);
                System.out.println("You successfully uploaded " + name + " into " + name + "-uploaded !");
                return sk;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
