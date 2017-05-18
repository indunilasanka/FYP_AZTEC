package Dashboard;

import java.io.FileInputStream; 
import java.io.IOException; 
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class ReadExcelX { 
    
    public String ReadExcelDocumentX(String ADDRESS_FILE) throws IOException{ 
        
        String ExcelXReadLines = "";
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(ADDRESS_FILE));
            
        for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) { 
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);

            for (Row row : myExcelSheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        ExcelXReadLines = ExcelXReadLines + "\n" + cell.getRichStringCellValue().getString();
                        //System.out.println(cell.getRichStringCellValue().getString());                   
                    }
                    else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        ExcelXReadLines = ExcelXReadLines + "\n" + cell.getNumericCellValue();   
                        //System.out.println(cell.getDateCellValue());    
                    }
                }
            } 
        } 
        return ExcelXReadLines;
    }
} 

// need to check contact numbers and date

