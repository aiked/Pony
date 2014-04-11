
package PonySearcher;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author jit
 */
public class SnippetGenerator {
    public static final String SNIPPET_SEPERATOR = " | ";
    
    private static final byte S_OFFSET_START = 25;
    private static final byte S_OFFSET_END = 25;
    


    public static ArrayList<String> generate(RandomAccessFile file, String term, ArrayList<Long> positions) throws IOException{
        assert( !positions.isEmpty() );

        ArrayList<String> snippets = new ArrayList();
        for(Long pos : positions){
            snippets.add( generateSingle(file, term, pos) + SNIPPET_SEPERATOR );
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
        
        while(buffer[++i]!=' '){;}
        buf_start = i;
        while(buffer[--buf_end]!=' '){;}
        String snippet = new String(buffer,buf_start+1,buf_end-buf_start);
        snippet = snippet.replaceAll("\\s+", " ");
        snippet = snippet.replaceAll(term, "<b>" + term + "</b>");
        return snippet;
    }
}
