
package PonyIndexer;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author jit
 */
public class VocabularyInfoHolder  implements Serializable {

    private static VocabularyInfoHolder instance = null;
    private HashMap<String, VocabularyInfo> vocMap;
    
    private VocabularyInfoHolder(){
        vocMap = new HashMap<>();
    }
    
    public static VocabularyInfoHolder getInstance(){
        if(instance==null){
            instance = new VocabularyInfoHolder();
        }
        return instance;
    }
    
    public VocabularyInfo get(String term){
        return vocMap.get(term);
    }
    
    public void add( VocabularyInfo info){
        vocMap.put(info.getTerm(), info);
    }
    
    public int getSize(){
        return vocMap.size();
    }
}
