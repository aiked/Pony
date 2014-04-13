/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonyDB;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Apostolidis
 */
public class DBReader {

    private String openPath;
    private RandomAccessFile documentInfoFile;
    private RandomAccessFile postingInfoFile;
    private RandomAccessFile vocabularyFile;
    
    public DBReader(){}
    
    public void openConnections(String path) throws FileNotFoundException, IOException{
        openPath = path + System.getProperty("file.separator");
        documentInfoFile = new RandomAccessFile(openPath + Configuration.DOCUMENT_INFO_NAME, "r");
        postingInfoFile = new RandomAccessFile(openPath + Configuration.POSTING_INFO_NAME, "r");
        vocabularyFile = new RandomAccessFile(openPath + Configuration.VOCABULARY_HOLDER_NAME, "r");
    }
    
    public void closeConnections() throws IOException{
        documentInfoFile.close();
        postingInfoFile.close();
        vocabularyFile.close();
    }
    
    public static boolean indexFilesExist(String path){
        String openPath = path + System.getProperty("file.separator");
        File folder = new File(openPath);
        if(folder.exists()){
            File documentFile = new File( openPath + Configuration.DOCUMENT_INFO_NAME );
            File postingFile = new File( openPath + Configuration.POSTING_INFO_NAME );
            File vodabularyFile = new File( openPath + Configuration.VOCABULARY_HOLDER_NAME );
            assert( !documentFile.exists() || (documentFile.exists() && postingFile.exists() && vodabularyFile.exists())  );
            return documentFile.exists();
        }
        return false;
    } 
    
    public DocumentInfo loadDocumentInfo( long pointer ) throws IOException{
        documentInfoFile.seek(pointer);
        Long id = documentInfoFile.readLong();
        String path = documentInfoFile.readUTF();
        String type = documentInfoFile.readUTF();
        Long totalTerm = documentInfoFile.readLong();
        return new DocumentInfo(id, path, totalTerm);
    }
    
    public PostingInfoHolder loadPostingInfoHolder( long pointer ) throws IOException{
        postingInfoFile.seek(pointer);
        PostingInfoHolder postingInfoHolder = new PostingInfoHolder();
        int mapSize = postingInfoFile.readInt();
        for( int i=0; i<mapSize; ++i ){
            long key = postingInfoFile.readLong();
            long id = postingInfoFile.readLong();
            assert(key==id);
            double tf = postingInfoFile.readDouble();
            int positionsSize = postingInfoFile.readInt();
            PostingInfo postingInfo = new PostingInfo(id, tf);
            for(int j=0; j<positionsSize; ++j){
                postingInfo.addPosition(postingInfoFile.readLong());
            }
            postingInfoHolder.add(postingInfo);
        }
        return postingInfoHolder;
    }
    
    public VocabularyInfoHolder loadVocabularyInfoHolder() 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        InputStream vocFile = new FileInputStream(openPath + Configuration.VOCABULARY_HOLDER_NAME);
        InputStream vocBuffer = new BufferedInputStream(vocFile);
        ObjectInput vocObject = new ObjectInputStream (vocBuffer);
        VocabularyInfoHolder vocabularyInfoHolder = (VocabularyInfoHolder) vocObject.readObject();
        return vocabularyInfoHolder;
    }
    
    public VocabularyInfoHolder loadVocabularyInfoHolderOpt() 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        
            VocabularyInfoHolder vocabularyInfoHolder = VocabularyInfoHolder.getInstance();
            
            vocabularyInfoHolder.setNumberOfDocuments(vocabularyFile.readLong());
            vocabularyInfoHolder.setAvgDocumentsTerm(vocabularyFile.readDouble());
            
            long mapLength = vocabularyFile.readLong();
            
            for(long i=0; i<mapLength; ++i){
                short termSize = vocabularyFile.readShort();
                byte [] bTerm = new byte[termSize];
                vocabularyFile.read(bTerm);
                String term = new String(bTerm, "UTF-8");
                long df = vocabularyFile.readLong();
                double idf = vocabularyFile.readDouble();
                long pointer = vocabularyFile.readLong();
                
                VocabularyInfo vocInfo = new VocabularyInfo(term,df,idf,pointer);
                vocabularyInfoHolder.add(vocInfo);
            }
            
            return vocabularyInfoHolder;
    }
            
    public RandomAccessFile loadDocument(String path) throws FileNotFoundException{
        return new RandomAccessFile(path, "r");
    }
    
    public void closeDocument(RandomAccessFile file) throws IOException{
        file.close();
    }
    
    private static void ReadFilesPathFromFolder(
                                        List<String> fileList,
                                        String path ){
         try{
            File folder = new File(path);
            for( File fp : folder.listFiles() ){
                if(fp.isDirectory()){
                    ReadFilesPathFromFolder(fileList, fp.getAbsolutePath());
                }
                else{
                    fileList.add(fp.getAbsolutePath());
                }
            }
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
    }
    public static ArrayList<String> readFilesPathFromFolder( String path ){
        ArrayList<String> files = new ArrayList<>();
        ReadFilesPathFromFolder(files, path);
        return files;
    }
    
    public static String readFile(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException{
            BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                            new FileInputStream(path), 
                                            "UTF-8"
                                        )
                                    );
            StringBuilder strFile = new StringBuilder();
            String line;
            while( (line = reader.readLine())!=null ){
                strFile.append( line );
                strFile.append( '\n' );
            }
            reader.close();
            return strFile.toString();
    }
}