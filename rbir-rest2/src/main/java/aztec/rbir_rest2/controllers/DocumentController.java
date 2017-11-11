package aztec.rbir_rest2.controllers;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


import aztec.rbir_rest2.models.*;
import aztec.rbir_backend.classifier.*;
import aztec.rbir_backend.clustering.*;
import aztec.rbir_backend.globals.Global;
import com.google.common.collect.Collections2;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.search.SearchHit;
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
    ResponseEntity<Set<DocumentModel>> list(@RequestParam("query") String query) {
        System.out.println("Query " + query);

        Set<SearchHit> res = aztec.rbir_backend.document.Document.phraseTextSearch(query);
        System.out.println(res);

        Set<DocumentModel> result = new HashSet<DocumentModel>();

        for(SearchHit hit : res){
            DocumentModel resultDoc = new DocumentModel(hit.getSource().get("path").toString(),hit.getSource().get("content").toString(),hit.getSource().get("category").toString());
            System.out.println(hit.getSource().get("path").toString());
            File file = new File(hit.getSource().get("path").toString());
            resultDoc.setFile(file);
            result.add(resultDoc);
        }
        return new ResponseEntity<Set<DocumentModel>>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<SetupResponse> handleFileUpload(@RequestParam("file") ArrayList<MultipartFile> files) {

        String result = null;
        DocumentsList documentList = new DocumentsList(files);

        if(Global.getClassificationAlgo() == "Naive"){
            documentList.forEach(e -> {
                    String  predictedCategory = Classifier.getCategory(e.getPreprocessedContent());
                    e.setPredictedCategory(predictedCategory);
            });
        }

        else{
            ClustersList clustersList = KMeanClusterer.loadModel();
            Distance distance = new CosineDistance();
            documentList.forEach(e -> {
                String  predictedCategory = KMeanClusterer.findCluster(e,clustersList,distance);
                e.setPredictedCategory(predictedCategory);
            });
        }

        BulkProcessor bulkProcessor = aztec.rbir_backend.document.Document.getBulkProcessor();

        documentList.forEach(e -> {
            File file = new File(e.getFilePath());
            File dir = new File("home/rbir/FYPSavingFolder/indexedFiles");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File destinationDir = new File(dir+"/"+e.getPredictedCategory()+"/");
            try {
                System.out.println("Test");
                Map document = new HashMap<String, Object>();
                document.put("name",file.getName());
                document.put("path",destinationDir.getCanonicalPath() + "/" + file.getName());
                document.put("content",e.getContent());
                document.put("category", e.getPredictedCategory());
                bulkProcessor.add(new IndexRequest(e.getPredictedCategory(),"document").source(document));
                FileUtils.moveFileToDirectory(file, destinationDir, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        try {
            bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, String> doc_cat = new HashMap<String, String>();

        documentList.forEach(e->{
            doc_cat.put(e.getFilePath(),e.getPredictedCategory());
        });

        SetupResponse response = new SetupResponse();
        response.setSuccess(true);
        response.setDoc_category(doc_cat);

        System.out.println("test");

        return new ResponseEntity<SetupResponse>(response, HttpStatus.OK);
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<SetupResponse> handleInitialSetup(@RequestParam("file") ArrayList<MultipartFile> files, @RequestParam("level") ArrayList<String> categories, @RequestParam("securitylvls") ArrayList<String> levels)
    {
        DocumentsList documentList1 = new DocumentsList(files, categories);
        DocumentsList documentList2 = new DocumentsList();

        for(aztec.rbir_backend.clustering.Document doc: documentList1){
            documentList2.add(new Document(doc));
        }

        Encoder encoder = new TfIdfEncoder(10000);
        encoder.encode(documentList1);
        Distance distance = new CosineDistance();
        Clusterer clusterer = new KMeanClusterer(distance, 0.6, 1000);
        ClustersList clusterList = clusterer.cluster(documentList1,3);
        KMeanClusterer.saveModel(clusterList);

        TrainingModel.calculateTrainingWords(documentList2);
        documentList2.forEach(e -> {
            String predictedCategory = Classifier.getCategory(e.getPreprocessedContent());
            e.setPredictedCategory(predictedCategory);
        });


        System.out.println( "----------main thread--------");
        System.out.println( "----------main thread--------");
        System.out.println( "----------main thread--------");

        int numCorrectClassification = 0;

        for(aztec.rbir_backend.clustering.Document doc : documentList1){
            System.out.println(doc.getCategory() + "  " + doc.getPredictedCategory());
            if(doc.getCategory().equals(doc.getPredictedCategory())) {
                numCorrectClassification++;
            }
        }

        int clusteringAccuracy = numCorrectClassification*100/files.size();

        numCorrectClassification = 0;

        System.out.println();

        for(aztec.rbir_backend.clustering.Document doc : documentList2){
            System.out.println(doc.getCategory() + "  " + doc.getPredictedCategory());
            if(doc.getCategory().equals(doc.getPredictedCategory())) {
                numCorrectClassification++;
            }
        }

        int classificationAccuracy = numCorrectClassification*100/files.size();

        DocumentsList indexingDocList = null;

        if(classificationAccuracy >= clusteringAccuracy){
            Global.setClassificationAlgo("Naive");
            indexingDocList = documentList2;
        }
        else {
            Global.setClassificationAlgo("KMeans");
            indexingDocList = documentList1;
        }

        Global.writeToFile();

        BulkProcessor bulkProcessor = aztec.rbir_backend.document.Document.getBulkProcessor();

        Map<String, String> docCategory = new HashedMap<String, String>();

        indexingDocList.forEach(e -> {
            docCategory.put(e.getTitle(),e.getPredictedCategory());
            System.out.println(e.getFilePath());
            File file = new File(e.getFilePath());

            File dir = new File("home/rbir/FYPSavingFolder/indexedFiles");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File destinationDir = new File(dir+"/"+e.getPredictedCategory()+"/");
            try {
                Map document = new HashMap<String, Object>();
                document.put("name",e.getTitle());
                document.put("type",e.getType());
                document.put("path",destinationDir.getCanonicalPath() + "/" + file.getName());
                document.put("content",e.getContent());
                document.put("category", e.getPredictedCategory());
                bulkProcessor.add(new IndexRequest(e.getPredictedCategory(),"document",e.getId()+"").source(document));
                FileUtils.moveFileToDirectory(file, destinationDir, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        try {
            bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, Integer> classifyAccuracy = new HashedMap<String, Integer>();
        classifyAccuracy.put("NaiveBaysian", classificationAccuracy);
        classifyAccuracy.put("KMeans", clusteringAccuracy);

        Map<String, Integer> numDocCategory = new HashMap<String, Integer>();

        for (String category: levels){
            numDocCategory.put(category, Collections2.filter(indexingDocList, doc -> doc.getCategory().equals(category)).size());
        }


        SetupResponse response = new SetupResponse();
        response.setSuccess(true);
        response.setClassifier_accuracy(classifyAccuracy);
        response.setDoc_category(docCategory);
        response.setNum_doc_category(numDocCategory);

        return new ResponseEntity<SetupResponse>(response, HttpStatus.OK);
    }

}
