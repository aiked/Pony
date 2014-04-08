package PonySearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 *
 * @author japostol
 */
public class Main {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, Exception{
        
        Search search = new Search(
                "./resources/CollectionIndex/", 
                "./resources/documentCollection", 
                new VectorSpaceRankingPolicy()
            );
        
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("DISCOVERERS".toLowerCase());
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippet());
        }

    }
}
