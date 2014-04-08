package PonySearcher;

/**
 *
 * @author Apostolidis
 */
public class PageRankInfo {
    private long docId;
    private double rank;
    private String snippet;

    public PageRankInfo(long docId) {
        rank = 0.0;
        snippet = "";
        this.docId = docId;
    }
    
    public void appendSnippet(String snippet){
        this.snippet = snippet;
    }
    
    public void addRank(double rank){
        this.rank += rank;
    }

    public double getRank() {
        return rank;
    }

    public String getSnippet() {
        return snippet;
    }
    
    
    
}
