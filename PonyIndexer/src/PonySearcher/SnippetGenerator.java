
package PonySearcher;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author jit
 */
public class SnippetGenerator {

    private static final byte S_OFFSET_START = 25;
    private static final byte S_OFFSET_END = 25;

    @SuppressWarnings("empty-statement")
    public static String SnippetGenerator(RandomAccessFile file, String term, Long position) throws IOException{
        
        long pStart = Math.max( position-S_OFFSET_START, 0);
        long length = Math.min( S_OFFSET_START+term.length()+S_OFFSET_END, file.length() );
        
        file.seek(pStart);
        byte[] buffer = new byte[(int)(length)];
        
        file.read(buffer);
       
        byte buf_start;
        byte buf_end = (byte)(length-1);
        byte i =0;
        
        while(buffer[++i]!=' '){;}
        buf_start = i;
        while(buffer[--buf_end]!=' '){;}

        return new String(buffer,buf_start+1,buf_end-buf_start);
    }
}
