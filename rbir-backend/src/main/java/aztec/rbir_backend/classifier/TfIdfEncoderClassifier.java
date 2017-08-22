package aztec.rbir_backend.classifier;


import aztec.rbir_backend.clustering.*;

/**
 * Created by subhahs on 03/08/2017.
 */

//Encoder with term Frequency Inverse document frequency
public class TfIdfEncoderClassifier {

    // number of features to be used in feature vector
    private final int numFeatures;
    // inverse document frequency used for normalization of feature vectors
    private Vector inverseDocumentFrequency = null;
    String [][] TermFrequencyArray = null;
    public TfIdfEncoderClassifier(int numFeatures)
    {
        this.numFeatures = numFeatures;
    }

    // Calculate word histogram for document
    public String [][]  calcHistogram(Document document) {

        String[] words = document.getContents().split("[^\\w]+");
        Vector histogram = new Vector(numFeatures);

        if(document.getId()!=0)
        {
            for (int i = 0; i < words.length; i++) {
                int hashCode = hashWord(words[i]);
                histogram.increment(hashCode);
            }
        }
        else
        {
            TermFrequencyArray = new String [numFeatures][3];
            for (int i = 0; i < words.length; i++) {
                int hashCode = hashWord(words[i]);
                TermFrequencyArray[hashCode][0] = words[i];
                histogram.increment(hashCode);
            }
            for(int n = 0; n < numFeatures; n++)
            {
                TermFrequencyArray[n][1] = String.valueOf(histogram.get(n));
            }
        }

        // histogram.logFrequency();
        document.setHistogram(histogram);
        return TermFrequencyArray;
    }

    //calculate histogram for all documents
    private String [][]  calcHistogram(DocumentsList documentList) {
        String wholeContent = "";
        for (Document document : documentList) {
            wholeContent = wholeContent + document.getContents();
            calcHistogram(document);
        }
        return calcHistogram(new Document(0,"whole_document",wholeContent,""));
    }

    //calculate IDF
    private String[][] calcInverseDocumentFrequency(DocumentsList documentList, String[][] termDocFrequencyArray) {

        inverseDocumentFrequency = new Vector(numFeatures);
        for (Document document : documentList) {
            for (int i = 0; i < numFeatures; i++) {
                if (document.getHistogram().get(i) > 0) {
                    inverseDocumentFrequency.increment(i);
                }
            }
        }

        inverseDocumentFrequency.invert();
        inverseDocumentFrequency.multiply(documentList.size());
        inverseDocumentFrequency.log();

        for(int n = 0; n < numFeatures; n++)
        {
            termDocFrequencyArray[n][2] = String.valueOf(inverseDocumentFrequency.get(n));
        }
        return termDocFrequencyArray;
    }

    //encode document with TF-IDF
    private void encode(Document document) {

        // Normalize word histogram by maximum word frequency
        Vector vector = document.getHistogram();
        // Allow histogram to be deallocated as it is no longer needed
        document.setHistogram(null);
        //vector.divide(vector.max());
        // Normalize by inverseDocumentFrequency
        //vector.multiply(inverseDocumentFrequency);
        // Store feature vecotr in document
        document.setVector(vector);
        // Precalculate norm for use in distance calculations
        document.setNorm(vector.magnitude());

    }

    //Hash the word to normalize
    private int hashWord(String word) {

        return Math.abs(word.hashCode()) % numFeatures;
    }

    //encode all documents
    public String [][] encode(DocumentsList documentList) {
        String [][] termDocFrequencyArray = null;
        termDocFrequencyArray = calcHistogram(documentList);
        termDocFrequencyArray = calcInverseDocumentFrequency(documentList,termDocFrequencyArray);
        for (Document document : documentList) {
            encode(document);
        }
        return termDocFrequencyArray;
    }

}
