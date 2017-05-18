package Dashboard;

import java.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadPDF {

public String ReadPDFDocument(String ADDRESS_FILE)
{
    PDDocument pd;
    BufferedWriter wr;
    try {
            File input = new File(ADDRESS_FILE);  // The PDF file from where you would like to extract
            pd = PDDocument.load(input);
            int i = pd.getNumberOfPages();
            System.out.println(pd.isEncrypted());
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1); 
            stripper.setEndPage(i); 
            
            String pdfReadLines;
            pdfReadLines = stripper.getText(pd); 
            //System.out.println(pdfReadLines);
            
            if (pd != null) {
                pd.close();
            }
            
            return pdfReadLines;
    } 
    
    catch (Exception e){
    } 
    return null;
}

}