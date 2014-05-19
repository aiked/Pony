package PonySearcher;

import Common.StopWords;
import PonyDB.DBReader;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.models.PageRankInfo;
import PonySearcher.models.ParsedQueryTerm;
import PonySearcher.optimization.NormalVocabularyTraitsRetrieval;
import PonySearcher.optimization.QueryOptimizer;
import PonySearcher.ranking.PageRankingPolicy;
import PonySearcher.ranking.Rank;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Apostolidis
 */
public class Search {
    private DBReader dbReader;
    private final VocabularyInfoHolder vocabularyInfoHolder;
    private StopWords stopWords;
    private ParsedQuery parsedQuery;
    private Rank ranker;
    private QueryOptimizer queryOptimizer;
    private PriorityQueue<PageRankInfo> rankTerms;
    private List<String> relatedQueries;
    
    public Search(
            String collectionIndexPath, 
            String StopWordsFolder, 
            PageRankingPolicy pageRankingPolicy
    ) throws FileNotFoundException, IOException, ClassNotFoundException, FileNotFoundException, FileNotFoundException, IOException, Exception{
        initiateSearch(collectionIndexPath, StopWordsFolder);
        vocabularyInfoHolder = dbReader.loadVocabularyInfoHolderOpt();
        ranker = new Rank(vocabularyInfoHolder, dbReader, pageRankingPolicy);
        
    }
    
    public Search(
            VocabularyInfoHolder vocabularyInfoHolder, 
            String collectionIndexPath, 
            String StopWordsFolder, 
            PageRankingPolicy pageRankingPolicy
    ) throws FileNotFoundException, IOException, ClassNotFoundException, Exception{
        initiateSearch(collectionIndexPath, StopWordsFolder);
        this.vocabularyInfoHolder = vocabularyInfoHolder;  
        ranker = new Rank(vocabularyInfoHolder, dbReader, pageRankingPolicy);
    }
    
    private void initiateSearch(
            String collectionIndexPath, 
            String StopWordsFolder
    ) throws FileNotFoundException, IOException, Exception{
        dbReader = new DBReader();
        dbReader.openConnections(collectionIndexPath);
        stopWords = new StopWords();
        if(StopWordsFolder!=null)
            stopWords.importFromFolder(StopWordsFolder);
        parsedQuery = new ParsedQuery(stopWords);
        QueryOptimizer.singletonCreate(stopWords, new NormalVocabularyTraitsRetrieval());
        queryOptimizer = QueryOptimizer.getSingleInst();
    }

    public PriorityQueue<PageRankInfo> getRankTerms(){
        return this.rankTerms;
    }
    
    public List<String> getRelatedQueries(){
        return this.relatedQueries;
    }
    
    public void submitQuery(String query) 
        throws IOException, Exception{

        ArrayList<ParsedQueryTerm> parsedQueryWords = parsedQuery.parse(query);
  
        //System.out.println("afterparse: "+parsedQueryWords);
        // Optimized flow.
//        QueryOptimizer.Query optimizedQuery = queryOptimizer.optimize(parsedQueryWords);
//        relatedQueries = optimizedQuery.getRelatedQueries();
//        rankTerms = ranker.rankTerms( optimizedQuery.getOptimizedQuery() );
        
//        // Unoptimized flow, evaluation.
        rankTerms = ranker.rankTerms( parsedQueryWords );
        
    }

    public void setPageRankingPolicy(PageRankingPolicy pageRankingPolicy) {
        ranker.setPageRankingPolicy(pageRankingPolicy);
    }
    
    public VocabularyInfoHolder getVocabularyInfoHolder(){
        return this.vocabularyInfoHolder;
    }
}
