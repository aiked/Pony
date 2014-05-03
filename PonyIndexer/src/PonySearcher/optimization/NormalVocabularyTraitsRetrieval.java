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
    private static final int sameVal = 10;

    @Override
    public int getTotalsynonymsToBeFetched() {
        return sameVal;
    }

    @Override
    public int getTotalmeronymsToBeFetched() {
        return sameVal;
    }

    @Override
    public int getTotalhyponymsToBeFetched() {
        return sameVal;
    }

    @Override
    public int getTotalhyperonymsToBeFetched() {
        return sameVal;
    }

    @Override
    public int getTotalholonyToBeFetched() {
        return sameVal;
    }
    
}
