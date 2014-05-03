package PonySearcher;

import PonySearcher.models.ParsedQueryTerm;
import Common.TermNormalizer;
import Common.StopWords;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author japostol
 */
public class ParsedQuery {
    private final TermNormalizer termNormalizer;
    private final StopWords stopWords;
    
    public ParsedQuery(StopWords stopWords){
        termNormalizer = TermNormalizer.getInstance();
        this.stopWords = stopWords;
    }
        
    public ArrayList<ParsedQueryTerm> parse(String query) {

        int maxFrequency = 0;
        HashMap<String,ParsedQueryTerm> tmpTerms = new HashMap();
        ArrayList<ParsedQueryTerm> parsedQueryWords = new ArrayList();
        String weightString="";
        double weight;
        
        StringTokenizer tokenizer = new StringTokenizer(query, TermNormalizer.DOCUMENT_TERMS_DELIMITER, true);
        
        while(tokenizer.hasMoreTokens() ) {
            String word = tokenizer.nextToken();
            String[] parsedWord = termNormalizer.getLexicalAnalyzedTerm(word, stopWords);
            if(parsedWord!=null){
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

                ParsedQueryTerm wordInfo = tmpTerms.get(parsedWord[0]);
                if(wordInfo == null){
                    tmpTerms.put(parsedWord[0], new ParsedQueryTerm( 1, parsedWord[0], word, weight));
                    maxFrequency = Math.max(maxFrequency, 1);
                }
                else{
                    wordInfo.incrementFrequency();
                    wordInfo.setWeight(weight);
                    maxFrequency = Math.max(maxFrequency,(int)wordInfo.getTf());
                }
            }
            
        }
        
        for( Map.Entry<String, ParsedQueryTerm> tmpTerm : tmpTerms.entrySet() ){
            ParsedQueryTerm tmpWordInfo =  tmpTerm.getValue();
            tmpWordInfo.setTf(tmpWordInfo.getTf()/((double)maxFrequency));
            parsedQueryWords.add(tmpWordInfo);
        }
        
        for (ParsedQueryTerm pW : parsedQueryWords){
            System.out.println(pW.getParsedWord()+"  "+pW.getTf()+"  "+pW.getWeight());
        }
        
        return parsedQueryWords;
    }
}
