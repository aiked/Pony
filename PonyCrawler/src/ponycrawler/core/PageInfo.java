
package ponycrawler.core;

import java.util.ArrayList;

/**
 *
 * @author Apostolidis
 */
public class PageInfo {
    public final static String SUPPORTEDEXT_HTML = "html";

    private final String url;
    private final String extension;
    private final String content;    
    private final ArrayList<String> links;

    public PageInfo(String url, String extension, String content, ArrayList<String> links) {
        this.extension = extension;
        this.content = content;
        this.links = links;
        this.url = url;
    }  
    
    public String getExtension() {
        return extension;
    }

    public String getContent() {
        return content;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getFriendlyUrl() {
        String frindlyUrl = url.replaceAll("[^0-9a-z]", "_");
        if(frindlyUrl.length()>200){
            frindlyUrl = frindlyUrl.substring(frindlyUrl.length()-200);
        }
        return frindlyUrl;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
    
    @Override
    public String toString(){
        return "<< {{ url: " + this.url + "}},\n"
                + "{{ extension: " + this.extension + "}},\n"
                + " {{ link found: " + this.links + "}},\n"
                + " {{ content" + this.content + "}} >>" ;
    }   
    
}
