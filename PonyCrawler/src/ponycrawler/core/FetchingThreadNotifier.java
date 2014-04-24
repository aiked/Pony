package ponycrawler.core;

/**
 *
 * @author Apostolidis
 */
public interface FetchingThreadNotifier {
    public void onFetchingFinish(FetchingThread fetchingThread, PageInfo pageInfo, String storageLocation);
}
