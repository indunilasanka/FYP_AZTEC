package Dashboard;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {

    public String CSERead(String ADDRESS_FILE) throws FileNotFoundException, IOException
    {
        CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE));
        String[] nextLine;        
        String nextLine2 = "";

        while ((nextLine = reader.readNext()) != null) {
            nextLine2 = nextLine2 +"\n"+ nextLine[0];
        }
        return nextLine2;
    }    
}