package Common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Apostolidis
 * @param <T>
 */
public class SortedIterablePriorityQuery<T> extends PriorityQueue<T>{
    public SortedIterable<T> sortedIterator(){
        return new SortedIterable<>(this);
    }
    
    public SortedIterablePriorityQuery(){
        super();
    }
    
    public SortedIterablePriorityQuery(int capasity, Comparator comparator){
        super(capasity, comparator);
    }
    
    public List<T> getOrderedList(){
        ArrayList<T> list = new ArrayList();
        SortedIterable<T> sortedIterator = sortedIterator(); 
        while(sortedIterator.hasNext()){ 
            list.add(sortedIterator.next()); 
        }
        return list;
    }
    
    public Set<T> getSet(){
        Set<T> set = new HashSet();
        SortedIterable<T> sortedIterator = sortedIterator(); 
        while(sortedIterator.hasNext()){ 
            set.add(sortedIterator.next()); 
        }
        return set;
    }
    
    public class SortedIterable<T> implements Iterator
    {
       final PriorityQueue<T> pq;
       public SortedIterable(PriorityQueue <T> source){
         pq = new PriorityQueue(source); 
       }

       @Override
       public boolean hasNext(){
         return !pq.isEmpty();
       }

       @Override
       public T next(){ 
           return pq.poll(); 
       }

       @Override
       public void remove(){ 
           throw new UnsupportedOperationException(""); 
       }
    }
}


