

package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import static Common.TermNormalizer.getLanguage;
import PonySearcher.models.ParsedQueryTerm;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.lexvo.uwn.Entity;
import org.lexvo.uwn.Statement;
import org.lexvo.uwn.UWN;

/**
 *
 * @author Apostolidis
 */
public class VocabolaryTraits {
    private static final String UWN_DB = "resources" + File.separator + "uwnDatabase";
    
    private static final String PREDICATE_TYPE_SEPERATOR = "rel:";
    private static final int PREDICATE_TYPE_SEPERATOR_LENGTH = PREDICATE_TYPE_SEPERATOR.length();
    
    private static UWN uwn = null;
    
    private static HashMap<String, SortedIterablePriorityQuery<Statement>> vocabolaryTraitsCollectionMap;
    
    private static SortedIterablePriorityQuery<Statement> synonymsCategory = null;
    private static SortedIterablePriorityQuery<Statement> meronymsCategory = null;
    private static SortedIterablePriorityQuery<Statement> hyponymnsCategory = null;
    private static SortedIterablePriorityQuery<Statement> hyperonymsCategory = null;
    private static SortedIterablePriorityQuery<Statement> holonymsCategory = null;
    
    private final SortedIterablePriorityQuery<ParsedQueryTerm> synonyms;
    private final SortedIterablePriorityQuery<ParsedQueryTerm> meronyms;
    private final SortedIterablePriorityQuery<ParsedQueryTerm> hyponymns;
    private final SortedIterablePriorityQuery<ParsedQueryTerm> hyperonyms;
    private final SortedIterablePriorityQuery<ParsedQueryTerm> holonyms;
        
    public static void InitiateVocabolaryTraits() throws Exception{
        File dbFilePath = new File( UWN_DB );
        uwn = new UWN( dbFilePath );
        vocabolaryTraitsCollectionMap = new HashMap();
        
        Comparator<Statement> comparator = new Comparator<Statement>() {
            @Override
            public int compare(Statement a, Statement b) {
                if      (a.getWeight()==b.getWeight())  return 0;
                else if (a.getWeight()>b.getWeight())   return 1;
                else                                    return -1;
            }
        };
        synonymsCategory = new SortedIterablePriorityQuery(10, comparator);
        meronymsCategory = new SortedIterablePriorityQuery(10, comparator);
        hyponymnsCategory = new SortedIterablePriorityQuery(10, comparator);
        hyperonymsCategory = new SortedIterablePriorityQuery(10, comparator);
        holonymsCategory = new SortedIterablePriorityQuery(10, comparator);
    }
    
    public static VocabolaryTraits getNewVocabolaryTraits(
            ParsedQueryTerm parsedQueryTerm,             
            int synonymsSize, 
            int meronymsSize, 
            int hyponymsSize, 
            int hyperonymsSize, 
            int holonymsSize
    ) throws IOException{
        assert(uwn!=null);
        
        synonymsCategory.clear();
        meronymsCategory.clear();
        hyponymnsCategory.clear();
        hyperonymsCategory.clear();
        holonymsCategory.clear();
        
        vocabolaryTraitsCollectionMap.put("means", synonymsCategory);
        vocabolaryTraitsCollectionMap.put("closely_related", synonymsCategory);
        vocabolaryTraitsCollectionMap.put("similar", synonymsCategory);
        vocabolaryTraitsCollectionMap.put("subclass", hyponymnsCategory);
        vocabolaryTraitsCollectionMap.put("has_subclass", hyperonymsCategory);
        vocabolaryTraitsCollectionMap.put("part_of", meronymsCategory);
        vocabolaryTraitsCollectionMap.put("has_part", holonymsCategory);
        
        String term = parsedQueryTerm.getWord();
        HashSet<String> termLangs = getLanguage(term);
        if(termLangs.isEmpty()){
            return null;
        }else{
            Entity entity = Entity.createTerm(term, termLangs.iterator().next());
            Iterator<Statement> it = uwn.get(entity);
            while (it.hasNext()) {
                Statement stmt = it.next();   


                String predicateType = stmt.getPredicate().getId();
                assert(predicateType.startsWith(PREDICATE_TYPE_SEPERATOR));
                String type = predicateType.substring(PREDICATE_TYPE_SEPERATOR_LENGTH);
                SortedIterablePriorityQuery<Statement> vocabularyTraitCollection 
                        = vocabolaryTraitsCollectionMap.get(type);
                if(vocabularyTraitCollection!=null){
                    vocabularyTraitCollection.add(stmt);
                }
            }

            VocabolaryTraits vocabolaryTraits = new VocabolaryTraits();

            extractTermsFromCategory(synonymsCategory, vocabolaryTraits.synonyms, termLangs);
            extractTermsFromCategory(hyponymnsCategory, vocabolaryTraits.hyponymns, termLangs);
            extractTermsFromCategory(hyperonymsCategory, vocabolaryTraits.hyperonyms, termLangs);
            extractTermsFromCategory(meronymsCategory, vocabolaryTraits.meronyms, termLangs);
            extractTermsFromCategory(holonymsCategory, vocabolaryTraits.holonyms, termLangs);

            return vocabolaryTraits;
        }
    }
    
    private static void extractTermsFromCategory(
            SortedIterablePriorityQuery<Statement> category, 
            SortedIterablePriorityQuery<ParsedQueryTerm> terms,
            final HashSet<String> supportedLangs
    ) throws IOException{
        while( !category.isEmpty() ){ 
            Statement stmt = (Statement) category.poll();
            Iterator<Statement> catTerms = uwn.getTermEntities(stmt.getObject());
            while(catTerms.hasNext()){
                Statement catTerm = catTerms.next();
                //if(supportedLangs.contains(catTerm.getObject().getTermLanguage()))
                    terms.add( new ParsedQueryTerm(0, catTerm.getObject().getTermStr(), catTerm.getWeight()) );
            }
        }
    }

    private VocabolaryTraits(){
        this.synonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
        this.meronyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
        this.hyponymns = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
        this.hyperonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
        this.holonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
    }
    
    private class ParsedQueryTermComparator implements Comparator<ParsedQueryTerm>{
        @Override
        public int compare(ParsedQueryTerm a, ParsedQueryTerm b) {
            if      (a.getWeight()==b.getWeight())  return 0;
            else if (a.getWeight()>b.getWeight())   return 1;
            else                                    return -1;
        }

    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getSynonyms() {
        return synonyms;
    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getMeronyms() {
        return meronyms;
    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getHyponymns() {
        return hyponymns;
    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getHyperonyms() {
        return hyperonyms;
    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getHolonyms() {
        return holonyms;
    }
    
    
    
    public static void main(String[] args) throws Exception{
        VocabolaryTraits.InitiateVocabolaryTraits();
        VocabolaryTraits vocabolaryTraits = VocabolaryTraits.getNewVocabolaryTraits( 
                new ParsedQueryTerm(0, "αγαπη", 0), 10, 10, 10, 10, 10 
            );
        for(ParsedQueryTerm vocabolaryTrait:vocabolaryTraits.synonyms){
            System.out.println(vocabolaryTrait.getWord());
        }
        
    }
}
