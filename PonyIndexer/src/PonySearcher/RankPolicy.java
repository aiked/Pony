package PonySearcher;

import PonyIndexer.VocabularyInfoHolder;


/**
 *
 * @author Apostolidis
 */
public interface RankPolicy {

    public PageRankInfo Rank(
            VocabularyInfoHolder VocabularyInfoHolder, 
            Document doc, 
            Document query
    );
    
}
