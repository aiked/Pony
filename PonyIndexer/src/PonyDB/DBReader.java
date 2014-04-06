/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonyDB;

import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;

/**
 *
 * @author Apostolidis
 */
public class DBReader {
    private static DBReader instance = null;
    
    private String openPath;
    private RandomAccessFile DocumentInfoFile;
    private RandomAccessFile PostingInfoFile;
    
    private DBReader(){}
    
    public static DBReader getInstance(){
        if(instance==null){
            instance = new DBReader();
        }
        return instance;
    }
    
    public void openConnections(String path) throws FileNotFoundException, IOException{
        this.openPath = path;
        this.DocumentInfoFile = new RandomAccessFile(path + Configuration.DOCUMENT_INFO_NAME, "r");
        this.PostingInfoFile = new RandomAccessFile(path + Configuration.POSTING_INFO_NAME, "r");
    }
    
    public void closeConnections() throws IOException{
        this.DocumentInfoFile.close();
        this.PostingInfoFile.close();
    }
    
    public DocumentInfo loadDocumentInfo( long pointer ) throws IOException{
        this.DocumentInfoFile.seek(pointer);
        Long id = this.DocumentInfoFile.readLong();
        String path = this.DocumentInfoFile.readUTF();
        String type = this.DocumentInfoFile.readUTF();
        return new DocumentInfo(id, path);
    }
    
    public PostingInfoHolder loadPostingInfoHolder( long pointer ) throws IOException{
        this.PostingInfoFile.seek(pointer);
        PostingInfoHolder postingInfoHolder = new PostingInfoHolder();
        int mapSize = this.PostingInfoFile.readInt();
        for( int i=0; i<mapSize; ++i ){
            long key = this.PostingInfoFile.readLong();
            long id = this.PostingInfoFile.readLong();
            assert(key==id);
            double tf = this.PostingInfoFile.readDouble();
            int positionsSize = this.PostingInfoFile.readInt();
            PostingInfo postingInfo = new PostingInfo(id, tf);
            for(int j=0; j<positionsSize; ++j){
                postingInfo.addPosition(this.PostingInfoFile.readLong());
            }
            postingInfoHolder.add(postingInfo);
        }
        return postingInfoHolder;
    }
    
    public VocabularyInfoHolder loadVocabularyInfoHolder() throws FileNotFoundException, IOException, ClassNotFoundException{
        InputStream vocFile = new FileInputStream(this.openPath + Configuration.VOCABULARY_HOLDER_NAME);
        InputStream vocBuffer = new BufferedInputStream(vocFile);
        ObjectInput vocObject = new ObjectInputStream (vocBuffer);
        VocabularyInfoHolder vocabularyInfoHolder = (VocabularyInfoHolder) vocObject.readObject();
        return vocabularyInfoHolder;
    }
}
