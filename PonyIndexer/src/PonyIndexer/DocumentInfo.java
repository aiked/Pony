
package PonyIndexer;


/**
 *
 * @author jit
 */
public class DocumentInfo {
    
    private Long     id;
    private String  path;
    private String  type;
    
    public DocumentInfo(){
        this.path   = null;
        this.type   = null;
    }
    
    public DocumentInfo( Long id, String path ){
        this.id     = id;
        this.path   = path;
        this.type   = extractType(path);
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

    public void setId(Long id) {
        this.id = id;
    }
    
}
