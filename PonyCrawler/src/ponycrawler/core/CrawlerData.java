package ponycrawler.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Apostolidis
 */
public class CrawlerData {
    Queue<String> pendingUrls;
    Set<String> fetchedUrls;
    List<String> forbitenUrls;
    
    public CrawlerData(){
        pendingUrls = new LinkedList();
        fetchedUrls = new HashSet();
        forbitenUrls = new ArrayList();
    }
    
    public boolean hasPendingUrls(){
        return !pendingUrls.isEmpty();
    }
    
    public String getNextPendingUrl(){
        return pendingUrls.remove();
    }
    
    private synchronized void addPendingUrl(String url){
        pendingUrls.add(url);
        fetchedUrls.add(url);
    }
    
    public void addForbitenUrlPattern(String urlPattern){
        forbitenUrls.add(".*" + urlPattern + ".*");
    }
    
    public void addForbitenUrlPatterns(Collection<? extends String> urlPatterns){
        for(String urlPattern : urlPatterns)
            addForbitenUrlPattern(urlPattern);
    }
    
    public boolean isExclusiveUrl(String url){
        
        for(String exclusUrl : forbitenUrls){
            if(url.matches(exclusUrl))
                return true;
        }
        return false;
    }
    
    public boolean isFetchedUrl(String url){
        return fetchedUrls.contains(url);
    }

    public synchronized void addUrlsToBeFetched(Collection<? extends String> links) {
        if(links!=null)
        for(String link : links){
            if(!isFetchedUrl(link) && !isExclusiveUrl(link))
                addPendingUrl(link);
        }
    }
}
