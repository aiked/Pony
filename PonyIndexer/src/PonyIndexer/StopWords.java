package PonyIndexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import Common.TermNormalizer;

/**
 *
 * @author jit
 */
public class StopWords {
    
    private HashSet<String> words;
    private TermNormalizer termNormalizer;
    
    public StopWords(){
        termNormalizer = TermNormalizer.getInstance();
        this.words = new HashSet();
    }

    public void importFromFile( String path ){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader( 
                    new FileInputStream(path), "UTF-8"));

            String line;

            while ((line = br.readLine()) != null) {
                line = termNormalizer.termToLowerCase(line);
                if(termNormalizer.isTermGreek(line)){
                    line = termNormalizer.removePunctuation(line);
                }
                words.add(line);
            }   
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
    }
        
    public void importFromFolder( String path ){
        try{
            File folder = new File(path);
            for ( File fp : folder.listFiles()){
                if(fp.isDirectory()){
                    importFromFolder(fp.toString());
                }
                else{
                    importFromFile(fp.getCanonicalPath());
                }
            }
        }catch(Exception e){ System.err.println("Error: "+e.getMessage()); }
    }
    
    public Boolean existWord( String word ){
        return words.contains(word);
    }
    
    public String getValidTerm( String word ){
        if(words.contains(word)){
            return null;
        }
        word = word.replaceAll("[(){}[,.]\'\";!:_?—<\\->]", "");
        
        if(word.matches("\\d+")){
            return null;
        }
        
        return word;
    }
    
    public int getSize(){
        return words.size();
    }
}