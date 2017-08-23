package aztec.rbir_backend.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by subhahs on 03/08/2017.
 */

//KMean cluster model
public class KMeanClusterer extends Clusterer {

    // threshold used to determine number of clusters k
    private final double clusteringThreshold;
    // number of iterations to use in k means clustering
    private final int clusteringIterations;

    public KMeanClusterer(Distance distance, double clusteringThreshold,
                           int clusteringIterations) {
        this.distance = distance;
        this.clusteringThreshold = clusteringThreshold;
        this.clusteringIterations = clusteringIterations;
    }

    /**
     * Run k means clustering on documentList. Number of clusters k is set to
     * the lowest value that ensures the intracluster to intercluster distance
     * ratio is above clusteringThreshold
     *
     * @return ClusterList containing the clusters
     */
    public ClustersList cluster(DocumentsList documentList, int numClusters) {
        HashMap<String, ArrayList<Document>> map = new HashMap<String, ArrayList<Document>>();
        documentList.forEach(document -> {
            if(map.containsKey(document.getCategory())){
                map.get(document.getCategory()).add(document);
            }
            else{
                ArrayList<Document> documents = new ArrayList<Document>();
                documents.add(document);
                map.put(document.getCategory(),documents);
            }
        });

        ClustersList clusterList = null;
        for (int k = 1; k <= documentList.size(); k++) {
       // for (int k = 1; k <= 5; k++) {
            clusterList = runKMeansClustering(documentList, numClusters, map);
            if (clusterList.calcIntraInterDistanceRatio(distance) < clusteringThreshold) {
                break;
            }
        }
        return clusterList;
    }


    //Run k means clustering on documentList for a fixed number of clusters k
    private ClustersList runKMeansClustering(DocumentsList documentList, int k, HashMap<String, ArrayList<Document>> initialList) {
        ClustersList clusterList = new ClustersList(k);
        documentList.clearIsAllocated();

        initialList.forEach((s, documents) -> {
            Random rnd = new Random();
            int rndDocIndex = rnd.nextInt(documents.size());
            Cluster cluster = new Cluster(documents.get(rndDocIndex));
            clusterList.add(cluster);
        });


        // create k-1 more clusters
        /*while (clusterList.size() < k) {
            // create new cluster containing furthest doc from existing clusters
            Document furthestDocument =
                    clusterList.findFurthestDocument(distance, documentList);
            Cluster nextCluster = new Cluster(furthestDocument);
            clusterList.add(nextCluster);
        }*/

        // add remaining documents to one of the k existing clusters
        for (int iter = 0; iter < clusteringIterations; iter++) {
            for (Document document : documentList) {
                if (!document.isAllocated()) {
                    Cluster nearestCluster =
                            clusterList.findNearestCluster(distance, document);
                    nearestCluster.add(document);
                }
            }
            // update centroids and centroidNorms
            clusterList.updateCentroids();
            // prepare for reallocation in next iteration
            if (iter < clusteringIterations - 1) {
                documentList.clearIsAllocated();
                clusterList.emptyClusters();
            }
        }
        return clusterList;
    }

    public String findCluster(Document document, ClustersList clusterList){
        Cluster nearestCluster = clusterList.findNearestCluster(distance, document);
        nearestCluster.add(document);
        nearestCluster.updateCentroid();
        return document.getPredictedCategory();
    }

    public DocumentsList findCluster(DocumentsList documentList, ClustersList clusterList){
        for (Document document : documentList) {
            Cluster nearestCluster = clusterList.findNearestCluster(distance, document);
            nearestCluster.add(document);
        }
        clusterList.updateCentroids();
        return documentList;
    }

}
