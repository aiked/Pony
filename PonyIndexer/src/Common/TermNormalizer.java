package Common;

import PonyIndexer.StopWords;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import mitos.stemmer.Stemmer;
        
/**
 *
 * @author jit
 */
public class TermNormalizer {
    public static final String DOCUMENT_TERMS_DELIMITER = 
            "\t\r\f!@#$%^&*;:'\".,0123456789()_-\\[\\]\\{\\}<>?|~`+-=/  \\'\b«»§΄―—’‘–°·";
    private static TermNormalizer instance = null;
    private final Set<Character> delimiters;
    
    private TermNormalizer(){
        
        Stemmer.Initialize();
        
        delimiters = new HashSet();
        for( char c : DOCUMENT_TERMS_DELIMITER.toCharArray()){
            delimiters.add(c);
        }
    }
    
    public static TermNormalizer getInstance(){
        if(instance==null){
            instance = new TermNormalizer();
        }
        return instance;
    }
    
    public String stemTerm( String term ){
        return Stemmer.Stem(term);
    }
    
    public String termToLowerCase( String term){
        return term.toLowerCase();
    }
    
    public boolean isDelimiter( String term ){
        return delimiters.contains(term.charAt(0));
    }
    
    public boolean isDelimiter( char tcar ){
        return delimiters.contains(tcar);
    }
    
    public boolean isDoubleDot( String term ){
        return (term.charAt(0)==':');
    }
    
    
    public String removePunctuation( String term){
        
        char[] retval = new char[term.length()];
        for(int i=0;i<retval.length;++i){
            char c = term.charAt(i);
            switch(c){
                case 'ά':
                    retval[i] = 'α'; break;
                case 'έ':
                    retval[i] = 'ε'; break;
                case 'ή':
                    retval[i] = 'η'; break;
                case 'ί':
                    retval[i] = 'ι'; break;
                case 'ό':
                    retval[i] = 'ο'; break;
                case 'ύ':
                    retval[i] = 'υ'; break;
                case 'ώ':
                    retval[i] = 'ω'; break;
                default:
                    retval[i] = c;   break;
            }
        }
        return new String(retval);
    }
    
    public boolean isTermGreek( String term ){
        char c = term.charAt(0);
        return   ((c>='α'&&c<='z')||(c=='ά') ||(c=='έ')||(c=='ή')
                    ||(c=='ί')||(c=='ό')||(c=='ύ')||(c=='ώ'));
    }
    
    public static HashSet<String> getLanguage(String term) {
        HashSet langs = new HashSet(2);
        // Java uses Unicode
        // Modern Greek in unicode are between 0370 - 03FF
        // Basic Latin 0021 - 007E
        char c = term.charAt(0); // Get the first character of the word
        if (c >= (char) 880 && c <= (char) 1023) {
            // If character is between 0x0370 - 0x03FF
            langs.add("grc");
            langs.add("ell");
        } else if (c >= (char) 33 && c <= (char) 126) {
            // If character is // between 0x0021 - 0x007E
            langs.add("eng"); // return english language
        } 
        return langs;
    }
    
    public static int countUTF8Stringlength(CharSequence sequence) {
        int count = 0;
        for (int i = 0, len = sequence.length(); i < len; ++i) {
          char ch = sequence.charAt(i);
          if (ch <= 0x7F) {
            count++;
          } else if (ch <= 0x7FF) {
            count += 2;
          } else if (Character.isHighSurrogate(ch)) {
            count += 4;
            ++i;
          } else {
            count += 3;
          }
        }
        return count;
    }
    
    public String getLexicalAnalyzedTerm(String term, StopWords stopWords){
        if(isDelimiter(term))
            return null;
        else{
            term = termToLowerCase(term);
            if(isTermGreek(term)){
                term = removePunctuation(term);
            }
            term = stopWords.getValidTerm(term);
            return term!=null && !term.isEmpty() ? stemTerm(term) : null;
        }
    }
        
}
