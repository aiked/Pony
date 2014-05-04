package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import Common.StopWords;
import PonySearcher.models.ParsedQueryTerm;
import static com.google.common.collect.Sets.powerSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Apostolidis
 */
public class QueryOptimizer {
    private static final int RELATED_QUERIES_SIZE = 10;
    
    private static QueryOptimizer singleInst = null;
    
    private final VocabolaryTraitsFetcher vocabolaryTraitsFetcher;
    private final VocabularyTraitsRetrievalPolicy vocabularyTraitsRetrievalPolicy;
    
    private QueryOptimizer( 
            StopWords _stopWords, 
            VocabularyTraitsRetrievalPolicy _vocabularyTraitsRetrievalPolicy
    ) throws Exception{
        vocabularyTraitsRetrievalPolicy = _vocabularyTraitsRetrievalPolicy;
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
    
    public Query optimize(List<ParsedQueryTerm> parsedQueryTerms) throws IOException{
        List<Set<ParsedQueryTerm>> queryTraits = new ArrayList(parsedQueryTerms.size());
        List <ParsedQueryTerm> optimizedQuery = new ArrayList();
        
        generateOptimizedQuery(parsedQueryTerms, optimizedQuery, queryTraits);

        List<String> createRelatedQueriesArray = createRelatedQueriesArray();
        generateRelatedQueries(queryTraits, createRelatedQueriesArray);
        
        return new Query(optimizedQuery, createRelatedQueriesArray);
    }
    
    public List<String> createRelatedQueriesArray(){
        ArrayList<String> relatedQueries = new ArrayList(RELATED_QUERIES_SIZE);
        for(int i=RELATED_QUERIES_SIZE-1; i!=-1; --i){
            relatedQueries.add("");
        }
        return relatedQueries;
    }
    
    private void generateOptimizedQuery(
            List<ParsedQueryTerm> ParsedQueryTerms,
            List<ParsedQueryTerm> optimizedQuery,
            List<Set<ParsedQueryTerm>> queryTraits
    ) throws IOException{

        for(ParsedQueryTerm parsedQueryTerm:ParsedQueryTerms){
            VocabolaryTraitsFetcher.VocabolaryTraits vocabolaryTraits = 
                    vocabolaryTraitsFetcher.getNewVocabolaryTraits(parsedQueryTerm);
            SortedIterablePriorityQuery<ParsedQueryTerm> allTraits = vocabolaryTraits.getAll();
            if(!allTraits.isEmpty()){
                queryTraits.add(allTraits.getSet());
            
                extractOptimizeQuery(
                        vocabolaryTraits.getSynonyms(), 
                        vocabularyTraitsRetrievalPolicy.getTotalsynonymsForOptimizedQuery(), 
                        optimizedQuery
                );
                extractOptimizeQuery(
                        vocabolaryTraits.getHolonyms(), 
                        vocabularyTraitsRetrievalPolicy.getTotalholonyForOptimizedQuery(), 
                        optimizedQuery
                );
                extractOptimizeQuery(
                        vocabolaryTraits.getHyperonyms(), 
                        vocabularyTraitsRetrievalPolicy.getTotalhyperonymsForOptimizedQuery(), 
                        optimizedQuery
                );
                extractOptimizeQuery(
                        vocabolaryTraits.getHyponymns(), 
                        vocabularyTraitsRetrievalPolicy.getTotalhyponymsForOptimizedQuery(), 
                        optimizedQuery
                );
                extractOptimizeQuery(
                        vocabolaryTraits.getMeronyms(), 
                        vocabularyTraitsRetrievalPolicy.getTotalmeronymsForOptimizedQuery(), 
                        optimizedQuery
                );
            }
            if(!optimizedQuery.contains(parsedQueryTerm)){
                optimizedQuery.add(parsedQueryTerm);
            }
        }
    }
    
    private void extractOptimizeQuery(
            SortedIterablePriorityQuery<ParsedQueryTerm> synonyms, 
            int totalsynonymsForOptimizedQuery, 
            List<ParsedQueryTerm> optimizedQuery
    ) {
        while(totalsynonymsForOptimizedQuery-->=0 && !synonyms.isEmpty()){
            optimizedQuery.add(synonyms.poll());
        }
    }
    
    private void generateRelatedQueries(
            List<Set<ParsedQueryTerm>> queryTraits, 
            List<String> relatedQueries
    ){
        ArrayList<Set<Set<ParsedQueryTerm>>> powerSetList = new ArrayList(queryTraits.size());
        for(Set<ParsedQueryTerm> queryTrait : queryTraits){
            Set<Set<ParsedQueryTerm>> tmpSet = new HashSet();
            Set<Set<ParsedQueryTerm>> powerTraitSet = powerSet(queryTrait);
            for(Set<ParsedQueryTerm> powerTraits:powerTraitSet){
                if(powerTraits.size()==queryTrait.size()-1){
                    tmpSet.add(powerTraits);
                }
            }
            powerSetList.add(tmpSet);
        }
        for(Set<Set<ParsedQueryTerm>> maxPowerSet:powerSetList){
            getTermsFromPowerSet(relatedQueries, maxPowerSet);
        }
    }
    
    private void getTermsFromPowerSet(
            List<String> relatedQueries,
            Set<Set<ParsedQueryTerm>> maxPowerSet
    ){ 
        Iterator<Set<ParsedQueryTerm>> powerSet = maxPowerSet.iterator();
        int index = 0;
        while(powerSet.hasNext() && index<relatedQueries.size()){
            Iterator<ParsedQueryTerm> termTrait = powerSet.next().iterator();
            while(termTrait.hasNext() && index<relatedQueries.size()){
                String query = relatedQueries.get(index);
                relatedQueries.set(index, query + " " + termTrait.next().getWord());
                ++index;
            }
        }
    }
    
    public class Query{
        private List<ParsedQueryTerm> optimizedQuery;
        private List<String> relatedQueries;

        public Query(List<ParsedQueryTerm> optimizedQuery, List<String> relatedQueries) {
            this.optimizedQuery = optimizedQuery;
            this.relatedQueries = relatedQueries;
        }

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
