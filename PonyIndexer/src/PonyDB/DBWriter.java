package PonyDB;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Apostolidis
 */
public class DBWriter {
    private static DBWriter instance = null;
    
    private String openPath;
    private RandomAccessFile documentInfoFile;
    private RandomAccessFile postingInfoFile;
    private RandomAccessFile vocabularyFile;
        
    private DBWriter(){}
    
    public static DBWriter getInstance(){
        if(instance==null){
            instance = new DBWriter();
        }
        return instance;
    }
    
    public void openConnections(String path) throws FileNotFoundException, 
                                                    IOException{
        openPath = path + "\\";
        documentInfoFile = new RandomAccessFile(openPath + Configuration.DOCUMENT_INFO_NAME, "rw");
        documentInfoFile.setLength(0);
        postingInfoFile = new RandomAccessFile(openPath + Configuration.POSTING_INFO_NAME, "rw");
        postingInfoFile.setLength(0);
        vocabularyFile = new RandomAccessFile(openPath + Configuration.VOCABULARY_HOLDER_NAME, "rw");
        vocabularyFile.setLength(0);
    }
    
    public void closeConnections() throws IOException{
        documentInfoFile.close();
        postingInfoFile.close();
        vocabularyFile.close();
    }
        
    public long saveNextDocumentInfo( DocumentInfo documentInfo ) 
                                            throws IOException{
        documentInfoFile.writeLong(documentInfo.getId());
        documentInfoFile.writeUTF(documentInfo.getPath());
        documentInfoFile.writeUTF(documentInfo.getType());
        documentInfoFile.writeLong(documentInfo.getTotalTerm());
        return documentInfoFile.getFilePointer();
    }
     
    public long saveNextPostingInfoHolder( PostingInfoHolder postingInfoHolder ) 
                                            throws IOException{
        HashMap<Long, PostingInfo> map = postingInfoHolder.getAllInfo();
        postingInfoFile.writeInt(map.size());
        for(Entry<Long, PostingInfo> entry : map.entrySet()) {
            postingInfoFile.writeLong(entry.getKey());
            PostingInfo value = entry.getValue();
            postingInfoFile.writeLong(value.getId());
            postingInfoFile.writeDouble(value.getTf());
            ArrayList<Long> positions = value.getPositions();
            postingInfoFile.writeInt(positions.size());
            for( Long pos : positions ){
                postingInfoFile.writeLong(pos);
            }
        }
        return postingInfoFile.getFilePointer();
    }
    
    public void saveVocabularyInfoHolder( VocabularyInfoHolder vocabularyInfoHolder ) 
                        throws FileNotFoundException, IOException{
        OutputStream vocFile = new FileOutputStream(openPath + Configuration.VOCABULARY_HOLDER_NAME);
        OutputStream vocBuffer = new BufferedOutputStream(vocFile);
        ObjectOutputStream vocabularyInfoHolderFile = new ObjectOutputStream(vocBuffer);
        vocabularyInfoHolderFile.writeObject( (Object) vocabularyInfoHolder);
        vocabularyInfoHolderFile.close();
    }
    
    public  void saveVocabularyInfoHolderOpt( VocabularyInfoHolder vocabularyInfoHolder ) 
                        throws FileNotFoundException, IOException{
       
        VocabularyInfoHolder vocHolder = VocabularyInfoHolder.getInstance();
        HashMap<String,VocabularyInfo> map = vocHolder.getVocMap();
        long mapLength = map.size();
        
        vocabularyFile.writeLong(vocHolder.getNumberOfDocuments());
        vocabularyFile.writeDouble(vocHolder.getAvgDocumentsTerm());
        vocabularyFile.writeLong(mapLength);
        
        for(Entry<String,VocabularyInfo> entry : map.entrySet()){
            VocabularyInfo value = entry.getValue();
            
            vocabularyFile.writeUTF(entry.getKey());
            
            vocabularyFile.writeLong(value.getDf());
            vocabularyFile.writeDouble(value.getIdf());
            vocabularyFile.writeLong(value.getPointer());
                    
        }
    }
    
    public static boolean createFolder(String path){
        File file = new File(path);
        return file.mkdir();
    }
}
