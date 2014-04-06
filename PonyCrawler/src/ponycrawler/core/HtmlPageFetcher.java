/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ponycrawler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author Apostolidis
 */
public class HtmlPageFetcher {
    
    
    public static PageInfo fetch(String url){
        try {
            URL urlHolder = new URL(url);
            InputStream pageAsStream = urlHolder.openStream();
            BufferedReader pageAsCharBuffer = new BufferedReader(new InputStreamReader(pageAsStream, "UTF-8"));
            String pageAsString = Utilities.charBufferToString( pageAsCharBuffer );
            String extension = Utilities.getUrlExtension(url);
            Document htmlDom = Jsoup.parse(pageAsString);
            if( htmlDom!=null ){
                ArrayList<String> links = extractLinksFromDom(htmlDom);
                String content = htmlDom.text();
                return new PageInfo( url, PageInfo.SUPPORTEDEXT_HTML, content, links );
            }
            return new PageInfo( url, extension, pageAsString, null );
  
        } catch (MalformedURLException ex) {
            Logger.getLogger(HtmlPageFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HtmlPageFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex){
            Logger.getLogger(HtmlPageFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static ArrayList<String> extractLinksFromDom(Document htmlDom){
        ArrayList<String> links = new ArrayList();
        Elements elementLinks = htmlDom.select("a[href]");
        if( !elementLinks.isEmpty() ){
            for (Element link : elementLinks) {
                String href = link.attr("abs:href");
                if(href!=null && !href.isEmpty())
                    links.add( href );
            }
        }
        return links;
    }
    
}
