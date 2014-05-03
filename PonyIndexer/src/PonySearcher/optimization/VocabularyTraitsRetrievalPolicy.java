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
}
