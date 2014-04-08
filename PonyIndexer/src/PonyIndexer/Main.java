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

    public static void main(String[] args) throws IOException {
        
        IndexManager index = IndexManager.getInstance();

        index.indexer(  "./resources/documentCollection",
                        "./resources/stopWords",
                        "./resources/CollectionIndex/");
    
    }
    
        
    
}
