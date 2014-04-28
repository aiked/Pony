
package PonyIndexer;

import PonyDB.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import Common.TermNormalizer;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author jit
 */
public class IndexManager {

    private static VocabularyInfoHolder vocHolder = null;
    private static TermNormalizer termNormalizer = null;
            
    private static IndexManager instance = null;
    
    private IndexManager(){
    
    }
    
    public static IndexManager getInstance(){
        if( instance == null){
            instance = new IndexManager();
            
            vocHolder = VocabularyInfoHolder.getInstance();
            termNormalizer = TermNormalizer.getInstance();
        }
        return instance;
    }

    public void indexer( String ResourcesFolder,
                         String StopWordsFolder,
                         String StorageFolder,
                         IndexerListener indexerListener) 
        throws FileNotFoundException, IOException {
        
        if(indexerListener!=null) indexerListener.onChangeIndexingState("Indexing...");
        StopWords stopWords = new StopWords();
        
        if(StopWordsFolder!=null)
            stopWords.importFromFolder(StopWordsFolder);
        
        ArrayList<String> fileList = DBReader.readFilesPathFromFolder(ResourcesFolder);
        vocHolder.setNumberOfDocuments((long)(fileList.size()));
        
        if(indexerListener!=null) indexerListener.onNewIndexingMsg( vocHolder.getNumberOfDocuments() + " files");
        
        long totalWordsInAllDocuments = 0L;
        long cntDocumentPointer = 0L;
        double singleDocumentPercent = (1.0/((double)vocHolder.getNumberOfDocuments()))*100;
        double cntDocumentPercent = 0.0;
        
        DBWriter DBWriterInstance = PonyDB.DBWriter.getInstance();
        DBWriterInstance.openConnections(StorageFolder);
        
        HashSet<String> documentWords = new HashSet<>();
        for ( String fileName : fileList ){
            
            Long cntWord = 0L;
            documentWords.clear();
            BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                            new FileInputStream(fileName), 
                                            "UTF-8"
                                        )
                                    );
            String line;
            while ((line = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line, TermNormalizer.DOCUMENT_TERMS_DELIMITER, true);

                while(tokenizer.hasMoreTokens() ) {
                    
                    String nextToken = tokenizer.nextToken();

                    if(!termNormalizer.isDelimiter(nextToken)){
                    //if(!nextToken.matches(TermNormalizer.IS_DOCUMENT_TERMS_DELIMITER)){
                        
                        String term = nextToken;
                        
                        term = termNormalizer.termToLowerCase(term);
                        if(termNormalizer.isTermGreek(term)){
                            term = termNormalizer.removePunctuation(term);
                        }
                        term = stopWords.getValidTerm(term);
                        
                        if(term != null && !term.isEmpty()){
                            ++totalWordsInAllDocuments;
                            term = termNormalizer.stemTerm(term);
                            //System.out.println(term);
                            indexTerm(documentWords, term, fileName, cntDocumentPointer, cntWord);
                        }
                    }
                    cntWord += (long) TermNormalizer.countUTF8Stringlength(nextToken);
                }
                cntWord+=2;
            }
            reader.close();

            DocumentInfo currDocumentInfo = new DocumentInfo(cntDocumentPointer, fileName, cntWord);
            calculateTf(documentWords, cntDocumentPointer);

            cntDocumentPointer = DBWriterInstance.saveNextDocumentInfo(currDocumentInfo);
                      
            if(indexerListener!=null) indexerListener.onPercentileLoad(cntDocumentPercent);
            cntDocumentPercent += singleDocumentPercent;
        } 
        calculateDfs();
        
        if(indexerListener!=null) indexerListener.onChangeIndexingState("Saving...");
        if(indexerListener!=null) indexerListener.onNewIndexingMsg( vocHolder.getSize() + " files");
        
        Long postingInfoHolderfilePointer = 0L;
        double singlePostingInfoHolderPercent = (1.0/((double)vocHolder.getSize()*1.05))*100;
        double cntPostingInfoHolderPercent = 0.0;
        
        for ( VocabularyInfo vocInfo : vocHolder.getMap().values()){         
            vocInfo.setPointer(postingInfoHolderfilePointer);
            postingInfoHolderfilePointer = DBWriterInstance.saveNextPostingInfoHolder
                                                    (vocInfo.getPostHolder());
            vocInfo.setPostHolder(null);
            
            if(indexerListener!=null) indexerListener.onPercentileLoad(cntPostingInfoHolderPercent);
            cntPostingInfoHolderPercent += singlePostingInfoHolderPercent;
        }
        
        vocHolder.setAvgDocumentsTerm( (double)totalWordsInAllDocuments/(double)vocHolder.getNumberOfDocuments() );
        DBWriterInstance.saveVocabularyInfoHolderOpt(vocHolder);
        
        DBWriterInstance.closeConnections();
    }
    
    private void indexTerm( HashSet<String> documentWords,
                            String term, String filePath,
                            Long fileIndex, Long wordIndex ){
        
        VocabularyInfo vocInfo = vocHolder.get(term);
        
        documentWords.add(term);
        
        if(vocInfo == null){
            
            vocInfo  = new VocabularyInfo(term);
            PostingInfo postInfo = new PostingInfo(fileIndex,wordIndex);
            
            vocInfo.addPostingInfo(postInfo);
            vocHolder.add(vocInfo);
        }
        else{
            PostingInfoHolder postHolder = vocInfo.getPostHolder();
            if( postHolder.get(fileIndex) == null){
                PostingInfo postInfo = new PostingInfo(fileIndex,wordIndex);
                vocInfo.addPostingInfo(postInfo);
            }
            else{
                postHolder.get(fileIndex).addPosition(wordIndex);
            }
        }
    }
    
    public static long  calculateDf(final VocabularyInfo vocInfo){
        return (long)(vocInfo.getPostHolder().getAllInfo().size());
    }
    
    private static double LOG_BASE_2 = Math.log(2);
    public static double calculateIdf(
            final VocabularyInfoHolder vocHolder, 
            final VocabularyInfo vocInfo){
        
        double N = vocHolder.getNumberOfDocuments();
        double df = ((double)vocInfo.getDf())==N ? vocInfo.getDf()-1 : vocInfo.getDf();
        return Math.log( N / df )/LOG_BASE_2;
    }
    
    private void calculateDfs(){
        for ( String term : vocHolder.getMap().keySet()){
            VocabularyInfo vocInfo = vocHolder.get(term);
            vocInfo.setDf( calculateDf(vocInfo) );
            vocInfo.setIdf( calculateIdf(vocHolder, vocInfo) );
        }
    } 

    private void calculateTf(HashSet<String> words, Long docId){
        
        ArrayList<PostingInfo> docs = new ArrayList<>();
        Long maxfreq = 0L;

        for ( String word : words){
            PostingInfo postInfo = vocHolder.get(word).getPostInfoByDocId(docId);
            maxfreq =  Math.max( maxfreq, ((long)(postInfo.getPositions().size())));
            docs.add(postInfo);
        }
        
        for ( PostingInfo postDoc : docs){
            postDoc.setTf( ((double) postDoc.getPositions().size()) / ((double) maxfreq) );
        }
    }
    
    public int getVocSize(){
        return vocHolder.getSize();
    }
}