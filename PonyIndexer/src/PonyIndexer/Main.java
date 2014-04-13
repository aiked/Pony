
package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
//        if (args.length < 3){
//            System.out.println("Error: Invalid variables. Try importing these absolute paths\n"+
//                    "java -jar PonyIndexer.java <DocumentCollection> <StopWords> <CollectionIndex> ");
//            System.exit(1);
//        }
//        System.out.println("PonyIndexer");
//        IndexManager index = IndexManager.getInstance();
//        index.indexer( args[0], args[1], args[2], null );
//        System.out.println("All files were intexed.");
       
        
        IndexManager index = IndexManager.getInstance();
        index.indexer( 
                "/Users/jit/Github/Pony/PonyIndexer/resources/documentCollection",
                "/Users/jit/Github/Pony/PonyIndexer/resources/stopWords",
                "/Users/jit/Github/Pony/PonyIndexer/resources", null );
        
    }
}