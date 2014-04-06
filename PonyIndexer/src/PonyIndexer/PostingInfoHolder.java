
package PonyIndexer;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author jit
 */
public class PostingInfoHolder implements Serializable {

    private HashMap<Long,PostingInfo> infoMap;
    
    public PostingInfoHolder(){
        infoMap = new HashMap<>();
    }
    
    public void add( PostingInfo info){
        infoMap.put(info.getId(), info);
    }
    
    public PostingInfo get( Long id ){
        return infoMap.get(id);
    }
}
