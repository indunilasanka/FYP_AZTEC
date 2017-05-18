package Dashboard;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row;

public class ReadExcel {
  
    public String  ReadExcelDocument(String ADDRESS_FILE) throws IOException{
        
        String ExcelReadLines = "";
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(ADDRESS_FILE));
            
        for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {   
            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);
            for (Row row : myExcelSheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        ExcelReadLines = ExcelReadLines + "\n" + cell.getRichStringCellValue().getString();                   
                    }
                    else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        ExcelReadLines = ExcelReadLines + "\n" + cell.getNumericCellValue();
                        //System.out.println(cell.getDateCellValue());                   
                    }
                }
            } 
        }        
        return ExcelReadLines;
    } 
    
}

// need to check contact numbers and date