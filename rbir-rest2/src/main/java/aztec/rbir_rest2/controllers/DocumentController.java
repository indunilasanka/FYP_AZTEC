package aztec.rbir_rest2.controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;

import aztec.rbir_backend.classifier.Classify;
import aztec.rbir_rest2.models.*;
import aztec.rbir_backend.classifier.*;
import aztec.rbir_backend.clustering.*;
import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.logic.FileReaderFactory;
import aztec.rbir_backend.queryprocess.Searcher;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/documents")
public class DocumentController {


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Set<DocumentModel>> list(@RequestParam("query") String query) {
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

        Set<DocumentModel> response = new HashSet<DocumentModel>();
        if(result != null) {
            for (String doc : result) {
                String content = FileReaderFactory.read(doc);
                DocumentModel document = new DocumentModel(doc, content.substring(0, content.length() > 200?200:content.length()), Global.getHashtableFiles().get(doc));
                response.add(document);
            }
        }
        return new ResponseEntity<Set<DocumentModel>>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> handleFileUpload(@RequestParam("file") ArrayList<MultipartFile> files) {

        String result = null;
        DocumentsList documentList = new DocumentsList(files);
        documentList.forEach(e -> {
            String predictedCategory = Classifier.getCategory(e.getContents());
            e.setPredictedCategory(predictedCategory);
        });

        System.out.println("test");

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> handleInitialSetup(@RequestParam("file") ArrayList<MultipartFile> files, @RequestParam("level") ArrayList<String> categories)
    {
        System.out.println();

        DocumentsList documentList1 = new DocumentsList(files, categories);
        DocumentsList documentList2 = new DocumentsList();

        for(Document doc: documentList1){
            documentList2.add(doc);
        }

       // Thread clusteringThread = new Thread(new Runnable() {
      //      @Override
       //     public void run() {
              /*  Encoder encoder = new TfIdfEncoder(10000);
                encoder.encode(documentList1);
                Distance distance = new CosineDistance();
                Clusterer clusterer = new KMeanClusterer(distance, 0.5, 1000);
                ClustersList clusterList = clusterer.cluster(documentList1,2);
                System.out.println(clusterList.toString());*/
       //     }
     //   });

     //   Thread classificationThread = new Thread(new Runnable() {
     ///       @Override
     //       public void run() {
                TrainingModel.calculateTrainingWords(documentList2);
                documentList2.forEach(e -> {
                    String predictedCategory = Classifier.getCategory(e.getContents());
                    e.setPredictedCategory(predictedCategory);
                });
      //      }
     //   });

       /* clusteringThread.start();
        classificationThread.start();
        try {
            clusteringThread.join();
            classificationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println("test");


        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
