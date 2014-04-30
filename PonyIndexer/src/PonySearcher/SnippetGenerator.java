
package PonySearcher;

import Common.TermNormalizer;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author jit
 */
public class SnippetGenerator {
    public static final String SNIPPET_SEPERATOR = " <i>|</i> ";
    
    private static final byte S_OFFSET_START = 30;
    private static final byte S_OFFSET_END = 30;
    


    public static ArrayList<String> generate(RandomAccessFile file, String term, ArrayList<Long> positions) throws IOException{
        assert( !positions.isEmpty() );

        ArrayList<String> snippets = new ArrayList();
        if(positions!=null && !positions.isEmpty() ){
            for(int i=positions.size()-1; i!=0; --i){
                snippets.add( generateSingle(file, term, positions.get(i)) + SNIPPET_SEPERATOR );
            }
            snippets.add( generateSingle(file, term, positions.get(0)) );
        }
        return snippets;
    }
   
    @SuppressWarnings("empty-statement")
    public static String generateSingle(RandomAccessFile file, String term, Long position) throws IOException{
        
        long pStart = Math.max( position-S_OFFSET_START, 0);
        long pEnd = Math.min( position + term.length() + S_OFFSET_END, file.length() );
        long length = pEnd - pStart;
        
        file.seek(pStart);
        byte[] buffer = new byte[(int)(length)];
        
        file.read(buffer);
        
        String snippet = new String(buffer, "UTF-8");
        StringBuilder snippetToBuild = new StringBuilder(snippet);
        long termPos = position-pStart;
        snippetToBuild.insert((int) termPos, "<b>");
        int termLength = TermNormalizer.countUTF8Stringlength(term);
        snippetToBuild.insert((int) (termPos+termLength), "</b>");
               
        for(int i=0; i<snippetToBuild.length();){
            char schar = snippetToBuild.charAt(i);
            if(schar==' '){
                snippetToBuild.deleteCharAt(i);
            }else
                break;
        }

        for(int i=snippetToBuild.length()-1; i!=-1; --i){
            char schar = snippetToBuild.charAt(i);
            if(schar==' '){
                snippetToBuild.deleteCharAt(i);
            }else
                break;
        }
        snippet = snippetToBuild.toString();
        snippet = snippet.replaceAll("\\s+", " ");
        return snippet;
    }
}
