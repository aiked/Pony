package PonySearcher;

import Common.TermNormalizer;
import PonyIndexer.StopWords;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author japostol
 */
public class ParsedQuery {
    private TermNormalizer termNormalizer;
    private StopWords stopWords;
    
    public ParsedQuery(String StopWordsFolder){
        termNormalizer = TermNormalizer.getInstance();
        stopWords = new StopWords();
        if(StopWordsFolder!=null)
            stopWords.importFromFolder(StopWordsFolder);
    }
    
    
//    public ArrayList<ParsedQueryWord> parse(String query) {
//
//        int maxFrequency = 0;
//        HashMap<String, Integer> tmpTerms = new HashMap();
//        StringTokenizer tokenizer = new StringTokenizer(query, TermNormalizer.DOCUMENT_TERMS_DELIMITER, true);
//        while(tokenizer.hasMoreTokens() ) {
//            String word = tokenizer.nextToken();
//            if(!word.matches(TermNormalizer.IS_DOCUMENT_TERMS_DELIMITER)){
//                
//                word = termNormalizer.termToLowerCase(word);
//                if(termNormalizer.isTermGreek(word)){
//                    word = termNormalizer.removePunctuation(word);
//                }
//                word = stopWords.getValidTerm(word);
//                if(word!=null && !word.isEmpty()){
//                    word = termNormalizer.stemTerm(word);
//                    Integer frequency = tmpTerms.get(word);
//                    if(frequency==null){
//                        frequency = 0;
//                    }
//                    ++frequency;
//                    maxFrequency = Math.max(maxFrequency, frequency);
//                    tmpTerms.put(word, frequency);
//                }
//            }
//        }
//        ArrayList<ParsedQueryWord> parsedQueryWords = new ArrayList( tmpTerms.size() );
//        for( Map.Entry<String, Integer> tmpTerm : tmpTerms.entrySet() ){
//            parsedQueryWords.add( 
//                    new ParsedQueryWord( 
//                        ((double)tmpTerm.getValue())/((double)maxFrequency), 
//                        tmpTerm.getKey(), 0 ) 
//                    );
//        }
//        return parsedQueryWords;
//    }
    
    public ArrayList<ParsedQueryWord> parse(String query) {

        int maxFrequency = 0;
        HashMap<String, WordInfo> tmpTerms = new HashMap();
        ArrayList<ParsedQueryWord> parsedQueryWords = new ArrayList();
        String word, nextWord, weightString="";
        double weight = 0;
        
        StringTokenizer tokenizer = new StringTokenizer(query, TermNormalizer.DOCUMENT_TERMS_DELIMITER, true);
        
        while(tokenizer.hasMoreTokens() ) {
            word = tokenizer.nextToken();
            
            if(!termNormalizer.isDelimiter(word)){
                word = termNormalizer.termToLowerCase(word);
                if(termNormalizer.isTermGreek(word)){
                    word = termNormalizer.removePunctuation(word);
                }
                word = stopWords.getValidTerm(word);
                if(word!=null && !word.isEmpty()){
                   
                    word = termNormalizer.stemTerm(word);
                    weight = 1.0;
                    // Get possible weight
                    if (tokenizer.countTokens()>2 && termNormalizer.isDoubleDot(tokenizer.nextToken())){
                        if(tokenizer.nextToken().charAt(0) == '0' && tokenizer.nextToken().charAt(0) == '.')
                            
                            weightString="0.";
                            char weightValue = tokenizer.nextToken().charAt(0);
                           
                            while(Character.isDigit(weightValue)){
                                weightString += weightValue;
                                weightValue = tokenizer.nextToken().charAt(0);
                            }
                            weight = Double.parseDouble(weightString);
                            if(weight == 0){ weight = 1.0; }
                    }
                    
                    WordInfo wordInfo = tmpTerms.get(word);
                    if(wordInfo == null){
                        tmpTerms.put(word, new WordInfo( 1,weight));
                        maxFrequency = Math.max(maxFrequency, 1);
                    }
                    else{
                        wordInfo.incrementFrequency();
                        wordInfo.setWeight(weight);
                        maxFrequency = Math.max(maxFrequency, wordInfo.getFreq());
                    }
                }
            }
        }
        for( Map.Entry<String, WordInfo> tmpTerm : tmpTerms.entrySet() ){
            parsedQueryWords.add( 
                    new ParsedQueryWord( 
                        ((double)tmpTerm.getValue().getFreq())/((double)maxFrequency), 
                        tmpTerm.getKey(), tmpTerm.getValue().getWeight() ) 
                    );
        }
        
//        for (ParsedQueryWord pW : parsedQueryWords){
//            System.out.println(pW.getWord()+"  "+pW.getTf()+"  "+pW.getWeight());
//        }
        return parsedQueryWords;
    }
    
    
    public class WordInfo{
        private int freq;
        private double weight;
        
        public WordInfo(int freq, double weight){
            this.freq = freq;
            this.weight = weight;
        }
        
        public void incrementFrequency(){
            ++this.freq;
        }
        
        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
    
    public class ParsedQueryWord {
        private final double tf;
        private final String word;
        private final double weight;

        public ParsedQueryWord(double tf, String word, double weight) {
            this.tf = tf;
            this.word = word;
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
    
}
