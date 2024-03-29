
package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        if (args.length < 3){
            System.out.println("Error: Invalid variables. Try importing these absolute paths\n"+
                    "java -jar PonyIndexer.java <DocumentCollection> <StopWords> <CollectionIndex> ");
            System.exit(1);
        }
        System.out.println("PonyIndexer");
        long startTime = System.currentTimeMillis();
        IndexManager index = IndexManager.getInstance();
        index.indexer( args[0], args[1], args[2], null );
        long finishTime = System.currentTimeMillis();
        System.out.println("All files were intexed in " + (finishTime-startTime) + " miliseconds");
    }
     
             
//        IndexManager index = IndexManager.getInstance();
//        index.indexer( 
//    D:\\github\\Pony\\PonyIndexer\\resources\\unitTest\\bundles\\bundle1 D:\\github\\Pony\\PonyIndexer\\resources\\stopWords D:\\github\\Pony\\PonyIndexer\\resources\\unitTest\\bundles\\bundles
//                "D:\\github\\Pony\\PonyIndexer\\resources\\documentCollection",
//                "D:\\github\\Pony\\PonyIndexer\\resources\\stopWords",
//                "D:\\github\\Pony\\PonyIndexer\\resources", null );
        
//        IndexManager index = IndexManager.getInstance();
//        index.indexer( 
//                "/home/michath/Github/Pony/PonyIndexer/resources/documentCollection",
//                "/home/michath/Github/Pony/PonyIndexer/resources/stopWords",
//                "/home/michath/Github/Pony/PonyIndexer/resources", null );
//        
//    }
}