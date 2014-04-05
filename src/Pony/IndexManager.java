
package Pony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author jit
 */
public class IndexManager {

    private static DocumentInfoHolder docHolder     = null;
    private static VocabularyInfoHolder vocHolder   = null;
    private static StopWords stopWords              = null;
    
    public void indexer( String ResourcesFolder,
                         String StopWordsFolder,
                         String StorageFolder) throws   FileNotFoundException, 
                                                        IOException {
        
        stopWords = StopWords.getInstance();
        docHolder = DocumentInfoHolder.getInstance();
        vocHolder = VocabularyInfoHolder.getInstance();
        
        stopWords.importFromFolder(StopWordsFolder);
        List<String> fileList = ReadFilesPathFromFolder(ResourcesFolder);
        
        Long cntFile = 0L;
        Long cntWord = 0L;
        
        for ( String fileName : fileList ){
            
            docHolder.add(cntFile, new DocumentInfo(cntFile, fileName));
            ++cntFile;
            cntWord = 0L;
            
            BufferedReader reader = null; StringTokenizer tokenizer = null;
            String delimiter = "\t\n\r\f";
            String line = null, currentToken = null;
            
            reader = new BufferedReader(new FileReader(fileName));
            
            while ((line = reader.readLine()) != null){
                tokenizer = new StringTokenizer(line, delimiter);
                while(tokenizer.hasMoreTokens() ) {
                    ++cntWord;
                    currentToken = tokenizer.nextToken();
                }
            }
        }
    }
    
    
    public void indexTerm( String term, String filePath,
                            Long fileIndex, Long wordIndex ){
        
        term = stopWords.getValidTerm(term);
        
        if(term == null){
            return;
        }
        
        VocabularyInfo vocInfo = vocHolder.get(term);
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
    
    
    public List<String> ReadFilesPathFromFolder( String path ){
        
        List<String> retval = null;
        
        try{
            File folder = new File(path);
            
            for( File fp : folder.listFiles() ){
                if(fp.isDirectory()){
                    ReadFilesPathFromFolder(fp.toString());
                }
                else{
                    retval.add(fp.getPath());
                }
            }
            return retval;
            
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
        return null;
    }
    
}