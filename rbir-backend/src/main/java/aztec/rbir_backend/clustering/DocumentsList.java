package aztec.rbir_backend.clustering;

import aztec.rbir_backend.indexer.Terms;
import aztec.rbir_backend.logic.FileReaderFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by subhahs on 03/08/2017.
 */

/*documents to be clustered */
public class DocumentsList extends ArrayList<Document> {

    private static final String FILES_TO_INDEX_DIRECTORY = "fileStore";

    public DocumentsList(ArrayList<MultipartFile> files, ArrayList<String> categories) {

        for (int i=0; i<files.size(); i++){
            String fullfilename = files.get(i).getOriginalFilename();
            String filename = fullfilename.substring(fullfilename.lastIndexOf('\\')+1,fullfilename.lastIndexOf('.'));
            String fileextention = fullfilename.substring(fullfilename.lastIndexOf('.')+1);
            System.out.println(fullfilename);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String newFileName = filename+"_"+dateFormat.format(date)+"."+fileextention;
            System.out.println(newFileName);

            File newFile = new File(Paths.get(FILES_TO_INDEX_DIRECTORY) + newFileName);
            try {
                files.get(i).transferTo(newFile);
                long documentID = i;
                String title = newFile.getName();
                String contents = Terms.getTerms(newFile.getAbsolutePath());
                String category = categories.get(i);
                String filePath = newFile.getAbsolutePath();
                add(new Document(documentID, title, contents, category, filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
