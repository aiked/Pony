

package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import Common.TermNormalizer;
import Common.StopWords;
import PonySearcher.models.ParsedQueryTerm;
import java.io.File;
import java.io.IOException;
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
public class VocabolaryTraitsFetcher {
    private static VocabolaryTraitsFetcher singleInst = null;
    
    private static final String UWN_DB = "resources" + File.separator + "uwnDatabase";
    
    private static final String PREDICATE_TYPE_SEPERATOR = "rel:";
    private static final int PREDICATE_TYPE_SEPERATOR_LENGTH = PREDICATE_TYPE_SEPERATOR.length();
    
    private UWN uwn = null;
    private StopWords stopWords = null;
    private TermNormalizer termNormalizer = null;
    private VocabularyTraitsRetrievalPolicy vocabularyTraitsRetrievalPolicy;
    
    private HashMap<String, SortedIterablePriorityQuery<Statement>> vocabolaryTraitsCollectionMap;
    
    private SortedIterablePriorityQuery<Statement> synonymsCategory = null;
    private SortedIterablePriorityQuery<Statement> meronymsCategory = null;
    private SortedIterablePriorityQuery<Statement> hyponymnsCategory = null;
    private SortedIterablePriorityQuery<Statement> hyperonymsCategory = null;
    private SortedIterablePriorityQuery<Statement> holonymsCategory = null;
    
    private HashSet<String> totalParsedQueryTerms = null;
    private HashSet<String> totalLexicalParsedQueryTerms = null;
    
    private VocabolaryTraitsFetcher(
            StopWords _stopWords, 
            VocabularyTraitsRetrievalPolicy _vocabularyTraitsRetrievalPolicy
    )throws Exception{
        vocabularyTraitsRetrievalPolicy = _vocabularyTraitsRetrievalPolicy;
        stopWords = _stopWords;
        termNormalizer = TermNormalizer.getInstance();
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
        totalParsedQueryTerms = new HashSet();
        totalLexicalParsedQueryTerms = new HashSet();
        
        synonymsCategory = new SortedIterablePriorityQuery(10, comparator);
        meronymsCategory = new SortedIterablePriorityQuery(10, comparator);
        hyponymnsCategory = new SortedIterablePriorityQuery(10, comparator);
        hyperonymsCategory = new SortedIterablePriorityQuery(10, comparator);
        holonymsCategory = new SortedIterablePriorityQuery(10, comparator);
    }
    
    public static void singletonCreate(            
            StopWords _stopWords, 
            VocabularyTraitsRetrievalPolicy _vocabularyTraitsRetrievalPolicy
    ) throws Exception{
        if(singleInst==null)
            singleInst = new VocabolaryTraitsFetcher(_stopWords, _vocabularyTraitsRetrievalPolicy);
    }
    
    public static VocabolaryTraitsFetcher getSingleInst(){
        return singleInst;
    }
    
    public VocabolaryTraits getNewVocabolaryTraits(
            ParsedQueryTerm parsedQueryTerm
    ) throws IOException{

        totalParsedQueryTerms.clear();
        totalLexicalParsedQueryTerms.clear();
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
        String termLang = TermNormalizer.getLanguage(term);
        HashSet<String> languageCodes = TermNormalizer.getLanguageCodes(termLang);
        if(termLang.isEmpty()){
            return null;
        }else{
            Entity entity = Entity.createTerm(term, termLang);
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

            extractTermsFromCategory(
                    synonymsCategory, 
                    vocabolaryTraits.synonyms, 
                    vocabolaryTraits.all,
                    languageCodes, 
                    vocabularyTraitsRetrievalPolicy.getTotalsynonymsToBeFetched()
            );
            extractTermsFromCategory(
                    hyponymnsCategory, 
                    vocabolaryTraits.hyponymns, 
                    vocabolaryTraits.all,
                    languageCodes, 
                    vocabularyTraitsRetrievalPolicy.getTotalhyponymsToBeFetched()
            );
            extractTermsFromCategory(
                    hyperonymsCategory, 
                    vocabolaryTraits.hyperonyms, 
                    vocabolaryTraits.all,
                    languageCodes, 
                    vocabularyTraitsRetrievalPolicy.getTotalhyperonymsToBeFetched()
            );
            extractTermsFromCategory(
                    meronymsCategory, 
                    vocabolaryTraits.meronyms, 
                    vocabolaryTraits.all,
                    languageCodes, 
                    vocabularyTraitsRetrievalPolicy.getTotalmeronymsToBeFetched()
            );
            extractTermsFromCategory(
                    holonymsCategory, 
                    vocabolaryTraits.holonyms, 
                    vocabolaryTraits.all,
                    languageCodes, 
                    vocabularyTraitsRetrievalPolicy.getTotalholonyToBeFetched()
            );

            if(!totalLexicalParsedQueryTerms.contains(parsedQueryTerm.getParsedWord()))
                vocabolaryTraits.all.add(parsedQueryTerm);
            
            return vocabolaryTraits;
        }

    }
    
    private void extractTermsFromCategory(
            SortedIterablePriorityQuery<Statement> category, 
            SortedIterablePriorityQuery<ParsedQueryTerm> terms,
            SortedIterablePriorityQuery<ParsedQueryTerm> all,
            final HashSet<String> supportedLangs,
            final int fetchedMaxSize
    ) throws IOException{
        if(fetchedMaxSize!=0){
            int fechedSize = 0;
            while( !category.isEmpty() ){ 
                Statement stmt = (Statement) category.poll();
                Iterator<Statement> catTerms = uwn.getTermEntities(stmt.getObject());
                while(catTerms.hasNext()){
                    Statement catTerm = catTerms.next();
                    String term = catTerm.getObject().getTermStr();
                    if( supportedLangs.contains(catTerm.getObject().getTermLanguage()) 
                        && !totalParsedQueryTerms.contains(term)){
                        totalParsedQueryTerms.add(term);
                        String[] lexTerm = termNormalizer.getLexicalAnalyzedTerm(term, stopWords);
                        if(lexTerm!=null && !totalLexicalParsedQueryTerms.contains(lexTerm[0])){
                            totalLexicalParsedQueryTerms.add(lexTerm[0]);
                            ParsedQueryTerm parsedQueryTerm = new ParsedQueryTerm(0, lexTerm[0], term, catTerm.getWeight());
                            all.add(parsedQueryTerm);
                            terms.add(parsedQueryTerm);
                            if(++fechedSize==fetchedMaxSize)
                                return;
                        }

                    }  
                }
            } 
        }
    }

    public class VocabolaryTraits{
        
        protected final SortedIterablePriorityQuery<ParsedQueryTerm> synonyms;
        private final SortedIterablePriorityQuery<ParsedQueryTerm> meronyms;
        private final SortedIterablePriorityQuery<ParsedQueryTerm> hyponymns;
        private final SortedIterablePriorityQuery<ParsedQueryTerm> hyperonyms;
        private final SortedIterablePriorityQuery<ParsedQueryTerm> holonyms;
        private final SortedIterablePriorityQuery<ParsedQueryTerm> all;
        
        public VocabolaryTraits(){
            this.synonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
            this.meronyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
            this.hyponymns = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
            this.hyperonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
            this.holonyms = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
            this.all = new SortedIterablePriorityQuery(10, new ParsedQueryTermComparator());
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
        
        public SortedIterablePriorityQuery<ParsedQueryTerm> getAll() {
            return all;
        }
    }


    public static void main(String[] args) throws Exception{
        VocabolaryTraitsFetcher.singletonCreate(new StopWords(), new NormalVocabularyTraitsRetrieval());
        VocabolaryTraitsFetcher singleInst1 = VocabolaryTraitsFetcher.getSingleInst();
        VocabolaryTraits newVocabolaryTraits = singleInst1.getNewVocabolaryTraits( 
                new ParsedQueryTerm(0, "αγάπη", "αγάπη", 0)
        );
       
        for(ParsedQueryTerm vocabolaryTrait:newVocabolaryTraits.all){
            System.out.println(vocabolaryTrait.getWord());
        }
        
    }
}
