package ponycrawler.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apostolidis
 */
public class CrawlerCore implements FetchingThreadNotifier {

    private final ArrayList<CrawlerListener> crawlerListeners;
    private final FetchingThreadManager fetchingThreadManager;
    private final CrawlerData crawlerData;
    
    public CrawlerCore(final String storageFolder, int totalThreads){
        fetchingThreadManager = new FetchingThreadManager(
                totalThreads, 
                this,
                storageFolder
            );
        crawlerData = new CrawlerData();
        crawlerListeners = new ArrayList();
    }
    
    public void addCrwalerListener(CrawlerListener crawlerListener){
        crawlerListeners.add(crawlerListener);
    }
    
    public void notifyStartFetching(String url){
        for(CrawlerListener crawlerListener: crawlerListeners){
            crawlerListener.startFetching(url);
        }
    }
    
    public void notifyPageSuccessFetched(PageInfo pageinfo, String storageLocation){
        for(CrawlerListener crawlerListener: crawlerListeners){
            crawlerListener.pageSuccessFetched(pageinfo, storageLocation);
        }
    }
    
    public void notifyPageFailFetched(String url){
        for(CrawlerListener crawlerListener: crawlerListeners){
            crawlerListener.pageFailFetched(url);
        }
    }
    
    public void crawl(
            Collection<? extends String> urlsToBeFetched, 
            Collection<? extends String> forbitenUrls) 
            throws InterruptedException
    {
        if(forbitenUrls!=null)
            crawlerData.addForbitenUrlPatterns(forbitenUrls);
        crawlerData.addUrlsToBeFetched(urlsToBeFetched);
        
        while(fetchingThreadManager.hasRunningThreads() || crawlerData.hasPendingUrls()){
            if( !fetchingThreadManager.hasIdleThreads() || !crawlerData.hasPendingUrls() ){
                fetchingThreadManager.garbageCollect();
                Thread.yield();
            }else{           
                String urlToBeFetched = crawlerData.getNextPendingUrl();
                notifyStartFetching(urlToBeFetched);
                fetchingThreadManager.startThread(urlToBeFetched);  
            }
        }
    }

    @Override
    public void onFetchingFinish(FetchingThread fetchingThread, PageInfo pageInfo, String storageLocation) {
        // url not fetched/fetched save location
        if(pageInfo==null ){
            notifyPageFailFetched(fetchingThread.getFetcedUrl());
        }else{
            notifyPageSuccessFetched(pageInfo, storageLocation);
            if(!pageInfo.getLinks().isEmpty()){
                crawlerData.addUrlsToBeFetched(pageInfo.getLinks());
            }
        }
            
    }
    
    public static void main(String[] args) {
        try {
            CrawlerCore crawlerCore = new CrawlerCore("D:\\\\mikonos", 64);
            crawlerCore.addCrwalerListener( new CrawlerListener() {

                @Override
                public void startFetching(String url) {
                    System.out.println("startFetching: " + url);
                }

                @Override
                public void pageSuccessFetched(PageInfo pageinfo, String storageLocation) {
                    System.out.println("pageSuccessFetched: " + storageLocation);
                }

                @Override
                public void pageFailFetched(String url) {
                    System.out.println("pageFailFetched: " + url);
                }

                @Override
                public void finishAllFetches() {
                    System.out.println("finishAllFetches");
                }
            } );
            ArrayList<String> urls = new ArrayList();
            urls.add("http://www.newsbeast.gr/");
            urls.add("http://www.in.gr/");
            urls.add("http://www.tvxs.gr/");
            ArrayList<String> excluUrls = new ArrayList();
            excluUrls.add(".*facebook..*");
            excluUrls.add(".*twitter..*");
            excluUrls.add(".*youtube..*");
            excluUrls.add(".*linkedin..*");
            excluUrls.add(".*google..*");
            crawlerCore.crawl(urls, excluUrls);
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
