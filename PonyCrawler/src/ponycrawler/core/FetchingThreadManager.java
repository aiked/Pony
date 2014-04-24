package ponycrawler.core;

import java.util.ArrayList;

/**
 *
 * @author Apostolidis
 */
public class FetchingThreadManager {
    private final int totalThreads;
    private final ArrayList<Thread> runningThreads;

    private final FetchingThreadNotifier fetchingThreadNotifier;
    private final String storageFolder;
    
    public FetchingThreadManager(
            int totalThreads, 
            FetchingThreadNotifier fetchingThreadNotifier, 
            String storageFolder
    ){
        this.fetchingThreadNotifier = fetchingThreadNotifier;
        this.storageFolder = storageFolder;
        this.totalThreads = totalThreads;
        runningThreads = new ArrayList(totalThreads);
    }
    
    
    public boolean hasRunningThreads(){
        return !runningThreads.isEmpty();
    }
    
    public boolean hasIdleThreads(){
        return runningThreads.size()<totalThreads;
    }
    
    public void garbageCollect(){
        for(int i = runningThreads.size()-1; i!=0; --i){
            final Thread fetchingThread = runningThreads.get(i);
            if(fetchingThread.getState()==Thread.State.TERMINATED){
                runningThreads.remove(i);
            }
        }
    }
    
    public void startThread(String url) throws InterruptedException{
        assert( hasIdleThreads() );
        FetchingThread fetchingThread = new FetchingThread(
            url,
            storageFolder, 
            fetchingThreadNotifier
        );
        runningThreads.add(fetchingThread);
        fetchingThread.start();
    }  
}
