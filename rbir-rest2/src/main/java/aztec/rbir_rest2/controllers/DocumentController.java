package aztec.rbir_rest2.controllers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;

import aztec.rbir_backend.classifier.Classify;
import aztec.rbir_backend.indexer.IndexerLucence;
import aztec.rbir_rest2.models.*;
import aztec.rbir_backend.classifier.*;
import aztec.rbir_backend.clustering.*;
import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.logic.FileReaderFactory;
import aztec.rbir_backend.queryprocess.Searcher;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Document;
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

        Set<DocumentModel> result = new HashSet<DocumentModel>();

        ArrayList<Document> hitDocs = IndexerLucence.searchIndex(query);
        for(Document doc : hitDocs){
            DocumentModel resultDoc = new DocumentModel(doc.get("path"),doc.get("contents"),doc.get("category"));
            result.add(resultDoc);
        }
        return new ResponseEntity<Set<DocumentModel>>(result, HttpStatus.OK);
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

    /*    DocumentsList documentList1 = new DocumentsList(files, categories);
        DocumentsList documentList2 = new DocumentsList();

        for(Document doc: documentList1){
            documentList2.add(doc);
        }*/

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
   /*             TrainingModel.calculateTrainingWords(documentList2);
                documentList2.forEach(e -> {
                    String predictedCategory = Classifier.getCategory(e.getContents());
                    e.setPredictedCategory(predictedCategory);
                });*/
      //      }
     //   });

    /*    documentList2.forEach(e -> {
            System.out.println(e.getFilePath());
            File file = new File(e.getFilePath());
            File destinationDir = new File("E:/FYPSavingFolder/indexedFiles/"+e.getPredictedCategory()+"/");
            try {
                FileUtils.moveFileToDirectory(file, destinationDir, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });*/


        File indexedFilesDir = new File("E:/FYPSavingFolder/indexedFiles/");
        File[] directories = indexedFilesDir.listFiles();

        for (File folder : directories) {
            try {
                String files_directory = folder.getCanonicalPath();
                System.out.println(files_directory + "  " + files_directory.substring(files_directory.lastIndexOf('\\')+1));
                String category = files_directory.substring(files_directory.lastIndexOf('\\')+1);
                new File(files_directory+"/index/").mkdir();
                String index_directory = files_directory + "/index/";
                System.out.println(files_directory+"   "+index_directory);
                IndexerLucence.createIndex(files_directory,index_directory,category);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
