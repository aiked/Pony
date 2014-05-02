package PonySearcher.ranking;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.ParsedQuery;
import PonySearcher.models.PageRankInfo;
import PonySearcher.models.ParsedQueryTerm;

/**
 *
 * @author michath
 */
public class OkapiRankingPolicy  implements PageRankingPolicy{

    @Override
    public double rankTerm(VocabularyInfo vocabularyInfo, ParsedQueryTerm parsedQueryWord, VocabularyInfoHolder vocabularyInfoHolder, PostingInfoHolder postingInfoHolder) {
        double size = postingInfoHolder.getAllInfo().size();
        return ( (double) ( vocabularyInfoHolder.getNumberOfDocuments() - size + 0.5 ))
                / ( size + 0.5 );
    }

    @Override
    public double rankDocument(PostingInfo value, DocumentInfo documentInfo, VocabularyInfoHolder vocabularyInfoHolder, VocabularyInfo vocabularyInfo) {
        return value.getTf() * ( 2.0 + 0.75 ) 
            / value.getTf() + 2.0 * ( 1 - 0.75 + 0.75 * ( documentInfo.getTotalTerm() / vocabularyInfoHolder.getAvgDocumentsTerm() ) );
    }

    @Override
    public void addRank(PageRankInfo pageRankInfo, double termRank, double docRank, ParsedQueryTerm parsedQueryWord) {
        pageRankInfo.addRank(termRank*docRank);
    }

    @Override
    public void calculateRank(PageRankInfo pageRankInfo) {
        
    }
    
}
