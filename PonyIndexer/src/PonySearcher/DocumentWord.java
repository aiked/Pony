package PonySearcher;

import java.util.ArrayList;

/**
 *
 * @author japostol
 */
public class DocumentWord {
    private final String Word;
    private final ArrayList<Long> positions;
    
    private final Double tf;
    private final Double w;

    public DocumentWord(final String Word, final ArrayList<Long> positions, 
                        final Double tf, final Double w) {
        this.Word = Word;
        this.positions = positions;
        this.tf = tf;
        this.w = w;
    }
    
    public String getWord() {
        return Word;
    }

    public ArrayList<Long> getPositions() {
        return positions;
    }

    public Double getTf() {
        return tf;
    }

    public Double getW() {
        return w;
    }
}
