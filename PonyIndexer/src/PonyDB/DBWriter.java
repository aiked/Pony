package PonyDB;

import static PonyDB.Configuration.*;
import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfo;
import PonyIndexer.VocabularyInfoHolder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
    
    FileChannel documentChannel;
    ByteBuffer documentBuffer = ByteBuffer.allocate(S_BUFFER);
    private long documentPointer = 0L;
    
    FileChannel postingChannel;
    ByteBuffer postingBuffer = ByteBuffer.allocate(S_BUFFER);
    private long postingPointer = 0L;
    
    FileChannel vocabularyChannel;
    ByteBuffer vocabularyBuffer = ByteBuffer.allocate(S_BUFFER);
    private long vocabularyPointer = 0L;
   
    private DBWriter(){}
    
    public static DBWriter getInstance(){
        if(instance==null){
            instance = new DBWriter();
        }
        return instance;
    }
    
    private static void writeAndClearBuffer(FileChannel channel, ByteBuffer buffer) throws IOException{
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
    }
    
    public void openConnections(String path) throws FileNotFoundException, 
                                                    IOException{
        DBWriter.createFolder(path);
        openPath = path + System.getProperty("file.separator");
        
        documentInfoFile = new RandomAccessFile(openPath + Configuration.DOCUMENT_INFO_NAME, "rw");
        documentInfoFile.setLength(0);
        documentChannel = documentInfoFile.getChannel();
        
        postingInfoFile = new RandomAccessFile(openPath + Configuration.POSTING_INFO_NAME, "rw");
        postingInfoFile.setLength(0);
        postingChannel = postingInfoFile.getChannel();
        
        vocabularyFile = new RandomAccessFile(openPath + Configuration.VOCABULARY_HOLDER_NAME, "rw");
        vocabularyFile.setLength(0);
        vocabularyChannel = vocabularyFile.getChannel();

    }
    
    public void closeConnections() throws IOException{
        
        documentBuffer.flip();
        documentChannel.write(documentBuffer);
        documentChannel.close();
        documentInfoFile.close();
        
        postingBuffer.flip();
        postingChannel.write(postingBuffer);
        postingChannel.close();
        postingInfoFile.close();
        
        vocabularyBuffer.flip();
        vocabularyChannel.write(vocabularyBuffer);
        vocabularyChannel.close();
        vocabularyFile.close();
    }

    public long saveNextDocumentInfo( DocumentInfo documentInfo ) 
                                            throws IOException{
        short pathSize = (short)documentInfo.getPath().getBytes("UTF-8").length;
        short typeSize = (short)documentInfo.getType().getBytes("UTF-8").length;
        
        documentBuffer.putLong(documentInfo.getId());
        documentBuffer.putShort(pathSize);
        documentBuffer.put(documentInfo.getPath().getBytes("UTF-8"));
        documentBuffer.putShort(typeSize);
        documentBuffer.put(documentInfo.getType().getBytes("UTF-8"));
        documentBuffer.putLong(documentInfo.getTotalTerm());
        
        documentPointer +=  2*SIZE_LONG + 2*SIZE_UTF8 + pathSize + typeSize;
        
        if(documentBuffer.position()>S_WRITE_LIMIT){
            documentBuffer.flip();
            documentChannel.write(documentBuffer);
            documentBuffer.clear();
        }
        return documentPointer;
    }
    
    public long saveNextPostingInfoHolder( PostingInfoHolder postingInfoHolder ) 
                                            throws IOException{
        HashMap<Long, PostingInfo> map = postingInfoHolder.getAllInfo();
        
        postingBuffer.putInt(map.size());
        postingPointer += SIZE_INT;
        for(Entry<Long, PostingInfo> entry : map.entrySet()) {
            postingBuffer.putLong(entry.getKey());
            PostingInfo value = entry.getValue();
            postingBuffer.putLong(value.getId());
            postingBuffer.putDouble(value.getTf());
            ArrayList<Long> positions = value.getPositions();
            postingBuffer.putInt(positions.size());
            postingPointer += 2*SIZE_LONG+SIZE_INT+SIZE_DOUBLE+positions.size()*SIZE_LONG;
            for( Long pos : positions ){
                postingBuffer.putLong(pos);
                if(postingBuffer.position()>S_WRITE_LIMIT){
                    writeAndClearBuffer(postingChannel,postingBuffer);
                }
            }
        }
        if(postingBuffer.position()>S_WRITE_LIMIT){
            writeAndClearBuffer(postingChannel,postingBuffer);
        }
        return postingPointer;
    }
    
    public  void saveVocabularyInfoHolderOpt( VocabularyInfoHolder vocabularyInfoHolder ) 
                        throws FileNotFoundException, IOException{
       
        VocabularyInfoHolder vocHolder = VocabularyInfoHolder.getInstance();
        HashMap<String,VocabularyInfo> map = vocHolder.getVocMap();
        long mapLength = map.size();
        vocabularyBuffer.putLong(vocHolder.getNumberOfDocuments());
        vocabularyBuffer.putDouble(vocHolder.getAvgDocumentsTerm());
        vocabularyBuffer.putLong(mapLength);
        vocabularyPointer += SIZE_LONG+SIZE_DOUBLE+SIZE_LONG;
        for(Entry<String,VocabularyInfo> entry : map.entrySet()){
            VocabularyInfo value = entry.getValue();
            byte[] key = entry.getKey().getBytes("UTF-8");
            vocabularyBuffer.putShort((short)key.length);
            vocabularyBuffer.put(key);
            vocabularyBuffer.putLong(value.getDf());
            vocabularyBuffer.putDouble(value.getIdf());
            vocabularyBuffer.putLong(value.getPointer());
            vocabularyPointer += SIZE_UTF8+key.length+SIZE_LONG+SIZE_DOUBLE+SIZE_LONG;
            if(vocabularyBuffer.position()>S_WRITE_LIMIT){
                    writeAndClearBuffer(vocabularyChannel,vocabularyBuffer);
            }
        }
    }
    
    public static boolean createFolder(String path){
        File file = new File(path);
        return file.mkdir();
    }
}