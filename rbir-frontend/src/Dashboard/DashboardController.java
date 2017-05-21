
package Dashboard;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import logic.WordFrequencyWithJava;

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
    private JFXButton btnBrowse; 
    
    @FXML
    private JFXButton btnIndex;
    
    @FXML
    private TitledPane keywordList;
    
    @FXML
    private VBox keywordListLayout;
    
    @FXML
    private AnchorPane addKeyword;
    
    @FXML
    private TextField keywordText;
    
    
    private ArrayList<String> keyList = new ArrayList<String>();
    private ArrayList<CheckBox> cbs = new ArrayList<CheckBox>();

    @FXML
    void Browse(ActionEvent event) throws IOException{
          
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath.setText(selectedFile.getAbsolutePath());
            
            String filePathStr = filePath.getText();
            String fileType = getFileExtension(filePathStr); 

            if(null != fileType)  {

                if("csv".equals(fileType)){
                    System.out.println("CSV");
                    String CSVFileReadLines;
                    ReadCSV fileCSV = new ReadCSV();
                    CSVFileReadLines = fileCSV.CSERead(filePathStr);
                    stringOutput.setText(CSVFileReadLines);
                    btnIndex.setDisable(false);
                }

                else if ("txt".equals(fileType)){
                    System.out.println("TXT");
                    StringBuilder sb;
                    ReadTextFile fileText = new ReadTextFile();
                    sb = fileText.ReadText(filePathStr);
                    stringOutput.setText(sb.toString()); 
                    btnIndex.setDisable(false);
                }

                else if ("pdf".equals(fileType)){
                    System.out.println("PDF");
                    String PDFReadLines;
                    ReadPDF filePDF = new ReadPDF();
                    PDFReadLines = filePDF.ReadPDFDocument(filePathStr);
                    stringOutput.setText(PDFReadLines); 
                    btnIndex.setDisable(false);
                }

                else if ("xls".equals(fileType)){
                    System.out.println("XLS");
                    String XLSReadLines;
                    ReadExcel fileExcel = new ReadExcel();
                    XLSReadLines = fileExcel.ReadExcelDocument(filePathStr);
                    stringOutput.setText(XLSReadLines); 
                    btnIndex.setDisable(false);
                }

                else if ("xlsx".equals(fileType)){
                    System.out.println("XLSX");
                    String XLSXReadLines;
                    ReadExcelX fileExcelX = new ReadExcelX();
                    XLSXReadLines = fileExcelX.ReadExcelDocumentX(filePathStr);
                    stringOutput.setText(XLSXReadLines);
                    btnIndex.setDisable(false);
                }

                else if ("docx".equals(fileType)){
                    System.out.println("DOCX");
                    String DocxReadLines;
                    ReadDocumentX fileDocX = new ReadDocumentX();
                    DocxReadLines = fileDocX.ReadDocx(filePathStr);
                    stringOutput.setText(DocxReadLines);
                    btnIndex.setDisable(false);
                }

                else if ("pptx".equals(fileType)){
                    System.out.println("PPTX");
                    String PPTXReadLines;
                    ReadPPTX filePPTX = new ReadPPTX();
                    PPTXReadLines = filePPTX.ReadPowerpointX(filePathStr);
                    stringOutput.setText(PPTXReadLines); 
                    btnIndex.setDisable(false);
                }

                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("File type is not supported");
                    alert.setContentText("Please contact the administrator \n");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(btnBrowse.getScene().getWindow());
                    alert.showAndWait();
                    filePath.setText("");
                }
            } 
            
        }
    }
    
    @FXML
    void Index(ActionEvent event) throws IOException {
          btnBrowse.setDisable(true);
          btnIndex.setDisable(true);
          String[] mostFrequentWords = WordFrequencyWithJava.getMostFrequentWords(stringOutput.getText(), 10);
          System.out.println(mostFrequentWords);
            
          keywordListLayout.setPadding(new Insets(20, 20, 30, 20));
          
          for(String str: mostFrequentWords){
              CheckBox box = new CheckBox(str);
              cbs.add(box);
              box.setOnAction(e -> handleOptions(box));
              keywordListLayout.getChildren().addAll(box);
          }
         
          keywordList.setVisible(true);
          addKeyword.setVisible(true);
          stringOutput.setVisible(false);
            
    }
    
    
    @FXML
    void AddKeyword(ActionEvent event){
       System.out.println(keywordText.getText());
       if(!keywordText.getText().equals("")){
           CheckBox box = new CheckBox(keywordText.getText());
           box.setSelected(true);
           box.setOnAction(e -> handleOptions(box));
           keyList.add(box.getText());
           cbs.add(box);
           keywordListLayout.getChildren().add(box);
       } 
       else {
           System.out.println(keyList);
       }
    }
    
    @FXML
    void SelectAll(ActionEvent e){
        keyList.clear();
        for(CheckBox box: cbs){
            box.setSelected(true);
            keyList.add(box.getText());
        }
    }
    
    @FXML
    void DeselectAll(ActionEvent e){
        keyList.clear();
        for(CheckBox box: cbs){
            box.setSelected(false);
        }
    }
    
    private static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    
    private void handleOptions(CheckBox box){
        if(box.isSelected())
            keyList.add(box.getText());
        else
            keyList.remove(box.getText());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnIndex.setDisable(true);
    }    
    
}
