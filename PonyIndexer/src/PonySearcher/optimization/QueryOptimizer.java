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
        ArrayList<Set<ParsedQueryTerm>> queryTraits = new ArrayList(ParsedQueryTerms.size());
        ArrayList<ParsedQueryTerm> optimizedQuery = new ArrayList();
        
        generateOptimizedQuery(ParsedQueryTerms, optimizedQuery, queryTraits);
        
        ArrayList<String> relatedQueries = new ArrayList(10);
        for(int i=10-1; i!=-1; --i){
            relatedQueries.add("");
        }

        generateRelatedQueries(queryTraits, relatedQueries);
        
        return new Query(optimizedQuery, relatedQueries);
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
                optimizedQuery.addAll(allTraits);
            }
        }
    }

    private void generateRelatedQueries(
            ArrayList<Set<ParsedQueryTerm>> queryTraits, 
            ArrayList<String> relatedQueries
    ){
        int maxPowerSetIndex = 0;
        int powerSetSize = 0;
        ArrayList<Set<Set<ParsedQueryTerm>>> powerSetList = new ArrayList(queryTraits.size());
        for(int i=queryTraits.size()-1; i!=-1; --i){
            Set<Set<ParsedQueryTerm>> tmpSet = new HashSet();
            Set<Set<ParsedQueryTerm>> powerTraitSet = powerSet(queryTraits.get(i));
            for(Set<ParsedQueryTerm> powerTraits:powerTraitSet){
                if(powerTraits.size()==queryTraits.get(i).size()-1){
                    tmpSet.add(powerTraits);
                }
            }
            powerTraitSet = tmpSet;
            if(powerTraitSet.size()>powerSetSize){
                powerSetSize = powerTraitSet.size();
                maxPowerSetIndex = i;
            }
            powerSetList.add(powerTraitSet);
        }
         
        for(int i=0; i<powerSetList.size(); ++i){
            Set<Set<ParsedQueryTerm>> maxPowerSet = powerSetList.get(i);
            getTermsFromPowerSet(relatedQueries, maxPowerSet);
        }
    }
    
    private void getTermsFromPowerSet(
            ArrayList<String> relatedQueries,
            Set<Set<ParsedQueryTerm>> maxPowerSet
    ){ 
        Iterator<Set<ParsedQueryTerm>> powerSet = maxPowerSet.iterator();
        int index = 0;
        while(powerSet.hasNext() && index<relatedQueries.size()){
            Iterator<ParsedQueryTerm> termTrait = powerSet.next().iterator();
            while(termTrait.hasNext() && index<relatedQueries.size()){
                String query = relatedQueries.get(index);
                relatedQueries.set(index++, query + " " + termTrait.next().getWord());
            }
        }
    }
    
    public class Query{
        private ArrayList<ParsedQueryTerm> optimizedQuery;
        private ArrayList<String> relatedQueries;

        public Query(ArrayList<ParsedQueryTerm> optimizedQuery, ArrayList<String> relatedQueries) {
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
