package PonyIndexer;

import java.io.Serializable;

/**
 *
 * @author jit
 */
public class VocabularyInfo implements Serializable {
    
    private String  term;
    private Long    df;
    private Double  idf;
    private PostingInfoHolder postHolder;
    private Long    pointer;
    
    public VocabularyInfo(){
        term    = null;
        postHolder = new PostingInfoHolder();
        df      = -1L;
        pointer = -1L;
    }

    public VocabularyInfo(String term){
        this.term = term;
        postHolder = new PostingInfoHolder();
        df = 1L;
        pointer = -1L;
    }
    
    public VocabularyInfo( String term, long df, double idf, long pointer){
        this.term = term;
        this.df = df;
        this.idf = idf;
        this.postHolder = null;
        this.pointer = pointer;
    }
    
    public void addPostingInfo( PostingInfo info){
            postHolder.add(info);
    }
    
    public void incrementDf(){
        ++df;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Long getDf() {
        return df;
    }

    public void setDf(Long df) {
        this.df = df;
    }

    public Double getIdf() {
        return idf;
    }

    public void setIdf(Double idf) {
        this.idf = idf;
    }

    public PostingInfoHolder getPostHolder() {
        return postHolder;
    }

    public void setPostHolder(PostingInfoHolder postHolder) {
        this.postHolder = postHolder;
    }
    
    public PostingInfo getPostInfoByDocId(long docId){
        return postHolder.get(docId);
    }

    public Long getPointer() {
        return pointer;
    }

    public void setPointer(Long pointer) {
        this.pointer = pointer;
    }

}
