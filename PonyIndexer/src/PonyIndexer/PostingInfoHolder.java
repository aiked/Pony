
package PonyIndexer;

import java.util.HashMap;

/**
 *
 * @author jit
 */
public class PostingInfoHolder {

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
    
    public HashMap<Long,PostingInfo> getAllInfo(){
        return infoMap;
    }
}
