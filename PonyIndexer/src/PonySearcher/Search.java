package PonySearcher;

import PonyDB.DBReader;
import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.ParsedQuery.ParsedQueryWord;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author Apostolidis
 */
public class Search {
    private final DBReader dbReader;
    private final VocabularyInfoHolder vocabularyInfoHolder;
    private PageRankingPolicy pageRankingPolicy;
    
    public Search(String vocabularyInfoHolderFilePath, String documentsPath, PageRankingPolicy pageRankingPolicy) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        
        dbReader = new DBReader();
        dbReader.openConnections(vocabularyInfoHolderFilePath);
        vocabularyInfoHolder = dbReader.loadVocabularyInfoHolder();
        this.pageRankingPolicy = pageRankingPolicy;
    }

    public PriorityQueue<PageRankInfo> retrieveAndRank(String query) 
                                                throws IOException, Exception{

        HashMap <Long, PageRankInfo> tmpPagesRankInfo = new HashMap();
        ParsedQuery parsedQuery = new ParsedQuery(query);
        
        for(ParsedQueryWord parsedQueryWord : parsedQuery.getParsedQueryWords()){
            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(parsedQueryWord.getWord());
            if( vocabularyInfo!=null ){
               
                assert( vocabularyInfo.getPostHolder()==null );
                PostingInfoHolder postingInfoHolder = 
                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());

                double rankOfTerm = pageRankingPolicy.rankTerm(vocabularyInfo, parsedQueryWord, vocabularyInfoHolder, postingInfoHolder);

                for( PostingInfo value : postingInfoHolder.getAllInfo().values() ){
                    DocumentInfo documentInfo = dbReader.loadDocumentInfo(value.getId());
                    if(documentInfo==null){
                        throw new Exception("Cannot find document info. "
                                            + "docId: " + value.getId());
                    }else{
                        PageRankInfo pageRankInfo = tmpPagesRankInfo.get(value.getId());
                        if(pageRankInfo==null){
                            pageRankInfo = new PageRankInfo(value.getId());
                            tmpPagesRankInfo.put(value.getId(), pageRankInfo);
                        }

                        double rankOfDoc = pageRankingPolicy.rankDocument(value, documentInfo, vocabularyInfoHolder);
                        
                        pageRankInfo.addRank(rankOfTerm*rankOfDoc);
                        pageRankInfo.appendSnippet(
                                value.getPositions().toString() 
                                +  documentInfo.getPath()); 
                    }

                }
            }
        }
        PriorityQueue<PageRankInfo> pagesRankInfo = new PriorityQueue(
                tmpPagesRankInfo.size(),
                new PageRankInfoComparator()
            );
        pagesRankInfo.addAll(tmpPagesRankInfo.values());
        
        return pagesRankInfo;
    }
    
//    public PriorityQueue<PageRankInfo> retrieveAndRank(String query) 
//                                                throws IOException, Exception{
//
//        HashMap <Long, PageRankInfo> tmpPagesRankInfo = new HashMap();
//        ParsedQuery parsedQuery = new ParsedQuery(query);
//        
//        for(ParsedQueryWord parsedQueryWord : parsedQuery.getParsedQueryWords()){
//            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(parsedQueryWord.getWord());
//            if( vocabularyInfo!=null ){
//                double qIdf = vocabularyInfo.getIdf();
//                double qTf = parsedQueryWord.getTf();
//                double queryTermWeight = qIdf*qTf;
//            
//                assert( vocabularyInfo.getPostHolder()==null );
//                PostingInfoHolder postingInfoHolder = 
//                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());
//                
//                for( PostingInfo value : postingInfoHolder.getAllInfo().values() ){
//                    DocumentInfo documentInfo = dbReader.loadDocumentInfo(value.getId());
//                    if(documentInfo==null){
//                        throw new Exception("Cannot find document info. "
//                                            + "docId: " + value.getId());
//                    }else{
//                        PageRankInfo pageRankInfo = tmpPagesRankInfo.get(value.getId());
//                        if(pageRankInfo==null){
//                            pageRankInfo = new PageRankInfo(value.getId());
//                            tmpPagesRankInfo.put(value.getId(), pageRankInfo);
//                        }
//                        pageRankInfo.addRank(queryTermWeight*value.getVectorSpaceW());
//                        pageRankInfo.appendSnippet(
//                                value.getPositions().toString() 
//                                +  documentInfo.getPath()); 
//                    }
//
//                }
//            }
//        }
//        PriorityQueue<PageRankInfo> pagesRankInfo = new PriorityQueue(
//                tmpPagesRankInfo.size(),
//                new PageRankInfoComparator()
//            );
//        pagesRankInfo.addAll(tmpPagesRankInfo.values());
//        
//        return pagesRankInfo;
//    }
        
    public class PageRankInfoComparator implements Comparator<PageRankInfo>{
        @Override
        public int compare(PageRankInfo a, PageRankInfo b) {
            if      (a.getRank()==b.getRank())  return 0;
            else if (a.getRank()>b.getRank())   return 1;
            else                                return -1;
        }
    }
}
