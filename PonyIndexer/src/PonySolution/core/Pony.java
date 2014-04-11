package PonySolution.core;

import PonyDB.DBReader;
import PonyDB.DBWriter;
import PonyIndexer.IndexManager;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.PageRankInfo;
import PonySearcher.Search;
import PonySearcher.VectorSpaceRankingPolicy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 *
 * @author Apostolidis
 */
public class Pony {
    
    public static Search Start(String documentPath, String stopWordsPath) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        
        String collectionIndexPath = documentPath + "\\collectionIndex";
        Search search;
        if(DBReader.indexFilesExist(collectionIndexPath)){   
            search = new Search(
                    collectionIndexPath, 
                    stopWordsPath,
                    new VectorSpaceRankingPolicy()
                );
        }else{
//            boolean success = DBWriter.createFolder(collectionIndexPath);
//            IndexManager index = IndexManager.getInstance();
//            index.indexer(  documentPath,
//                            stopWordsPath,
//                            collectionIndexPath);   
//            
//            search = new Search(
//                VocabularyInfoHolder.getInstance(),
//                collectionIndexPath, 
//                stopWordsPath,
//                new VectorSpaceRankingPolicy()
//            );
        }
        
        return null;
    }
    

    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, Exception {
        Search search = Start(
                       "D:\\\\github\\Pony\\PonyIndexer\\resources\\documentCollection", 
                       "D:\\\\github\\Pony\\PonyIndexer\\resources\\stopWords"
                   );
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("hello world");
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippets());
        }
    }
}
