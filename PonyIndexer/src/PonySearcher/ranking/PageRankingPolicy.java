package PonySearcher.ranking;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.models.PageRankInfo;
import PonySearcher.models.ParsedQueryTerm;

/**
 *
 * @author michath
 */
public interface PageRankingPolicy {
    
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQueryTerm parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder);
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder, VocabularyInfo vocabularyInfo);
    public void addRank(PageRankInfo pageRankInfo, double termRank, double docRank, ParsedQueryTerm parsedQueryWord);
    public void calculateRank(PageRankInfo pageRankInfo);
}
