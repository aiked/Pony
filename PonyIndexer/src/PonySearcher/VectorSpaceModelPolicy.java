/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher;

import PonyIndexer.VocabularyInfoHolder;

/**
 *
 * @author japostol
 */
public class VectorSpaceModelPolicy implements RankPolicy{

    @Override
    public PageRankInfo Rank(
            VocabularyInfoHolder VocabularyInfoHolder, 
            Document doc, 
            Document query) {
        
        PageRankInfo pageRankInfo = new PageRankInfo();
        
        for(DocumentWord documentWord : doc.getWords().values()){
            DocumentWord queryWord = query.getDocumentWord( documentWord.getWord() );
            assert( queryWord!=null );
            pageRankInfo.addRank( documentWord.getW()*queryWord.getW() );
            
            // TODO: add correct snippet
            // this is fake
            pageRankInfo.appendSnippet( doc.getDocumentContent().substring(0, 2) );
        }

        return pageRankInfo;
    }
    
}
