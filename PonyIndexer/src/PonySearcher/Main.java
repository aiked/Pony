/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 *
 * @author japostol
 */
public class Main {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, Exception{
        
        Search search = new Search("./resources/CollectionIndex/");
        PriorityQueue<PageRankInfo> retrieveAndRank = search.retrieveAndRank("light");
        
        while(!retrieveAndRank.isEmpty()){
          PageRankInfo pageRankInfo = retrieveAndRank.poll();
            System.out.println("====================");
            System.out.println(pageRankInfo.getRank());
            System.out.println(pageRankInfo.getSnippet());
        }

    }
}
