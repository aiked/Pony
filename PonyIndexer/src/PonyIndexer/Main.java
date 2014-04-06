/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonyIndexer;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        

        IndexManager index = IndexManager.getInstance();
        
        //StopWords words = StopWords.getInstance();
        //words.importFromFolder("./resources/stopWords");
        //words.printWords();
        
        index.indexer(  "./resources/documentCollection",
                        "./resources/stopWords",
                        "undefined");
    
        }
    
}
