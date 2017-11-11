package aztec.rbir_backend.clustering;


import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.indexer.Terms;
import aztec.rbir_backend.logic.FileReaderFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by subhahs on 03/08/2017.
 */

/*documents to be clustered */
public class DocumentsList extends ArrayList<Document> {

    public DocumentsList(ArrayList<MultipartFile> files, ArrayList<String> categories) {

        for (int i=0; i<files.size(); i++){
            String fullfilename = files.get(i).getOriginalFilename();
            String filename = fullfilename.substring(fullfilename.lastIndexOf('/')+1,fullfilename.lastIndexOf('.'));
            String fileextention = fullfilename.substring(fullfilename.lastIndexOf('.')+1);
            System.out.println(fullfilename);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String newFileName = filename+"_"+dateFormat.format(date)+"."+fileextention;
            System.out.println(newFileName);

            File dir = new File(Global.path+"nonIndexedFiles");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File newFile = new File(dir+"/"+newFileName);

            System.out.println(newFile.getAbsolutePath());
            try {
                files.get(i).transferTo(newFile);
                long documentID = Global.getLastDocId();
                String title = filename;
                String filePath = newFile.getAbsolutePath();
                String content = Terms.getTerms(filePath);
                String preprocessedContent = Terms.preprocess(content);
                String type = FileReaderFactory.IdentifyType(filePath,content);
                String category = categories.get(i);

                add(new Document(documentID, title, type, content, preprocessedContent, category, filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DocumentsList(ArrayList<MultipartFile> files) {

        for (int i=0; i<files.size(); i++){
            String fullfilename = files.get(i).getOriginalFilename();
            String filename = fullfilename.substring(fullfilename.lastIndexOf('/')+1,fullfilename.lastIndexOf('.'));
            String fileextention = fullfilename.substring(fullfilename.lastIndexOf('.')+1);
            System.out.println(fullfilename);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String newFileName = filename+"_"+dateFormat.format(date)+"."+fileextention;
            System.out.println(newFileName);

            File dir = new File(Global.path+"nonIndexedFiles");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File newFile = new File(dir+"/"+newFileName);

            System.out.println(newFile.getAbsolutePath());
            try {
                files.get(i).transferTo(newFile);
                long documentID = Global.getLastDocId();
                String title = filename;
                String filePath = newFile.getAbsolutePath();
                String content = Terms.getTerms(filePath);
                String preprocessedContent = Terms.preprocess(content);
                String type = FileReaderFactory.IdentifyType(filePath,content);
                add(new Document(documentID, title, type, content,preprocessedContent,null, filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DocumentsList(){
        super();
    }



    /**
     * Mark all documents as not being allocated to a cluster
     */
    public void clearIsAllocated() {
        for (Document document : this) {
            document.clearIsAllocated();
            //document.setCategory(null);
        }
    }

}
