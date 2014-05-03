package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import Common.StopWords;
import PonySearcher.models.ParsedQueryTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Apostolidis
 */
public class QueryOptimizer {
    private static QueryOptimizer singleInst = null;
    
    private final VocabolaryTraitsFetcher vocabolaryTraitsFetcher;
    
    private QueryOptimizer( 
            StopWords _stopWords, 
            VocabularyTraitsRetrievalPolicy _vocabularyTraitsRetrievalPolicy
    ) throws Exception{
        VocabolaryTraitsFetcher.singletonCreate(_stopWords, _vocabularyTraitsRetrievalPolicy);
        vocabolaryTraitsFetcher = VocabolaryTraitsFetcher.getSingleInst();
    }
    
    public static void singletonCreate(            
            StopWords _stopWords, 
            VocabularyTraitsRetrievalPolicy _vocabularyTraitsRetrievalPolicy
    ) throws Exception{
        if(singleInst==null)
            singleInst = new QueryOptimizer(_stopWords, _vocabularyTraitsRetrievalPolicy);
    }
    
    public static QueryOptimizer getSingleInst(){
        return singleInst;
    }
    
    public Query optimize(List<ParsedQueryTerm> ParsedQueryTerms) throws IOException{
        Query query = new Query();
        for(ParsedQueryTerm parsedQueryTerm:ParsedQueryTerms){
            VocabolaryTraitsFetcher.VocabolaryTraits vocabolaryTraits = 
                    vocabolaryTraitsFetcher.getNewVocabolaryTraits(parsedQueryTerm);
            SortedIterablePriorityQuery<ParsedQueryTerm> allTraits = vocabolaryTraits.getAll();
            if(!allTraits.isEmpty()){
                query.optimizedQuery.addAll(allTraits);
                
                
            }
        }
        return query;
    }
    
    public class Query{
        private ArrayList<ParsedQueryTerm> optimizedQuery;
        private ArrayList<String> relatedQueries;
        
        public Query(){
            optimizedQuery = new ArrayList();
            relatedQueries = new ArrayList();
        }
        
        public List<ParsedQueryTerm> getOptimizedQuery(){
            return optimizedQuery;
        }

        public List<String> getRelatedQueries(){
            return relatedQueries;
        }
    }

}
