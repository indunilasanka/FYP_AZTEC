
package Dashboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFComments;
import org.apache.poi.xslf.usermodel.XSLFNotes;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class ReadPPTX {
   
   public String ReadPowerpointX(String ADDRESS_FILE) throws FileNotFoundException, IOException
   {
      File file = new File(ADDRESS_FILE);
      XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
      String PPTXReadLines = "";
      XSLFSlide[] slide = ppt.getSlides();
      
      for (int i = 0; i < slide.length; i++){
         
         XSLFShape[] sh = slide[i].getShapes();         
         String title = slide[i].getTitle();
         XSLFNotes notes = slide[i].getNotes();
         XSLFComments comments = slide[i].getComments();

         for (int j = 0; j < sh.length; j++){
             PPTXReadLines = PPTXReadLines + "\n" + sh[j].getShapeName();
         }
         
         for (XSLFShape shape: sh) {
            if (shape instanceof XSLFTextShape) {
            XSLFTextShape textShape = (XSLFTextShape)shape;
            String text = textShape.getText();
            PPTXReadLines = PPTXReadLines + "\n" + text;
            }
        }
         
        PPTXReadLines = PPTXReadLines + "\n" + title; 
        
        if(notes != null)
        {
            PPTXReadLines = PPTXReadLines + "\n" + notes.toString();
        }
        
        if(comments != null)
        {
            PPTXReadLines = PPTXReadLines + "\n" + comments.toString();
        }  
      }
      
      return PPTXReadLines;
    }
}