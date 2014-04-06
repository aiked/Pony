package Common;

/**
 *
 * @author Apostolidis
 */
public class Utilities {
    
    public static String getUrlExtension(String url){
        int i = url.lastIndexOf(".");
        return i >= 0 ? url.substring(i+1) : null;
    }
    
}
