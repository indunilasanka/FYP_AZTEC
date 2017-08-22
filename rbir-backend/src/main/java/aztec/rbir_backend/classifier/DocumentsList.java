package aztec.rbir_backend.classifier;

import aztec.rbir_backend.indexer.Terms;
import aztec.rbir_backend.logic.FileReaderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by subhahs on 03/08/2017.
 */

/*documents to be clustered */
public class DocumentsList extends ArrayList<Document> {


    public DocumentsList(File folder) {

        File[] listOfFiles = folder.listFiles();
        Terms terms = new Terms();

        long index = 1;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                long documentID = index;
                String title = file.getName();
                String contents = terms.getTerms(file.getPath());
                String category = title.substring(title.lastIndexOf('_')+1,title.lastIndexOf('.'));
                index++;
                add(new Document(documentID, title, contents, category));
            }
        }


       /* StringTokenizer st = new StringTokenizer(input, "{");
        int numDocuments = 1;//st.countTokens() - 1;
        String record = st.nextToken(); // skip empty split to left of {
        Pattern pattern =
                Pattern.compile("\"content\": \"(.*)\", \"id\": (.*), \"title\": \"(.*)\"");
        System.out.println("try");
        for (int i = 0; i < numDocuments; i++) {
            record = st.nextToken();
            Matcher matcher = pattern.matcher(record);
            if (matcher.find()) {
                long documentID = Long.parseLong(matcher.group(2));
                String title = matcher.group(3);
                String contents = matcher.group(1);
                add(new Document(documentID, title, contents));
            }
        }
        */
    }



    /**
     * Mark all documents as not being allocated to a cluster
     */
    public void clearIsAllocated() {
        for (Document document : this) {
            document.clearIsAllocated();
            document.setCategory(null);
        }
    }

}
