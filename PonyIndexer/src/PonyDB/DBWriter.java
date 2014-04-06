package PonyDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import PonyIndexer.DocumentInfo;
import PonyIndexer.PostingInfoHolder;
import PonyIndexer.VocabularyInfoHolder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.io.OutputStream;

/**
 *
 * @author Apostolidis
 */
public class DBWriter {
    private static DBWriter instance = null;
    
    private String openPath;
    private ObjectOutput DocumentInfoFile;
    private ObjectOutput PostingInfoFile;
    
    private static final String DOCUMENT_INFO_NAME = "document.bin";
    private static final String POSTING_INFO_NAME = "posting.bin";
    private static final String VOCABULARY_HOLDER_NAME = "vocabulary.bin";
    
    private DBWriter(){}
    
    public static DBWriter getInstance(){
        if(instance==null){
            instance = new DBWriter();
        }
        return instance;
    }
    
    public void openConnections(String path) throws FileNotFoundException, IOException{
        this.openPath = path;
        OutputStream docFile = new FileOutputStream(path + DOCUMENT_INFO_NAME);
        OutputStream docBuffer = new BufferedOutputStream(docFile);
        this.DocumentInfoFile = new ObjectOutputStream(docBuffer);

        OutputStream postFile = new FileOutputStream(path + POSTING_INFO_NAME);
        OutputStream postBuffer = new BufferedOutputStream(postFile);
        this.PostingInfoFile = new ObjectOutputStream(postBuffer);
    }
    
    public void closeConnections() throws IOException{
        this.DocumentInfoFile.close();
        this.PostingInfoFile.close();
    }
        
    public long saveNextDocumentInfo( DocumentInfo documentInfo ) throws IOException{
       this.DocumentInfoFile.writeObject( (Object) documentInfo);
       return 0;
    }
     
    public long saveNextPostingInfoHolder( PostingInfoHolder postingInfoHolder ) throws IOException{
       this.DocumentInfoFile.writeObject( (Object) postingInfoHolder);
       return 0;
    }
    
    public void saveVocabularyInfoHolder( VocabularyInfoHolder vocabularyInfoHolder ) throws FileNotFoundException, IOException{
        OutputStream vocFile = new FileOutputStream(this.openPath + VOCABULARY_HOLDER_NAME);
        OutputStream vocBuffer = new BufferedOutputStream(vocFile);
        ObjectOutputStream vocabularyInfoHolderFile = new ObjectOutputStream(vocBuffer);
        vocabularyInfoHolderFile.writeObject( (Object) vocabularyInfoHolder);
        vocabularyInfoHolderFile.close();
    }
}
