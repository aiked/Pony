
package Pony;

/**
 *
 * @author jit
 */
public class DocumentInfo {
    
    private String  path;
    private String  type;
    
    public DocumentInfo(){
        this.path   = null;
        this.type   = null;
    }
    
    public DocumentInfo( String path ){
        this.path   = path;
        this.type   = extractType(path);
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
    
    private String extractType( String path ){
        return path.substring(path.lastIndexOf('.')+1);
    }
    
}
