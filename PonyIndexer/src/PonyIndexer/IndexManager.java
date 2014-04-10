
package PonyIndexer;

import PonyDB.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import Common.TermNormalizer;

/**
 *
 * @author jit
 */
public class IndexManager {
    private static final String DOCUMENT_TERMS_DELIMITER = "\t\r\f ";
    private static final String IS_DOCUMENT_TERMS_DELIMITER = "(\t|\r|\f| )*";
    
    private static VocabularyInfoHolder vocHolder = null;
    private static TermNormalizer termNormalizer = null;
            
    private static IndexManager instance = null;
    
    private IndexManager(){}
    
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
                         String StorageFolder) throws   FileNotFoundException, 
                                                        IOException {
        
        StopWords stopWords = new StopWords();
        
        stopWords.importFromFolder(StopWordsFolder);
        ArrayList<String> fileList = DBReader.ReadFilesPathFromFolder(ResourcesFolder);
        vocHolder.setNumberOfDocuments((long)(fileList.size()));
        
        long totalWordsInAllDocuments = 0L;
        long cntDocument = 0L;
        DBWriter DBWriterInstance = PonyDB.DBWriter.getInstance();
        DBWriterInstance.openConnections(StorageFolder);
        
        HashSet<String> documentWords = new HashSet<>();
        for ( String fileName : fileList ){
            
            Long cntWord = 0L;
            documentWords.clear();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            
            String line;
            while ((line = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line, DOCUMENT_TERMS_DELIMITER, true);

                while(tokenizer.hasMoreTokens() ) {
                    
                    String nextToken = tokenizer.nextToken();
                    
                    if(!nextToken.matches(IS_DOCUMENT_TERMS_DELIMITER)){
                        
                        String term = nextToken;
                        
                        term = termNormalizer.termToLowerCase(term);
                        if(termNormalizer.isTermGreek(term)){
                            term = termNormalizer.removePunctuation(term);
                        }
                        term = stopWords.getValidTerm(term);
                        
                        if(term != null && !term.isEmpty()){
                            ++totalWordsInAllDocuments;
                            term = termNormalizer.stemTerm(term);
                            indexTerm(documentWords, term, fileName, cntDocument, cntWord);
                        }
                    }
                    cntWord += (long)(nextToken.length());
                }
                ++cntWord;
            }
            reader.close();

            DocumentInfo currDocumentInfo = new DocumentInfo(cntDocument, fileName, cntWord);
            calculateTf(documentWords, cntDocument);

            cntDocument = DBWriterInstance.saveNextDocumentInfo(currDocumentInfo);            
        } 
        calculateDfs();
        
        Long postingInfoHolderfilePointer = 0L;
        for ( VocabularyInfo vocInfo : vocHolder.getMap().values()){         
            vocInfo.setPointer(postingInfoHolderfilePointer);
            postingInfoHolderfilePointer = DBWriterInstance.saveNextPostingInfoHolder
                                                    (vocInfo.getPostHolder());
            vocInfo.setPostHolder(null);
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
    
    public static double calculateIdf(
            final VocabularyInfoHolder vocHolder, 
            final VocabularyInfo vocInfo){
        
        double base2 = Math.log(2);
        double N = vocHolder.getNumberOfDocuments();
        return Math.log( N / ((double)vocInfo.getDf()) ) / base2;
    }
    
    private void calculateDfs(){
        for ( String term : vocHolder.getMap().keySet()){
            VocabularyInfo vocInfo = vocHolder.get(term);
            vocInfo.setDf( calculateDf(vocInfo) );
            vocInfo.setIdf( calculateIdf(vocHolder, vocInfo) );
            
            PostingInfoHolder postHolder = vocInfo.getPostHolder();
            for( Long id : postHolder.getAllInfo().keySet()){
                PostingInfo postInfo = postHolder.get(id);
            }
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