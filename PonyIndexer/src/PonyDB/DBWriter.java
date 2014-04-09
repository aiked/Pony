package PonyDB;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
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
    private RandomAccessFile DocumentInfoFile;
    private RandomAccessFile PostingInfoFile;
    private RandomAccessFile VocabularyFile;
        
    private DBWriter(){}
    
    public static DBWriter getInstance(){
        if(instance==null){
            instance = new DBWriter();
        }
        return instance;
    }
    
    public void openConnections(String path) throws FileNotFoundException, 
                                                    IOException{
        this.openPath = path;
        this.DocumentInfoFile = new RandomAccessFile(path + Configuration.DOCUMENT_INFO_NAME, "rw");
        this.PostingInfoFile = new RandomAccessFile(path + Configuration.POSTING_INFO_NAME, "rw");
        this.VocabularyFile = new RandomAccessFile(path + Configuration.VOCABULARY_HOLDER_NAME, "rw");
    }
    
    public void closeConnections() throws IOException{
        this.DocumentInfoFile.close();
        this.PostingInfoFile.close();
        this.VocabularyFile.close();
    }
        
    public long saveNextDocumentInfo( DocumentInfo documentInfo ) 
                                            throws IOException{
        this.DocumentInfoFile.writeLong(documentInfo.getId());
        this.DocumentInfoFile.writeUTF(documentInfo.getPath());
        this.DocumentInfoFile.writeUTF(documentInfo.getType());
        this.DocumentInfoFile.writeLong(documentInfo.getTotalTerm());
        return this.DocumentInfoFile.getFilePointer();
    }
     
    public long saveNextPostingInfoHolder( PostingInfoHolder postingInfoHolder ) 
                                            throws IOException{
        HashMap<Long, PostingInfo> map = postingInfoHolder.getAllInfo();
        this.PostingInfoFile.writeInt(map.size());
        for(Entry<Long, PostingInfo> entry : map.entrySet()) {
            this.PostingInfoFile.writeLong(entry.getKey());
            PostingInfo value = entry.getValue();
            this.PostingInfoFile.writeLong(value.getId());
            this.PostingInfoFile.writeDouble(value.getTf());
            this.PostingInfoFile.writeDouble(value.getVectorSpaceW());
            ArrayList<Long> positions = value.getPositions();
            this.PostingInfoFile.writeInt(positions.size());
            for( Long pos : positions ){
                this.PostingInfoFile.writeLong(pos);
            }
        }
        return this.PostingInfoFile.getFilePointer();
    }
    
    public void saveVocabularyInfoHolder( VocabularyInfoHolder vocabularyInfoHolder ) 
                        throws FileNotFoundException, IOException{
        OutputStream vocFile = new FileOutputStream(this.openPath + Configuration.VOCABULARY_HOLDER_NAME);
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
        
        this.VocabularyFile.writeLong(vocHolder.getNumberOfDocuments());
        this.VocabularyFile.writeDouble(vocHolder.getAvgDocumentsTerm());
        this.VocabularyFile.writeLong(mapLength);
        
        for(Entry<String,VocabularyInfo> entry : map.entrySet()){
            VocabularyInfo value = entry.getValue();
            
            this.VocabularyFile.writeUTF(entry.getKey());
            
            this.VocabularyFile.writeLong(value.getDf());
            this.VocabularyFile.writeDouble(value.getIdf());
            this.VocabularyFile.writeLong(value.getPointer());
                    
        }
    }
    
    
}
