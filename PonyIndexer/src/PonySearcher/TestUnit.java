/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher;

import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.models.PageRankInfo;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Apostolidis
 */
public class TestUnit {
    
    public static void searchTermsAndResultSize(int sampleSize, Search search) throws Exception{
        
        VocabularyInfoHolder vocabularyInfoHolder = search.getVocabularyInfoHolder();
        Set<String> indexedTerms = vocabularyInfoHolder.getMap().keySet();
       
        termsPerQuery(1, sampleSize, indexedTerms, search);
        termsPerQuery(2, sampleSize, indexedTerms, search);
        termsPerQuery(3, sampleSize, indexedTerms, search);
        termsPerQuery(4, sampleSize, indexedTerms, search);
    }
    
    private static void termsPerQuery(int n, int sampleSize, Set<String> indexedTerms, Search search) throws Exception{
        Iterator<String> indexTermsIterator = indexedTerms.iterator();
        for(int e=10000;e>0;--e) indexTermsIterator.next();
        for(int i=sampleSize-1; i!=-1 ; --i){
            String query="";
            for(int termsPerQuery=0; termsPerQuery<n; ++termsPerQuery){
                if(indexTermsIterator.hasNext()){
                    query+=indexTermsIterator.next()+" ";
                }else{
                    System.out.println("all the indexed terms has be used");
                    return;
                }
            }
            
            long startTime = System.currentTimeMillis();
            //System.out.println("searching: "+query);
            search.submitQuery(query);
            long finishTime = System.currentTimeMillis();
            
            PriorityQueue<PageRankInfo> retrieveAndRank = search.getRankTerms();
            if(retrieveAndRank==null){
                ++i;
                continue;
            }
            System.out.println( n + "," + retrieveAndRank.size()+","+(finishTime-startTime));
        }
    }
}
