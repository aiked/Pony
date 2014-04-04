/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Pony;

import java.io.IOException;


/**
 *
 * @author jit
 */
public class Search {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //StopWords WordsGR = new StopWords("./Resources/stopwordsGr.txt", "Greek");
        //StopWords WordsEN = new StopWords("./Resources/stopwordsEn.txt", "English");
        
        //WordsGR.PrintWords();
        //WordsEN.PrintWords();
        
        DocumentHolder DocHolder = DocumentHolder.getInstance();
        
        DocHolder.ReadFilesFromFolder("./Resources");
        DocHolder.PrintMap();
    }
    
}
