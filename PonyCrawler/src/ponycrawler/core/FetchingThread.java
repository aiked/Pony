package ponycrawler.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apostolidis
 */
public class FetchingThread extends Thread  {
    private final FetchingThreadNotifier fetchingThreadNotifier;
    private final String pendingUrl;
    private final String storageFolder;
    
    public FetchingThread(String urlToBeFetched, String storageFolder, FetchingThreadNotifier fetchingThreadNotifier){
        this.fetchingThreadNotifier = fetchingThreadNotifier;
        this.storageFolder = storageFolder;
        this.pendingUrl = urlToBeFetched;
    }
    
    public String getFetcedUrl(){
        return pendingUrl;
    }
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() + ": " + "fetching " + pendingUrl);
        PageInfo page = HtmlPageFetcher.fetch( pendingUrl );
        String storageLocation=null;
        if(page!=null)
            storageLocation = storePageInfo(page, storageFolder);
        
        fetchingThreadNotifier.onFetchingFinish(this, page, storageLocation);
    }
    
    
    private static String storePageInfo( PageInfo page, final String storageFolder ){
        if(page.getContent().length()>100){
            String storageLocation = storageFolder 
                                    + System.getProperty("file.separator") 
                                    + page.getFriendlyUrl() + ".txt";
            try {
                PrintWriter writer = new PrintWriter(storageLocation, "UTF-8");
                writer.println(page.getUrl());
                writer.print( page.getContent() );
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Logger.getLogger(FetchingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return storageLocation;
        }else
            return null;

    }
}
