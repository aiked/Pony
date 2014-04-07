package PonySearcher;

import PonyDB.DBReader;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Apostolidis
 */
public class Search {
    DBReader dbReader;
    VocabularyInfoHolder vocabularyInfoHolder;
    RankPolicy rankPolicy;
    
    public Search(String VocabularyInfoHolderFilePath) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        
        dbReader = new DBReader();
        dbReader.openConnections(VocabularyInfoHolderFilePath);
        vocabularyInfoHolder = dbReader.loadVocabularyInfoHolder();
        this.rankPolicy = new VectorSpaceModelPolicy();
    }
        
    public SortedSet<PageRankInfo> retrieveAndRank(String query) 
                                                throws IOException{
        
        SortedSet<PageRankInfo> pagesRankInfo = 
                new TreeSet<>(new PageRankInfoComparator());
        
        String[] parsedQuery = parseQuery(query);
        
        for(String term : parsedQuery){
            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(term);
            
            if( vocabularyInfo!=null ){
                assert( vocabularyInfo.getPostHolder()==null );
                PostingInfoHolder postingInfoHolder = 
                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());
                
                Document currDoc = null;
                Document queryDoc = null;
                
                PageRankInfo rank = rankPolicy.Rank(vocabularyInfoHolder, currDoc, queryDoc);
                pagesRankInfo.add(rank);
            }
        }

        return pagesRankInfo;
    }
    
    private HashMap<Long, Document> getDocumentsThatContainsWords(String[] words) throws IOException{
        HashMap<Long, Document> documents = new HashMap();
        
        for(String term : words){
            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(term);
            
            if( vocabularyInfo!=null ){
                assert( vocabularyInfo.getPostHolder()==null );
                PostingInfoHolder postingInfoHolder = 
                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());
                
                for( PostingInfo value : postingInfoHolder.getAllInfo().values() ){
                    Document document = documents.get(value.getId());
                    DocumentWord documentWord;
                    if( document==null ){
                        document = new Document(
                                value.getId(), 
                                vocabularyInfo.getDf(), 
                                "docContent"
                            );
                    }    
                    documentWord = document.getDocumentWord(term);
                    assert(documentWord==null);
                    documentWord = new DocumentWord( 
                            term, 
                            value.getPositions(), 
                            value.getTf(), 
                            value.getTf()*document.getDf() 
                        );
                    //append
                }
                
                Document currDoc = null;
                Document queryDoc = null;
                
                PageRankInfo rank = rankPolicy.Rank(vocabularyInfoHolder, currDoc, queryDoc);
                //rank.addRank(rank);
            }
        }
        
        return documents;
    }
    
    class PageRankInfoComparator implements Comparator<PageRankInfo>{
        @Override
        public int compare(PageRankInfo a, PageRankInfo b) {
            if      (a.getRank()==b.getRank())  return 0;
            else if (a.getRank()>b.getRank())   return 1;
            else                                return -1;
        }
    }

    private String[] parseQuery(String query){
        return query.split(" ");
    }
    
    public void setRankPolicy(RankPolicy rankPolicy){
        this.rankPolicy = rankPolicy;
    }
}
