package aztec.rbir_backend.classifier;


/**
 * Created by subhahs on 03/08/2017.
 */

/* class for each document */
public class Document implements Comparable<Document> {

    private final String title;
    private final String contents;
    private final long id;
    private String category;

    // whether document has been allocated to a cluster
    private boolean allocated;
    // document word histogram
    private Vector histogram;
    // encoded document vector (TF-IDF)
    private Vector vector;
    // precalculated document vector norms
    private double norm;

    public Document(long id, String title, String contents, String category) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    //setters
    public void setVector(Vector vector) {
        this.vector = vector;
    }
    public void setIsAllocated() {
        allocated = true;
    }
    public void setHistogram(Vector histogram) {
        this.histogram = histogram;
    }
    public void setNorm(double norm) {
        this.norm = norm;
    }
    public void clearIsAllocated() {
        allocated = false;
    }
    public void setCategory(String category){this.category = category;}

    //getters
    public boolean isAllocated() {
        return allocated;
    }
    public long getId() {
        return id;
    }
    public String getContents() {
        return contents;
    }
    public Vector getVector() {
        return vector;
    }
    public Vector getHistogram() {
        return histogram;
    }
    public double getNorm() {
        return norm;
    }
    public String getCategory(){return category;}

    //compare documents ids
    public int compareTo(Document document) {
        if (id > document.getId()) {
            return 1;
        } else if (id < document.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
}
