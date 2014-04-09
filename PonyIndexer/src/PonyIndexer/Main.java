
package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        IndexManager index = IndexManager.getInstance();
        
        index.indexer(  "/home/michath/Github/Pony/PonyIndexer/resources/documentCollection",
                        "/home/michath/Github/Pony/PonyIndexer/resources/stopWords",
                        "/home/michath/Github/Pony/PonyIndexer/resources/CollectionIndex/");
        
       

    }
}