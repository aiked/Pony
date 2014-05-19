package PonySearcher;

import PonySearcher.models.PageRankInfo;
import PonySearcher.ranking.VectorSpaceRankingPolicy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author japostol
 */
public class Main {
    
    public static void main(String[] args) 
            throws IOException, FileNotFoundException, ClassNotFoundException, Exception{
        
//        if (args.length < 3){
//            System.out.println("Error: Invalid variables. Try importing these absolute paths\n"+
//                    "java -jar PonySearcher.java <DocumentCollection> <StopWords> <\"Search String\"> ");
//            System.exit(1);
//        }
//        
                Search search = new Search( "C:\\github\\Pony\\PonyIndexer\\resources\\unitTest\\bundles\\bundle6db",
                                    "C:\\github\\Pony\\PonyIndexer\\resources\\stopWords"
                                     , new VectorSpaceRankingPolicy() );
//        Search search = new Search( args[0], args[1], new VectorSpaceRankingPolicy() );
        
        TestUnit.searchTermsAndResultSize(100,search);
        
        //search.submitQuery(args[2]);
        //PriorityQueue<PageRankInfo> retrieveAndRank = search.getRankTerms();
                        
        
        
//        Search search = new Search( "/home/michath/Github/Pony/PonyIndexer/resources",
//                                    "/home/michath/Github/Pony/PonyIndexer/resources/stopWords");
        
        
//        Search search = new Search( "D:\\github\\Pony\\PonyIndexer\\resources",
//                                    "D:\\github\\Pony\\PonyIndexer\\resources\\stopWords"
//                , new VectorSpaceRankingPolicy() );
//        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("injection");
 
//                Search search = new Search( "D:\\\\github\\Pony\\PonyIndexer\\resources\\documentCollection\\collectionIndex",
//                                    "D:\\\\github\\Pony\\PonyIndexer\\resources\\stopWords"
//                , new VectorSpaceRankingPolicy() );
//        search.submitQuery("αγάπη");
//        
//        PriorityQueue<PageRankInfo> retrieveAndRank = search.getRankTerms();
        
//        while(!retrieveAndRank.isEmpty()){
//            PageRankInfo pageRankInfo = retrieveAndRank.poll();
//            System.out.println("====================");
//            System.out.println(pageRankInfo.getRank());
//            System.out.println(pageRankInfo.getSnippets());
//        }
//        System.out.println("====================");
//        System.out.println("====================");
//        List<String> relatedQueries = search.getRelatedQueries();
//        for(String relatedQuery:relatedQueries){
//            System.out.println("====================");
//            System.out.println(relatedQuery);
//        }             
    }
    
    
}
