
package Dashboard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;

/**
 *
 * @author subhahs
 */
public class DashboardController implements Initializable {
  
    @FXML
    private TextField filePath;
    
    @FXML
    private TextArea stringOutput;

    @FXML
    private Button btnBrowse;  
    private Button btnIndex;


    @FXML
    void Browse(ActionEvent event) {
          
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    void Index(ActionEvent event) throws IOException {
        String filePathStr = filePath.getText();
        String fileType = getFileExtension(filePathStr);
        
          
        if(null != fileType)  {
            
            if("csv".equals(fileType)){
                System.out.println("CSV");
                String CSVFileReadLines;
                ReadCSV fileCSV = new ReadCSV();
                CSVFileReadLines = fileCSV.CSERead(filePathStr);
                stringOutput.setText(CSVFileReadLines);
            }
            
            else if ("txt".equals(fileType)){
                System.out.println("TXT");
                StringBuilder sb;
                ReadTextFile fileText = new ReadTextFile();
                sb = fileText.ReadText(filePathStr);
                stringOutput.setText(sb.toString()); 
            }
            
            else if ("pdf".equals(fileType)){
                System.out.println("PDF");
                String PDFReadLines;
                ReadPDF filePDF = new ReadPDF();
                PDFReadLines = filePDF.ReadPDFDocument(filePathStr);
                stringOutput.setText(PDFReadLines); 
            }
            
            else if ("xls".equals(fileType)){
                System.out.println("XLS");
                String XLSReadLines;
                ReadExcel fileExcel = new ReadExcel();
                XLSReadLines = fileExcel.ReadExcelDocument(filePathStr);
                stringOutput.setText(XLSReadLines);   
            }
            
            else if ("xlsx".equals(fileType)){
                System.out.println("XLSX");
                String XLSXReadLines;
                ReadExcelX fileExcelX = new ReadExcelX();
                XLSXReadLines = fileExcelX.ReadExcelDocumentX(filePathStr);
                stringOutput.setText(XLSXReadLines);
            }
            
            else if ("docx".equals(fileType)){
                System.out.println("DOCX");
                String DocxReadLines;
                ReadDocumentX fileDocX = new ReadDocumentX();
                DocxReadLines = fileDocX.ReadDocx(filePathStr);
                stringOutput.setText(DocxReadLines);
            }
            
            else if ("pptx".equals(fileType)){
                System.out.println("PPTX");
                String PPTXReadLines;
                ReadPPTX filePPTX = new ReadPPTX();
                PPTXReadLines = filePPTX.ReadPowerpointX(filePathStr);
                stringOutput.setText(PPTXReadLines);          
            }
            
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("File type is not supported");
                alert.setContentText("Please contact the administrator \n");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                filePath.setText("");
            }
        }   
    }
    
    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
