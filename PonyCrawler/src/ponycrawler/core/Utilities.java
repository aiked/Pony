package ponycrawler.core;

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
    
    public static String getUrlExtension(String url){
        int i = url.lastIndexOf(".");
        return i >= 0 ? url.substring(i+1) : null;
    }
    
}
