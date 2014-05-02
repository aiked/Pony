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
public class VectorSpaceRankingPolicy implements PageRankingPolicy{

    @Override
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQueryTerm parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder) {
        return vocabularyInfo.getIdf()*parsedQueryWord.getTf();
    }
    
    @Override
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder, VocabularyInfo vocabularyInfo) {
        return vocabularyInfo.getIdf()*value.getTf();
    }

    @Override
    public void addRank(PageRankInfo pageRankInfo, double termRank, double docRank, ParsedQueryTerm parsedQueryWord) {
        pageRankInfo.addRank(termRank*docRank);//*parsedQueryWord.getWeight());
        pageRankInfo.addDenominatorWordRank(docRank*docRank);
        pageRankInfo.addDenominatorQueryRank(termRank*termRank);
    }

    @Override
    public void calculateRank(PageRankInfo pageRankInfo) {
        pageRankInfo.setRank( 
                Math.sqrt( 
                    pageRankInfo.getDenominatorQueryRank()
                    *pageRankInfo.getDenominatorWordRank() 
                )
            );
    }
    
}
