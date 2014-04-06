/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ponycrawler.core;

public interface CrawlerListener {
    void startFetching(String url);
    void pageFetched(PageInfo pageinfo);
    void pageSaved(String path);
}
