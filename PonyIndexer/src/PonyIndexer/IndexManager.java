
package PonyIndexer;

import PonyDB.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author jit
 */
public class IndexManager {

    private static VocabularyInfoHolder vocHolder   = null;
    private static StopWords stopWords              = null;
    
    private static IndexManager instance = null;
    
    private IndexManager(){}
    
    public static IndexManager getInstance(){
        if( instance == null){
            instance = new IndexManager();
        }
        return instance;
    }
    
    public void indexer( String ResourcesFolder,
                         String StopWordsFolder,
                         String StorageFolder) throws   FileNotFoundException, 
                                                        IOException {
        
        stopWords = StopWords.getInstance();
        vocHolder = VocabularyInfoHolder.getInstance();
        
        stopWords.importFromFolder(StopWordsFolder);
        
        List<String> fileList = new ArrayList<>();
        DocumentInfo currDocumentInfo = null;
        
        Long cntDocument = 0L;
        Long cntWord = 0L;
        
        DBWriter DBWriterInstance = PonyDB.DBWriter.getInstance();
        DBWriterInstance.openConnections(StorageFolder);
        
        DBReader.ReadFilesPathFromFolder(fileList, ResourcesFolder);
        
        vocHolder.setNumberOfDocuments((long)(fileList.size()));
        
        for ( String fileName : fileList ){
            
            cntWord = 0L;
            
            BufferedReader reader = null; StringTokenizer tokenizer = null;
            String delimiter = "\t\n\r\f ";
            String line = null, term = null;
            HashSet<String> documentWords = new HashSet<>();
            
            reader = new BufferedReader(new FileReader(fileName));
            
            while ((line = reader.readLine()) != null){
                tokenizer = new StringTokenizer(line, delimiter);
                while(tokenizer.hasMoreTokens() ) {
                    
                    ++cntWord;
                    term = stopWords.getValidTerm(tokenizer.nextToken().toLowerCase());
                    
                    if(term != null){
                        
                        indexTerm(documentWords, term, fileName, cntDocument, cntWord);
                    }
                }
            }
            reader.close();

            currDocumentInfo = new DocumentInfo(cntDocument, fileName, cntWord);
            calculateTf(documentWords, cntDocument);
            
            
            cntDocument = DBWriterInstance.saveNextDocumentInfo(currDocumentInfo);
            
        } 
        calculateDf();
        
        for ( String term : vocHolder.getMap().keySet()){
            VocabularyInfo vocInfo =  vocHolder.get(term);
            
            Long filePointer = DBWriterInstance.saveNextPostingInfoHolder
                                                    (vocInfo.getPostHolder());
            vocInfo.setPointer(filePointer);
            vocInfo.setPostHolder(null);
        }
        DBWriterInstance.saveVocabularyInfoHolder(vocHolder);
        
        DBWriterInstance.closeConnections();
    }
    
    
    public void indexTerm( HashSet<String> documentWords,
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
    
    
    public void calculateDf(){
        
        Double base2 = Math.log(2);
        Long N = vocHolder.getNumberOfDocuments();
        
        for ( String term : vocHolder.getMap().keySet()){
            VocabularyInfo vocInfo = vocHolder.get(term);
            vocInfo.setDf((long)(vocInfo.getPostHolder().getAllInfo().size()));
            vocInfo.setIdf((double)(Math.log((N/vocInfo.getDf()))/base2));
            
            PostingInfoHolder postHolder = vocInfo.getPostHolder();
            for( Long id : postHolder.getAllInfo().keySet()){
                PostingInfo postInfo = postHolder.get(id);
                postInfo.setVectorSpaceW(vocInfo.getIdf()*postInfo.getTf());
            }
        }
    }
    

    public void calculateTf(HashSet<String> words, Long docId){
        
        ArrayList<PostingInfo> docs = new ArrayList<>();
        Long maxfreq = 0L;
        PostingInfo postInfo = null;
        
        for ( String word : words){
            postInfo = vocHolder.get(word).getPostInfoByDocId(docId);
            maxfreq =  Math.max( maxfreq, ((long)(postInfo.getPositions().size())));
            docs.add(postInfo);
        }
        
        for ( PostingInfo postDoc : docs){
            postDoc.setTf(((double) (postDoc.getPositions().size() / maxfreq)));
        }
    }
    
}