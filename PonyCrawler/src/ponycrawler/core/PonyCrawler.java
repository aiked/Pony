package ponycrawler.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.EventListener;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apostolidis
 */
public class PonyCrawler {
    private static HashSet<String> fetchedUrls = new HashSet();
    
    public static void crawl( final String url, int recursiveLevel, final String storageFolder, CrawlerListener crawlerListener ) throws Exception{
        createFolderIfNotExist( storageFolder );
        fetchedUrls.clear();
        fetch(url, recursiveLevel, storageFolder, crawlerListener);
    }
    
    private static void fetch( final String url, int recursiveLevel, final String storageFolder, CrawlerListener crawlerListener ){
        crawlerListener.startFetching(url);
        PageInfo page = HtmlPageFetcher.fetch( url );
        crawlerListener.pageFetched(page);
        fetchedUrls.add(url);
        if( page!=null ){
            String storageLocation = storePageInfo(page, storageFolder);
            crawlerListener.pageSaved(storageLocation);
            if( --recursiveLevel!=0 && page.getLinks()!=null && !page.getLinks().isEmpty() ){
                for( String link : page.getLinks() ){
                    if(!fetchedUrls.contains(link)){
                        fetch( link, recursiveLevel, storageFolder, crawlerListener );
                    }
                }
            } 
        }
    }
    
    private static void createFolderIfNotExist( final String storageFolder ) throws Exception{
        File dir = new File(storageFolder);      
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if(!result) {    
              throw new Exception("Cannot create folder: " + storageFolder);
            }
        }
    }
    
    private static String storePageInfo( PageInfo page, final String storageFolder ){
        String storageLocation = storageFolder + System.getProperty("file.separator") + page.getFriendlyUrl() + ".txt";
        try {
            PrintWriter writer = new PrintWriter(storageLocation, "UTF-8");
            writer.print( page.getContent() );
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(PonyCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storageLocation;
    }
    
//    public static void main(String[] args) {
//        try {
//            crawl( "http://stackoverflow.com/", 2, "D:\\\\mikonos" );
//        } catch (Exception ex) {
//            Logger.getLogger(PonyCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
