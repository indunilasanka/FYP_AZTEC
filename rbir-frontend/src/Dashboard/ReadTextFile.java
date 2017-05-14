package Dashboard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadTextFile {
    
    public StringBuilder ReadText(String ADDRESS_FILE) throws FileNotFoundException, IOException
    {
        
        try (BufferedReader br = new BufferedReader(new FileReader(ADDRESS_FILE))) {

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (line != null) {
                    sb.append(System.lineSeparator());
                }
            }
            
            return sb;
        }
    }
  
}