
package PonyIndexer;


/**
 *
 * @author jit
 */
public class DocumentInfo {
    
    private Long     id;
    private String  path;
    private String  type;
    private Long    totalTerm;
    
    public DocumentInfo(){
        this.path   = null;
        this.type   = null;
    }
    
    public DocumentInfo( Long id, String path, Long totalTerm ){
        this.id     = id;
        this.path   = path;
        this.type   = extractType(path);
        this.totalTerm = totalTerm;
    }

    private String extractType( String path ){
        return path.substring(path.lastIndexOf('.')+1);
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getTotalTerm() {
        return totalTerm;
    }

    public void setTotalTerm(Long totalTerm) {
        this.totalTerm = totalTerm;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
