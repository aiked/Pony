package PonySearcher;

import PonySearcher.models.ParsedQueryTerm;
import PonySearcher.models.PageRankInfo;
import PonySearcher.ranking.PageRankingPolicy;
import PonyDB.DBReader;
import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author Apostolidis
 */
public class Search {
    private DBReader dbReader;
    private final VocabularyInfoHolder vocabularyInfoHolder;
    private PageRankingPolicy pageRankingPolicy;
    private ParsedQuery parsedQuery;
    
    public Search(String collectionIndexPath, 
            String StopWordsFolder, PageRankingPolicy pageRankingPolicy) 
            throws FileNotFoundException, IOException, ClassNotFoundException, FileNotFoundException, FileNotFoundException, IOException{
        initiateSearch(collectionIndexPath, StopWordsFolder, pageRankingPolicy);
        vocabularyInfoHolder = dbReader.loadVocabularyInfoHolderOpt();
        
    }
    
    public Search(VocabularyInfoHolder vocabularyInfoHolder, String collectionIndexPath, 
            String StopWordsFolder, PageRankingPolicy pageRankingPolicy) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        initiateSearch(collectionIndexPath, StopWordsFolder, pageRankingPolicy);
        this.vocabularyInfoHolder = vocabularyInfoHolder;        
    }
    
    private void initiateSearch(String collectionIndexPath, String StopWordsFolder, PageRankingPolicy pageRankingPolicy) 
            throws FileNotFoundException, IOException{
        dbReader = new DBReader();
        dbReader.openConnections(collectionIndexPath);
        this.pageRankingPolicy = pageRankingPolicy;
        parsedQuery = new ParsedQuery(StopWordsFolder);
    }

    public PriorityQueue<PageRankInfo> retrieveAndRank(String query) 
                                                throws IOException, Exception{

        HashMap <Long, PageRankInfo> tmpPagesRankInfo = new HashMap();

        ArrayList<ParsedQueryTerm> parsedQueryWords = parsedQuery.parse(query);
        for(ParsedQueryTerm parsedQueryWord : parsedQueryWords){
            VocabularyInfo vocabularyInfo = vocabularyInfoHolder.get(parsedQueryWord.getWord());
            if( vocabularyInfo!=null ){
               
                assert( vocabularyInfo.getPostHolder()==null );
                PostingInfoHolder postingInfoHolder = 
                        dbReader.loadPostingInfoHolder(vocabularyInfo.getPointer());

                double rankOfTerm = pageRankingPolicy.rankTerm(vocabularyInfo, parsedQueryWord, vocabularyInfoHolder, postingInfoHolder);

                for( PostingInfo postingInfo : postingInfoHolder.getAllInfo().values() ){
                    DocumentInfo documentInfo = dbReader.loadDocumentInfo(postingInfo.getId());
                    if(documentInfo==null){
                        throw new Exception("Cannot find document info. "
                                            + "docId: " + postingInfo.getId());
                    }else{
                        PageRankInfo pageRankInfo = tmpPagesRankInfo.get(postingInfo.getId());
                        if(pageRankInfo==null){
                            RandomAccessFile documentFile = dbReader.loadDocument(documentInfo.getPath());
                            pageRankInfo = new PageRankInfo(postingInfo.getId(), documentInfo.getPath(), documentFile);
                            tmpPagesRankInfo.put(postingInfo.getId(), pageRankInfo);
                        }
                        double rankOfDoc = pageRankingPolicy.rankDocument(postingInfo, documentInfo, vocabularyInfoHolder, vocabularyInfo);
                        
                        pageRankingPolicy.addRank(pageRankInfo, rankOfTerm, rankOfDoc, parsedQueryWord);
                        ArrayList<String> snippets = SnippetGenerator.generate(
                                                        pageRankInfo.getDocumentFile(), 
                                                        parsedQueryWord.getWord(), 
                                                        postingInfo.getPositions()
                                                    );
                        pageRankInfo.appendSnippets(snippets); 
                    }

                }
            }
        }
        if(tmpPagesRankInfo.isEmpty()){
            return null;
        }else{
            PriorityQueue<PageRankInfo> pagesRankInfo = new PriorityQueue(
                    tmpPagesRankInfo.size(),
                    new PageRankInfoComparator()
                );
            for(PageRankInfo pageRankInfo : tmpPagesRankInfo.values()){
                dbReader.closeDocument(pageRankInfo.getDocumentFile());
                pageRankingPolicy.calculateRank(pageRankInfo);
                pagesRankInfo.add(pageRankInfo);
            }
            return pagesRankInfo;
        }
    }
        
    public class PageRankInfoComparator implements Comparator<PageRankInfo>{
        @Override
        public int compare(PageRankInfo a, PageRankInfo b) {
            if      (a.getRank()==b.getRank())  return 0;
            else if (a.getRank()>b.getRank())   return 1;
            else                                return -1;
        }
    }

    public void setPageRankingPolicy(PageRankingPolicy pageRankingPolicy) {
        this.pageRankingPolicy = pageRankingPolicy;
    }
    
    
}
