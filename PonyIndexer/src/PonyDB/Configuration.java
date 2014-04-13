/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonyDB;

/**
 *
 * @author Apostolidis
 */
public class Configuration {
    public static final String DOCUMENT_INFO_NAME = "document.bin";
    public static final String POSTING_INFO_NAME = "posting.bin";
    public static final String VOCABULARY_HOLDER_NAME = "vocabulary.bin";
    public static final String INDEX_FOLDER_NAME = "collectionIndex";
    
    public static final byte SIZE_UTF8 = 2;
    public static final byte SIZE_LONG = (Long.SIZE/8);
    public static final byte SIZE_INT = (Integer.SIZE/8);
    public static final byte SIZE_DOUBLE = (Double.SIZE/8);
    
    public static final int S_BUFFER = 8192;
    public static final int S_WRITE_LIMIT = 6144;
}
