
package Pony;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jit
 */
public class DocumentInfoHolder {

    private static DocumentInfoHolder instance = null;
    private Map<Long, DocumentInfo> fileMap;
    
    private DocumentInfoHolder(){
        fileMap = new HashMap<>();
    }
    
    public static DocumentInfoHolder getInstance(){
        if(instance==null){
            instance = new DocumentInfoHolder();
        }
        return instance;
    }
    
    public void add( Long id, DocumentInfo info){
        fileMap.put(id, info);
    }
    
    public DocumentInfo get(Long id){
        return fileMap.get(id);
    }
    
    public int getSize(){
        return fileMap.size();
    }
    
    public void PrintMap(){
        for ( Long key : fileMap.keySet()){
            System.out.println("\nIndex: "+key+
                               "\nPath : "+fileMap.get(key).getPath()+
                               "\nType : "+fileMap.get(key).getType());
        }
    }
     
}
