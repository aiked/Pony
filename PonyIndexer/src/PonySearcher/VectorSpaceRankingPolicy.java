package PonySearcher;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.ParsedQuery.ParsedQueryWord;

/**
 *
 * @author michath
 */
public class VectorSpaceRankingPolicy implements PageRankingPolicy{

    @Override
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQueryWord parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder) {
        return vocabularyInfo.getIdf()*parsedQueryWord.getTf();
    }
    
    @Override
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder, VocabularyInfo vocabularyInfo) {
        return vocabularyInfo.getIdf()*value.getTf();
    }
    
}
