package PonySearcher;

/**
 *
 * @author Apostolidis
 */
public class PageRankInfo {
    private double rank;
    private String snippet;

    public PageRankInfo() {
        rank = 0.0;
        snippet = "";
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
