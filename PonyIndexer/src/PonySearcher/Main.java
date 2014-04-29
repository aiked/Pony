package PonySearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
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
//        Search search = new Search( args[0], args[1], new VectorSpaceRankingPolicy() );
//        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank(args[2]);
        
//        Search search = new Search( "D:\\github\\Pony\\PonyIndexer\\resources",
//                                    "D:\\github\\Pony\\PonyIndexer\\resources\\stopWords"
//                , new VectorSpaceRankingPolicy() );
//        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("injection");
 
                Search search = new Search( "/home/michath/Github/Pony/PonyIndexer/resources",
                                    "/home/michath/Github/Pony/PonyIndexer/resources/stopWords"
                , new VectorSpaceRankingPolicy() );
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("injection:0.2 daddy:1.2 daddi:0.662312 lola:0.1 lola:0.3 xazos malakismeno lola:0.a vasd: don't:23");
        
        
        while(!retrieveAndRank.isEmpty()){
            PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippets());
        }
                
    }
}
