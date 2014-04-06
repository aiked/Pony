package PonyDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
        
    private DBWriter(){}
    
    public static DBWriter getInstance(){
        if(instance==null){
            instance = new DBWriter();
        }
        return instance;
    }
    
    public void openConnections(String path) throws FileNotFoundException, IOException{
        this.openPath = path;
        this.DocumentInfoFile = new RandomAccessFile(path + Configuration.DOCUMENT_INFO_NAME, "w");
        this.PostingInfoFile = new RandomAccessFile(path + Configuration.POSTING_INFO_NAME, "w");
    }
    
    public void closeConnections() throws IOException{
        this.DocumentInfoFile.close();
        this.PostingInfoFile.close();
    }
        
    public long saveNextDocumentInfo( DocumentInfo documentInfo ) throws IOException{
        this.DocumentInfoFile.writeLong(documentInfo.getId());
        this.DocumentInfoFile.writeUTF(documentInfo.getPath());
        this.DocumentInfoFile.writeUTF(documentInfo.getType());
        return this.DocumentInfoFile.getFilePointer();
    }
     
    public long saveNextPostingInfoHolder( PostingInfoHolder postingInfoHolder ) throws IOException{
        HashMap<Long, PostingInfo> map = postingInfoHolder.getAllInfo();
        this.PostingInfoFile.writeInt(map.size());
        for(Entry<Long, PostingInfo> entry : map.entrySet()) {
            this.PostingInfoFile.writeLong(entry.getKey());
            PostingInfo value = entry.getValue();
            this.PostingInfoFile.writeLong(value.getId());
            this.PostingInfoFile.writeDouble(value.getTf());
            ArrayList<Long> positions = value.getPositions();
            this.PostingInfoFile.writeInt(positions.size());
            for( Long pos : positions ){
                this.PostingInfoFile.writeLong(pos);
            }
        }
        return this.PostingInfoFile.getFilePointer();
    }
    
    public void saveVocabularyInfoHolder( VocabularyInfoHolder vocabularyInfoHolder ) throws FileNotFoundException, IOException{
        OutputStream vocFile = new FileOutputStream(this.openPath + Configuration.VOCABULARY_HOLDER_NAME);
        OutputStream vocBuffer = new BufferedOutputStream(vocFile);
        ObjectOutputStream vocabularyInfoHolderFile = new ObjectOutputStream(vocBuffer);
        vocabularyInfoHolderFile.writeObject( (Object) vocabularyInfoHolder);
        vocabularyInfoHolderFile.close();
    }
}
