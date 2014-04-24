package ponycrawler.core;

import ponycrawler.core.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Apostolidis
 */
public class Utilities {
    
    public static String charBufferToString(BufferedReader pageBuffer) throws IOException{
        String string = "";
        String line;
        while ((line = pageBuffer.readLine()) != null) {
            string+=line;
        }
        return string;
    }   
}
