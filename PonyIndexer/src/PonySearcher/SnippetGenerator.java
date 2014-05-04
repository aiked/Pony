
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
    public static final int SNIPPET_MAX_SIZE = 4;
    
    private static final byte S_OFFSET_START = 30;
    private static final byte S_OFFSET_END = 30;
    


    public static ArrayList<String> generate(RandomAccessFile file, String term, ArrayList<Long> positions) throws IOException{
        assert( !positions.isEmpty() );

        ArrayList<String> snippets = new ArrayList();
        if(positions!=null && !positions.isEmpty() ){
            int maxSize = positions.size()-SNIPPET_MAX_SIZE;
            for(int i=positions.size()-1; i!=0 && i>maxSize; --i){
                snippets.add( generateSingle(file, term, positions.get(i)) + SNIPPET_SEPERATOR );
            }
            snippets.add( generateSingle(file, term, positions.get(0)) );
        }
        return snippets;
    }
   
    @SuppressWarnings("empty-statement")
    public static String generateSingle(RandomAccessFile file, String term, Long position) throws IOException{
        int offsetStart = S_OFFSET_START;
        int offsetEnd = S_OFFSET_END;
        if(TermNormalizer.countUTF8Stringlength(term)!=term.length()){
            offsetStart *= 2;
            offsetEnd *= 2;
        }
        
        long pStart = Math.max( position-offsetStart, 0);
        // 7 is the byte length of <b></b>
        long pEnd = Math.min( position + term.length() + offsetEnd + 7, file.length() );
        
        int length = (int) (pEnd - pStart);
        
        file.seek(pStart);
        byte[] buffer = new byte[length];
        
        file.read(buffer);
 
        int termPosStart =  (int) (position-pStart);
        
        TermNormalizer termNormalizer = TermNormalizer.getInstance();
        int termPosEnd = termPosStart;
        while(termPosEnd<length && !termNormalizer.isDelimiter( (char) buffer[termPosEnd]) ){ ++termPosEnd; }
        
        for(int j= length-1; j>=termPosStart; --j){
            // 3 is the byte length of <b>
            buffer[j]=buffer[j-3];
        }
        buffer[termPosStart] = '<';
        buffer[termPosStart+1] = 'b';
        buffer[termPosStart+2] = '>';
  
        for(int j= length-1; j>=(termPosEnd+3); --j){
            // 3 is the byte length of </b>
            buffer[j]=buffer[j-4];
        }
        buffer[termPosEnd+3] = '<';
        buffer[termPosEnd+3+1] = '/';
        buffer[termPosEnd+3+2] = 'b';
        buffer[termPosEnd+3+3] = '>';
        
        int buf_start;
        int buf_end = length;
        int i =0;
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
