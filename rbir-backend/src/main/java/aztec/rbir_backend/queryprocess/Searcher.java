package aztec.rbir_backend.queryprocess;

import aztec.rbir_backend.globals.Global;
import aztec.rbir_backend.indexer.Terms;
import io.swagger.models.auth.In;

import java.util.*;

/**
 * Created by subhahs on 10/06/2017.
 */
public class Searcher {
    public static Set<String> searchOWQ(String query){
        System.out.println("searcchOWQ");
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> index = Global.getHashtableIndex();
        String tokenStream = Terms.getTermsQuery(query);
        if(index.containsKey(tokenStream))
            return index.get(tokenStream).keySet();
        else
            return null;
    }

    public static Set<String> searchFTQ(String query){
        System.out.println("searcchFTQ");
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> index = Global.getHashtableIndex();
        String tokenStream = Terms.getTermsQuery(query);
        String[] tokens = tokenStream.split(" ");
        Set<String> tokenSet = new HashSet<String>();
        for(String str: tokens)
            tokenSet.add(str);

        Set<String> docs = new HashSet<String>();
        for(String token: tokenSet){
            if(index.containsKey(token)){
                docs.addAll(index.get(token).keySet());
            }
        }
        return docs;
    }

    public static Set<String> searchPQ(String query){
        System.out.println("searcchPQ");
        Hashtable<String,Hashtable<String,ArrayList<Integer>>> index = Global.getHashtableIndex();
        String tokenStream = Terms.getTermsQuery(query);
        String[] tokens = tokenStream.split(" ");
       // Set<String> tokenSet = new HashSet<String>();
        ArrayList<String> tokenSet = new ArrayList<String>();
        for(String str: tokens)
            tokenSet.add(str);
        Set<String> docs = new HashSet<String>();
        int j = 0;
        for(String token: tokenSet){
            if(index.containsKey(token)){
                if(j == 0)
                    docs = index.get(token).keySet();
                else {
                    docs.retainAll(index.get(token).keySet());
                }
            }
            else
                return null;
            j++;
        }

        Set<String> resultDocs = new HashSet<String>();

        for(String docId: docs){
            int i = 0;
            ArrayList<Integer> positionList = new ArrayList<Integer>();
            Integer position = 0;
            for(String token: tokenSet){
                positionList = index.get(token).get(docId);
                if (i == 0) {
                    position = positionList.get(0);
                } else {
                    // ArrayList<Integer> positons = index.get(token).get(docId);
                    ListIterator<Integer> iterator = positionList.listIterator();
                    Integer tempPos = position;
                    while (iterator.hasNext()) {
                        Integer temp = iterator.next();
                        if (temp > position) {
                            position = temp;
                            break;
                        }
                    }
                    // positionList.retainAll(positons);
                    if (position == tempPos)
                        break;
                }

                i++;
            }
            if(i == tokenSet.size()){
                resultDocs.add(docId);
            }
        }
        return resultDocs;

    }
}
