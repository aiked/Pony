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
public interface PageRankingPolicy {
    
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQuery.ParsedQueryWord parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder);
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder, VocabularyInfo vocabularyInfo);
    public void addRank(PageRankInfo pageRankInfo, double termRank, double docRank, ParsedQueryWord parsedQueryWord);
    public void calculateRank(PageRankInfo pageRankInfo);
}
