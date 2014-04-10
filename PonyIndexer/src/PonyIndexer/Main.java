
package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        IndexManager index = IndexManager.getInstance();
        
        index.indexer(  "./resources/documentCollection",
                        "./resources/stopWords",
                        "./resources/CollectionIndex/");
        
       

    }
}