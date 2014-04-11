package PonySearcher;

import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Apostolidis
 */
public class PageRankInfo {
    private long docId;
    private double rank;
    private ArrayList<String> snippets;
    private RandomAccessFile documentFile;
    private String documentPath;

    public PageRankInfo(long docId, String docPath, RandomAccessFile documentFile) {
        rank = 0.0;
        snippets = new ArrayList();
        this.docId = docId;
        this.documentFile = documentFile;
        this.documentPath = docPath;
    }
    
    public PageRankInfo(String docPath, String snippet) {
        rank = 0.0;
        snippets = new ArrayList();
        snippets.add(snippet);
        this.docId = -1;
        this.documentFile = null;
        this.documentPath = docPath;
    }
    
    public void appendSnippets(ArrayList<String> snippets){
        this.snippets.addAll(snippets);
    }
    
    public void addRank(double rank){
        this.rank += rank;
    }

    public double getRank() {
        return rank;
    }

    public ArrayList<String> getSnippets() {
        return snippets;
    }

    public RandomAccessFile getDocumentFile() {
        return documentFile;
    }

    public long getDocId() {
        return docId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    
}
