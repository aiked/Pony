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
    
    
    public ArrayList<ParsedQueryWord> parse(String query) {

        int maxFrequency = 0;
        HashMap<String, Integer> tmpTerms = new HashMap();
        StringTokenizer tokenizer = new StringTokenizer(query, TermNormalizer.DOCUMENT_TERMS_DELIMITER, true);
        while(tokenizer.hasMoreTokens() ) {
            String word = tokenizer.nextToken();
            if(!word.matches(TermNormalizer.IS_DOCUMENT_TERMS_DELIMITER)){
                
                word = termNormalizer.termToLowerCase(word);
                if(termNormalizer.isTermGreek(word)){
                    word = termNormalizer.removePunctuation(word);
                }
                word = stopWords.getValidTerm(word);
                if(word!=null && !word.isEmpty()){
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
