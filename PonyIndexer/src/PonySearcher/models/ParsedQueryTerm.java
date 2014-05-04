package PonySearcher.models;


public class ParsedQueryTerm {
    private double tf;
    private String parsedWord;
    private String word;
    private double weight;

    public ParsedQueryTerm(double tf, String parsedWord, String word, double weight) {
        this.tf = tf;
        this.parsedWord = parsedWord;
        this.word = word;
        this.weight = weight;
    }

    public String getParsedWord() {
        return parsedWord;
    }

    public void setParsedWord(String parsedWord) {
        this.parsedWord = parsedWord;
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
