package PonyIndexer;

/**
 *
 * @author jit
 */
public class VocabularyInfo {
    
    private String  term;
    private Long    df;
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

    public PostingInfoHolder getPostHolder() {
        return postHolder;
    }

    public void setPostHolder(PostingInfoHolder postHolder) {
        this.postHolder = postHolder;
    }

    public Long getPointer() {
        return pointer;
    }

    public void setPointer(Long pointer) {
        this.pointer = pointer;
    }

}
