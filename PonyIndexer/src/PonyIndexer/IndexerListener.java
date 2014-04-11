/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonyIndexer;

/**
 *
 * @author Apostolidis
 */
public interface IndexerListener {
    void onChangeIndexingState(String state);
    void onNewIndexingMsg(String msg);
    void onPercentileLoad(double percent);
}
