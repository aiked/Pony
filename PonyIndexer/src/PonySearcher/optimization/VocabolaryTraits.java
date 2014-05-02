

package PonySearcher.optimization;

import Common.SortedIterablePriorityQuery;
import PonySearcher.models.ParsedQueryTerm;

/**
 *
 * @author Apostolidis
 */
public class VocabolaryTraits {
    
    public VocabolaryTraits(ParsedQueryTerm parsedQueryTerm){

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
}
