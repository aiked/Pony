package PonySearcher;

import Common.TermNormalizer;
import PonyIndexer.StopWords;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    
    
    public ArrayList<ParsedQueryWord> parse(String query) {
        String[] words = query.split(" ");
        int maxFrequency = 0;
        HashMap<String, Integer> tmpTerms = new HashMap();
        for(String word : words){
            if(!word.isEmpty()){
                word = termNormalizer.termToLowerCase(word);
                if(termNormalizer.isTermGreek(word)){
                    word = termNormalizer.removePunctuation(word);
                }
                word = stopWords.getValidTerm(word);
                if(word!=null){
                    word = termNormalizer.stemTerm(word);
                    Integer frequency = tmpTerms.get(word);
                    if(frequency==null){
                        frequency = 0;
                    }
                    ++frequency;
                    maxFrequency = Math.max(maxFrequency, frequency);
                    tmpTerms.put(word, frequency);
                }
            }
        }
        ArrayList<ParsedQueryWord> parsedQueryWords = new ArrayList( tmpTerms.size() );
        for( Map.Entry<String, Integer> tmpTerm : tmpTerms.entrySet() ){
            parsedQueryWords.add( 
                    new ParsedQueryWord( 
                        ((double)tmpTerm.getValue())/((double)maxFrequency), 
                        tmpTerm.getKey() ) 
                    );
        }
        return parsedQueryWords;
    }
    
    
    
    public class ParsedQueryWord {
        private final double tf;
        private final String word;

        public ParsedQueryWord(double tf, String word) {
            this.tf = tf;
            this.word = word;
        }

        public double getTf() {
            return tf;
        }

        public String getWord() {
            return word;
        }
        
        
    }
    
}
