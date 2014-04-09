package Common;

import mitos.stemmer.Stemmer;
        
/**
 *
 * @author jit
 */
public class TermNormalizer {

    private static TermNormalizer instance = null;
    
    private TermNormalizer(){
        Stemmer.Initialize();
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
}
