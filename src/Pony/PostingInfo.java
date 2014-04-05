
package Pony;

import java.util.ArrayList;

/**
 *
 * @author jit
 */
public class PostingInfo {

    private Long id;
    private Double tf;
    private ArrayList<Long> positions;
    
    public PostingInfo(){
        id = -1L;
        tf = -1D;
        positions = new ArrayList<>();
        
    }
    
    public PostingInfo(Long id, Long position){
        this.id = id;
        tf = -1D;
        positions = new ArrayList<>();
        positions.add(position);
    }

    public void addPosition(Long position){
        positions.add(position);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTf() {
        return tf;
    }

    public void setTf(Double tf) {
        this.tf = tf;
    }

    public ArrayList<Long> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Long> positions) {
        this.positions = positions;
    }
    
    
}
