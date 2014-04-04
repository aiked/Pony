
package Pony;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jit
 */
public class DocumentHolder {

    private static DocumentHolder instance = null;
    private Map<Integer, DocumentInfo> fileMap;
    
    protected DocumentHolder(){
        fileMap = new HashMap<>();
    }
    
    public static DocumentHolder getInstance(){
        if(instance==null){
            instance = new DocumentHolder();
        }
        return instance;
    }
    
    public int getSize(){
        return fileMap.size();
    }
    
    public void ReadFilesFromFolder( String path ){
        try{
            File folder = new File(path);
            
            for( File fp : folder.listFiles() ){
                if(fp.isDirectory()){
                    ReadFilesFromFolder(fp.toString());
                }
                else{
                    // TODO ty apple for fucking .DS_STORE
                    fileMap.put(fileMap.size()+1,new DocumentInfo(fp.getPath()));
                }
            }
            
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
    }
    
    public void PrintMap(){
        for ( Integer key : fileMap.keySet()){
            System.out.println("\nIndex: "+key+
                               "\nPath : "+fileMap.get(key).getPath()+
                               "\nType : "+fileMap.get(key).getType());
        }
    }
    
    
}
