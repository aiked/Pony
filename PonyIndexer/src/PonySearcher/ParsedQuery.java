package PonySearcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author japostol
 */
public class ParsedQuery {
    private final ArrayList<ParsedQueryWord> parsedQueryWords;

    public ParsedQuery(String query) {
        String[] words = query.split(" ");
        int maxFrequency = 0;
        HashMap<String, Integer> tmpTerms = new HashMap();
        for(String word : words){
            Integer frequency = tmpTerms.get(word);
            if(frequency==null){
                frequency = 0;
            }
            ++frequency;
            maxFrequency = Math.max(maxFrequency, frequency);
            tmpTerms.put(word, frequency);
        }
        parsedQueryWords = new ArrayList( tmpTerms.size() );
        for( Map.Entry<String, Integer> tmpTerm : tmpTerms.entrySet() ){
            parsedQueryWords.add( 
                    new ParsedQueryWord( 
                        ((double)tmpTerm.getValue())/((double)maxFrequency), 
                        tmpTerm.getKey() ) 
                    );
        }
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

    public ArrayList<ParsedQueryWord> getParsedQueryWords() {
        return parsedQueryWords;
    }
    
    
}
