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
public class NormalVocabularyTraitsRetrieval implements VocabularyTraitsRetrievalPolicy{
    private static final int totalToBeFetched = 10;
    private static final int optimizedQueryLength = 2;
    

    @Override
    public int getTotalsynonymsToBeFetched() {
        return totalToBeFetched;
    }

    @Override
    public int getTotalmeronymsToBeFetched() {
        return totalToBeFetched;
    }

    @Override
    public int getTotalhyponymsToBeFetched() {
        return totalToBeFetched;
    }

    @Override
    public int getTotalhyperonymsToBeFetched() {
        return totalToBeFetched;
    }

    @Override
    public int getTotalholonyToBeFetched() {
        return totalToBeFetched;
    }

    @Override
    public int getTotalsynonymsForOptimizedQuery() {
        return optimizedQueryLength;
    }

    @Override
    public int getTotalmeronymsForOptimizedQuery() {
        return optimizedQueryLength;
    }

    @Override
    public int getTotalhyponymsForOptimizedQuery() {
        return optimizedQueryLength;
    }

    @Override
    public int getTotalhyperonymsForOptimizedQuery() {
        return optimizedQueryLength;
    }

    @Override
    public int getTotalholonyForOptimizedQuery() {
        return optimizedQueryLength;
    }

    @Override
    public double getsynonymsCategoryWeight() {
        return 0.1;
    }

    @Override
    public double getmeronymsCategoryWeight() {
        return 0.08;
    }

    @Override
    public double gethyponymsCategoryWeight() {
        return 0.05;
    }

    @Override
    public double gethyperonymsCategoryWeight() {
        return 0.02;
    }

    @Override
    public double getholonyCategoryWeight() {
        return 0.02;
    }
}
