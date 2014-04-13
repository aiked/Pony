
package PonySearcher;

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
        
        byte buf_start;
        byte buf_end = (byte)(length);
        byte i =0;
        
        while( i<length && buffer[++i]!=' '){;}
        buf_start = i;
        while( buf_end>0 && buffer[--buf_end]!=' '){;}
        
        String snippet = new String(buffer,buf_start+1,buf_end-buf_start, "UTF-8");
        snippet = snippet.replaceAll("\\s+", " ");
        snippet = snippet.replaceAll(term, "<b>" + term + "</b>");
        return snippet;
    }
}
