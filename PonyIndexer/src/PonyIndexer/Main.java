
package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        IndexManager index = IndexManager.getInstance();
        
        index.indexer(  "D:\\github\\Pony\\PonyIndexer\\novels",
                        "D:\\github\\Pony\\PonyIndexer\\resources\\stopWords",
                        "D:\\github\\Pony\\PonyIndexer\\CollectionIndex",
                        null);
        
       

    }
}