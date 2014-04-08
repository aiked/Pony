package PonySearcher;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;

/**
 *
 * @author michath
 */
public interface PageRankingPolicy {
    
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQuery.ParsedQueryWord parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder);
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder);
    
}
