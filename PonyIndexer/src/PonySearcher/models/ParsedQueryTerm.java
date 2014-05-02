package PonySearcher.models;

/**
 *
 * @author Apostolidis
 */
public class ParsedQueryTerm {
    private double tf;
    private String word;
    private double weight;

    public ParsedQueryTerm(double tf, String word, double weight) {
        this.tf = tf;
        this.word = word;
        this.weight = weight;
    }

    public void incrementFrequency(){
        ++this.tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTf() {
        return tf;
    }

    public String getWord() {
        return word;
    }

    public double getWeight(){
        return weight;
    }
}
