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
        

        Search search = new Search(
                "D:\\github\\Pony\\PonyIndexer\\CollectionIndex",
                "D:\\github\\Pony\\PonyIndexer\\resources\\stopWords",
                new VectorSpaceRankingPolicy()
            );
 
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("world");
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippets());
        }
                
    }
}
