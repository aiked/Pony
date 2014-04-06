package PonySearcher;

import PonyDB.DBReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Apostolidis
 */
public class Search {
    DBReader dbReader;

    public Search(String VocabularyInfoHolderFilePath) throws FileNotFoundException, IOException{
        dbReader = new DBReader();
        dbReader.openConnections(VocabularyInfoHolderFilePath);
    }
    
    public Result findAndRank(String query){
        
        //collect the list of list<Vocabulary, info>
        //rank the result using a interface policy
        //return
        
        return null;
    }
}
