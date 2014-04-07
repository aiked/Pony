/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PonySearcher;

import java.util.HashMap;

/**
 *
 * @author japostol
 */
public class Document {
    private final long docId;
    private final String documentContent;
    private final long df;
    private final HashMap<String, DocumentWord> words;

    public Document(long docId, long df, String documentContent) {
        words = new HashMap();
        this.docId = docId;
        this.df = df;
        this.documentContent = documentContent;
    }
    
    public DocumentWord getDocumentWord(String word){
        return words.get(word);
    }
    
    public HashMap<String, DocumentWord> getWords(){
        return words;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public long getDocId() {
        return docId;
    }
    
    public long getDf(){
        return df;
    }

}
