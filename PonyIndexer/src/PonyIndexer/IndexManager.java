
package PonyIndexer;

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

    private static DocumentInfoHolder docHolder     = null;
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
        docHolder = DocumentInfoHolder.getInstance();
        vocHolder = VocabularyInfoHolder.getInstance();
        
        stopWords.importFromFolder(StopWordsFolder);
        
        List<String> fileList = new ArrayList<>();
        Long cntDocument = 0L;
        Long cntWord = 0L;
        
        ReadFilesPathFromFolder(fileList, ResourcesFolder);
                
       
        for ( String fileName : fileList ){
            
            docHolder.add(cntDocument, new DocumentInfo(cntDocument, fileName));
            ++cntDocument;
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
            calculateTf(documentWords, cntDocument);
        } 
        calculateDf();
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
        for ( String term : vocHolder.getMap().keySet()){
            VocabularyInfo vocInfo = vocHolder.get(term);
            vocInfo.setDf( ((long)(vocInfo.getPostHolder().getAllInfo().size())));
        }
    }
    

    public void calculateTf(HashSet<String> words, Long docId){
        
        ArrayList<PostingInfo> docs = new ArrayList<>();
        Long maxfreq = 0L;
        PostingInfo postInfo = null;
        
        for ( String word : words){
            postInfo = vocHolder.get(word).getPostHolder().get(docId);
            maxfreq =  Math.max( maxfreq, ((long)(postInfo.getPositions().size())));
            docs.add(postInfo);
        }
        
        for ( PostingInfo postDoc : docs){
            postDoc.setTf(((double) (postDoc.getPositions().size() / maxfreq)));
        }
    }
            
            
    public void ReadFilesPathFromFolder(    List<String> fileList,
                                                    String path ){
        
        try{
            File folder = new File(path);
            
            for( File fp : folder.listFiles() ){
                if(fp.isDirectory()){
                    ReadFilesPathFromFolder(fileList, fp.toString());
                }
                else{
                    fileList.add(fp.getPath());
                }
            }
            
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
    }
    
   /* -------- Debug, to be removed -------------*/
    
    public int getVocabularySize(){
        return vocHolder.getSize();
    }
    
    public void printAllTerms(){
        vocHolder = VocabularyInfoHolder.getInstance();
        
        for( String i : vocHolder.getMap().keySet()){
            System.out.println(i);
        }
    }
        
    public void f00( String term ){
        docHolder = DocumentInfoHolder.getInstance();
        vocHolder = VocabularyInfoHolder.getInstance();
        
        VocabularyInfo vocInfo = vocHolder.get(term);
        PostingInfoHolder postHolder = vocInfo.getPostHolder();
        
        System.out.println( "Vocabulary Info" +
                            "\t Term: "+vocInfo.getTerm() +
                            "\t df: "+vocInfo.getDf());
        
        HashMap<Long,PostingInfo> postInfoMap = postHolder.getAllInfo();
              
        for( Long i : postInfoMap.keySet()){
            
            PostingInfo postInfo = postInfoMap.get(i);
            DocumentInfo docInfo = docHolder.get(postInfo.getId());
            ArrayList<Long> docPositions = postInfo.getPositions();
            
            System.out.println("\n\tPath:"+docInfo.getPath());
            for( Long pos : docPositions){
                System.out.println("\t\t"+pos);
            }
            
        }
                            
    }
}