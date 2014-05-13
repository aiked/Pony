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
        for(int i=0; i<sampleSize; ++i){
            String query="";
            for(int termsPerQuery=0; termsPerQuery<n; ++termsPerQuery){
                if(indexTermsIterator.hasNext()){
                    query+=indexTermsIterator.next()+" ";
                }else{
                    System.out.println("all the indexed terms has be used");
                    return;
                }
            }
            search.submitQuery(query);
            PriorityQueue<PageRankInfo> retrieveAndRank = search.getRankTerms();
            System.out.println("query terms: " + n + ", results size: " + retrieveAndRank.size());
        }
    }
}
