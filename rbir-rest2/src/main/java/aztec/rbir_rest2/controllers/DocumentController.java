package aztec.rbir_rest2.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload( 
            @RequestParam("file") MultipartFile file){
            String name = "test11";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
