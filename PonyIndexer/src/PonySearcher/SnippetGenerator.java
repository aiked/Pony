
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
        // 7 is the byte length of <b></b>
        long pEnd = Math.min( position + term.length() + S_OFFSET_END + 7, file.length() );
        
        long length = (pEnd - pStart);
        
        file.seek(pStart);
        byte[] buffer = new byte[(int)(length)];
        
        file.read(buffer);
 
        byte termPosStart = (byte) (position-pStart);
        
        TermNormalizer termNormalizer = TermNormalizer.getInstance();
        byte termPosEnd = termPosStart;
        while(termPosEnd<length && !termNormalizer.isDelimiter( (char) buffer[termPosEnd]) ){ ++termPosEnd; }
        
        for(byte j=(byte) (length-1); j>=termPosStart; --j){
            // 3 is the byte length of <b>
            buffer[j]=buffer[j-3];
        }
        buffer[termPosStart] = '<';
        buffer[termPosStart+1] = 'b';
        buffer[termPosStart+2] = '>';
  
        for(byte j=(byte) (length-1); j>=(termPosEnd+3); --j){
            // 3 is the byte length of </b>
            buffer[j]=buffer[j-4];
        }
        buffer[termPosEnd+3] = '<';
        buffer[termPosEnd+3+1] = '/';
        buffer[termPosEnd+3+2] = 'b';
        buffer[termPosEnd+3+3] = '>';
        
        byte buf_start;
        byte buf_end = (byte)(length);
        byte i =0;
        while( i<length && buffer[++i]!=' '){;}
        buf_start = i;
        while( buf_end>buf_start && buffer[--buf_end]!=' '){;}
        if(buf_end==buf_start)
            return null;
        else{
            String snippet = new String(buffer,buf_start+1,buf_end-buf_start, "UTF-8");    
            snippet = snippet.replaceAll("\\s+", " ");
            return snippet;
        }
    }
        
    
}
