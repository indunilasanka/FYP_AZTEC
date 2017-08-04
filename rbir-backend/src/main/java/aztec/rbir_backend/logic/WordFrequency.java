package aztec.rbir_backend.logic;

/**
 * Created by subhahs on 28/05/2017.
 */

import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;


public class WordFrequency {

    public static String[] getMostFrequentWords(String content, int numOfWords) {

        HashMap<String,Integer> wordMap = new HashMap<String,Integer>();

        System.out.println(content);
        content = preprocess(content);
        System.out.println(content);

        String[] contentArray = content.split("\\s+");
        addWordsToMap(contentArray, wordMap);
        wordMap = sortByValue(wordMap);

        String[] tokens = {};
        tokens = wordMap.keySet().toArray(tokens);
        //remove common words
        String[] returnTokens = new String[numOfWords];
        if(tokens.length < numOfWords)
            return tokens;

        for(int i = 0; i < numOfWords; i++){
            returnTokens[i] = tokens[i];
        }
        return  returnTokens;
    }


    private static String preprocess(String content){
        String cleanedStr = content.replaceAll("[^a-zA-Z ]", " ").toLowerCase();
        System.out.println(cleanedStr);

        // --- with lucene library --- //
      /*  Set stopWords = StandardAnalyzer.STOP_WORDS_SET;
        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_30, new StringReader(cleanedStr.trim()));

        tokenStream = new StopFilter(Version.LUCENE_30, tokenStream, stopWords);
        tokenStream = new PorterStemFilter(tokenStream);

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
        return sb.toString();  */

        try {
            cleanedStr = ExudeData.getInstance().filterStoppingsKeepDuplicates(cleanedStr);
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

        return cleanedStr;
    }

    private static void addWordsToMap(String[] contentArray, Map<String, Integer> wordMap) {
        for (String string : contentArray) {
            Integer freq = wordMap.get(string);
            if(freq == null) {
                wordMap.put(string,1);
            }else {
                wordMap.replace(string,freq+1);
            }
        }
    }

    private static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
