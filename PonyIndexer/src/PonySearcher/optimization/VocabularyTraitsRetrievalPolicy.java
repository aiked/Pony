/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher.optimization;

/**
 *
 * @author Apostolidis
 */
public interface VocabularyTraitsRetrievalPolicy {
    public int getTotalsynonymsToBeFetched();
    public int getTotalmeronymsToBeFetched();
    public int getTotalhyponymsToBeFetched();
    public int getTotalhyperonymsToBeFetched();
    public int getTotalholonyToBeFetched();
    
    public int getTotalsynonymsForOptimizedQuery();
    public int getTotalmeronymsForOptimizedQuery();
    public int getTotalhyponymsForOptimizedQuery();
    public int getTotalhyperonymsForOptimizedQuery();
    public int getTotalholonyForOptimizedQuery();
    
    public double getsynonymsCategoryWeight();
    public double getmeronymsCategoryWeight();
    public double gethyponymsCategoryWeight();
    public double gethyperonymsCategoryWeight();
    public double getholonyCategoryWeight();
}
