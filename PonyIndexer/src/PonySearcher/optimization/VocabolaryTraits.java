

package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import static Common.TermNormalizer.getLanguage;
import PonySearcher.models.ParsedQueryTerm;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
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
    
    private SortedIterablePriorityQuery<Statement> synonyms;
    private SortedIterablePriorityQuery<Statement> meronyms;
    private SortedIterablePriorityQuery<Statement> hyponymns;
    private SortedIterablePriorityQuery<Statement> hyperonyms;
    private SortedIterablePriorityQuery<Statement> holonyms;
    
    public static void InitiateVocabolaryTraits() throws Exception{
        File dbFilePath = new File( UWN_DB );
        uwn = new UWN( dbFilePath );
        vocabolaryTraitsCollectionMap = new HashMap();
    }
    
    public static VocabolaryTraits getNewVocabolaryTraits(ParsedQueryTerm parsedQueryTerm) throws IOException{
        assert(uwn!=null);
        
        VocabolaryTraits VocabolaryTraits = new VocabolaryTraits();
        vocabolaryTraitsCollectionMap.put("means", VocabolaryTraits.synonyms);
        vocabolaryTraitsCollectionMap.put("closely_related", VocabolaryTraits.synonyms);
        vocabolaryTraitsCollectionMap.put("similar", VocabolaryTraits.synonyms);
        vocabolaryTraitsCollectionMap.put("subclass", VocabolaryTraits.hyponymns);
        vocabolaryTraitsCollectionMap.put("has_subclass", VocabolaryTraits.hyperonyms);
        vocabolaryTraitsCollectionMap.put("part_of", VocabolaryTraits.meronyms);
        vocabolaryTraitsCollectionMap.put("has_part", VocabolaryTraits.holonyms);
        

        String term = parsedQueryTerm.getWord();
        Entity entity = Entity.createTerm(term, getLanguage(term));
        Iterator<Statement> it = uwn.get(entity);
        while (it.hasNext()) {
            Statement stmt = it.next();

            Entity subject = stmt.getSubject();
            Entity object = stmt.getObject();
            Entity predicate = stmt.getPredicate();
            
            String predicateType = predicate.getId();
            assert(predicateType.startsWith(PREDICATE_TYPE_SEPERATOR));
            String type = predicateType.substring(PREDICATE_TYPE_SEPERATOR_LENGTH);
            SortedIterablePriorityQuery<Statement> vocabularyTraitCollection 
                    = vocabolaryTraitsCollectionMap.get(type);
            if(vocabularyTraitCollection!=null){
                vocabularyTraitCollection.add(stmt);
            }
            //System.out.println("Entity: " + subject.getId() + " Relation " + predicate.getId() + " :\"" + object.getId() + "\" with weight " + stmt.getWeight());
        }
        return null;
    }

    public VocabolaryTraits() {
        this.synonyms = new SortedIterablePriorityQuery();
        this.meronyms = new SortedIterablePriorityQuery();
        this.hyponymns = new SortedIterablePriorityQuery();
        this.hyperonyms = new SortedIterablePriorityQuery();
        this.holonyms = new SortedIterablePriorityQuery();
    }
    
    public class PageRankInfoComparator implements Comparator<Statement>{
        @Override
        public int compare(Statement a, Statement b) {
            if      (a.getWeight()==b.getWeight())  return 0;
            else if (a.getWeight()>b.getWeight())   return 1;
            else                                    return -1;
        }

    }
    
    public SortedIterablePriorityQuery<ParsedQueryTerm> getSynonyms(){
        return null;
    }

    public SortedIterablePriorityQuery<ParsedQueryTerm> getMeronyms(){
        return null;
    }
    
    public SortedIterablePriorityQuery<ParsedQueryTerm> getHyponyms(){
        return null;
    }
    
    public SortedIterablePriorityQuery<ParsedQueryTerm> getHypernyms(){
        return null;
    }
    
    public SortedIterablePriorityQuery<ParsedQueryTerm> getHolonyms(){
        return null;
    }
    
    public static void main(String[] args) throws Exception{
        VocabolaryTraits.InitiateVocabolaryTraits();
        VocabolaryTraits vocabolaryTraits= VocabolaryTraits.getNewVocabolaryTraits( 
                new ParsedQueryTerm(0, "play", 0) 
            );
        
    }
}
