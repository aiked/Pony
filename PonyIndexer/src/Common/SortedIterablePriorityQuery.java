package Common;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

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


