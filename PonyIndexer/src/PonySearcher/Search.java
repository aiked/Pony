package PonySearcher;

import PonyDB.DBReader;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Apostolidis
 */
public class Search {
    DBReader dbReader;
    VocabularyInfoHolder vocabularyInfoHolder;

    public Search(String VocabularyInfoHolderFilePath) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        
        dbReader = new DBReader();
        dbReader.openConnections(VocabularyInfoHolderFilePath);
        vocabularyInfoHolder = dbReader.loadVocabularyInfoHolder();
    }
    
    public PageRankInfo retrieveAndRank(String query) throws IOException{
        
        String[] parsedQuery = parseQuery(query);
        
        for(String term : parsedQuery){
            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(term);
            assert( vocabularyInfo.getPostHolder()==null );
           if( vocabularyInfo!=null ){
                PostingInfoHolder postingInfoHolder = 
                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());
                
           }
        }
        
        //collect the list of list<Vocabulary, info>
        //rank the result using a interface policy
        //return
        
        return null;
    }
    
    private String[] parseQuery(String query){
        return query.split(" ");
    }
}
