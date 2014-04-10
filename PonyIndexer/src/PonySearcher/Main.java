package PonySearcher;

import PonyDB.DBReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
                "./resources/stopWords",
                new VectorSpaceRankingPolicy()
            );

//        DBReader db = new DBReader();
//        RandomAccessFile file = db.loadDocument("./resources/documentCollection/bigger/Discoverers and Explorers.txt");
//        System.out.println( SnippetGenerator.SnippetGenerator( file, "land", 5941L));
        
        
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("άνθωπος");
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippet());
        }
                
    }
}
