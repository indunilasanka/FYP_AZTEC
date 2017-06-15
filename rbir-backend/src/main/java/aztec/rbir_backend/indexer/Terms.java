package aztec.rbir_backend.indexer;

/**
 * Created by subhahs on 28/05/2017.
 */

import aztec.rbir_backend.logic.FileReaderFactory;
import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;


public class Terms {

    public static String getTerms(String filePath) {
        String content = FileReaderFactory.read(filePath);
        if(content == null)
            return "Invalid File Type";
        content = preprocess(content);
        return content.trim();
    }

    public static String getTermsQuery(String query) {
        String terms = preprocess(query);
        return terms.trim();
    }

    private static String preprocess(String content){
        String cleanedStr = content.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
        System.out.println(cleanedStr);

        EnglishAnalyzer analyzer = new EnglishAnalyzer();

        TokenStream tokenStream = analyzer.tokenStream("test",new StringReader(cleanedStr.trim()));
        tokenStream = new StopFilter(tokenStream,EnglishAnalyzer.getDefaultStopSet());
       // System.out.println(tokenStream);
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

}
