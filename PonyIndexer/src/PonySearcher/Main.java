/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher;

import PonyDB.DBReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author japostol
 */
public class Main {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, Exception{
        
        DBReader db = new DBReader();
        RandomAccessFile file = db.loadDocument("./resources/documentCollection/bigger/Discoverers and Explorers.txt");
        System.out.println( SnippetGenerator.SnippetGenerator( file, "land", 5941L));
        
        /*
        Search search = new Search("./resources/CollectionIndex/", "./resources/documentCollection");
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("DISCOVERERS".toLowerCase());
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippet());
        }
                */
    }
}
