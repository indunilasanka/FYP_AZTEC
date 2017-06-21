package aztec.rbir_rest2.controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import aztec.rbir_backend.classifier.Classify;
import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.indexer.Indexer;
import aztec.rbir_backend.logic.DocumentSeeker;
import aztec.rbir_backend.logic.FileReaderFactory;
import aztec.rbir_backend.queryprocess.Searcher;
import aztec.rbir_rest2.models.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/documents")
public class DocumentController {


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Set<Document>> list(@RequestParam("query") String query) {
        System.out.println("Query "+query);
        Set<String> result = new HashSet<String>();
        if(query.split(" ").length == 1)
            result = Searcher.searchOWQ(query);
        else if(query.indexOf('"') != -1){
            String search = query.substring(1,query.length()-1);
            result = Searcher.searchPQ(search);
        }
        else{
            result = Searcher.searchFTQ(query);
        }

        Set<Document> response = new HashSet<Document>();
        if(result != null) {
            for (String doc : result) {
                String content = FileReaderFactory.read(doc);
                Document document = new Document(doc, content.substring(0, content.length() > 200?200:content.length()), Global.getHashtableFiles().get(doc));
                response.add(document);
            }
        }
        return new ResponseEntity<Set<Document>>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
                String fullfilename = file.getOriginalFilename();
                String filename = fullfilename.substring(fullfilename.lastIndexOf('\\')+1,fullfilename.lastIndexOf('.'));
                String fileextention = fullfilename.substring(fullfilename.lastIndexOf('.')+1);
                System.out.println(filename);
                System.out.println(fileextention);
                String result = null;
                if (!file.isEmpty()) {
                    try {
                        System.out.println(file.getOriginalFilename());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String newFileName = filename+"_"+dateFormat.format(date)+"."+fileextention;
                        System.out.println(newFileName);
                        File newFile = new File("E://project/"+newFileName);
                        if(!newFile.exists()) {
                            file.transferTo(newFile);
                            System.out.println(newFile.getAbsolutePath());

                            result = Indexer.indexFile(newFile.getPath());
                            Classify.classify(newFile.getPath());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.writeToFile();
                                    System.out.println("wrote to file");
                                }
                            }).start();

                        }
                        else
                            result = "File already exist";

                    } catch (Exception e) {
                        result = "fail";
                    }
                } else {
                    result = "fail";
                }
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
