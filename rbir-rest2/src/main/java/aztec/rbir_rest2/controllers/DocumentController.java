package aztec.rbir_rest2.controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import aztec.rbir_backend.indexer.Indexer;
import aztec.rbir_backend.logic.DocumentSeeker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/documents")
public class DocumentController {


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<String>> list(@RequestBody String[] keys) {
        ArrayList<String> documents = new ArrayList<String>();
        DocumentSeeker seeker = new DocumentSeeker();
        documents = seeker.searchDocument("aztec", keys);

        return new ResponseEntity<ArrayList<String>>(documents, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
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
                        File newFile = new File("E://"+newFileName);
                        file.transferTo(newFile);
                        System.out.println(newFile.getAbsolutePath());

                        result = Indexer.indexFile(newFile.getPath());

                    } catch (Exception e) {
                        result = "file saving error";
                    }
                } else {
                    result = "Invalid File!";
                }
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
