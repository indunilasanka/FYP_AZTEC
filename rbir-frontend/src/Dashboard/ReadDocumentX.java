package Dashboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadDocumentX {
   
   public String ReadDocx(String ADDRESS_FILE) throws FileNotFoundException, IOException
   {
       String ReadDocxLines;
       XWPFDocument docx = new XWPFDocument(new FileInputStream(ADDRESS_FILE));
       XWPFWordExtractor we = new XWPFWordExtractor(docx);
       ReadDocxLines = we.getText();
       return ReadDocxLines;
   }
}