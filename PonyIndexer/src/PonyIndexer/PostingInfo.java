
package PonyIndexer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jit
 */
public class PostingInfo implements Serializable{

    private Long id;
    private Double tf;
    private Double vectorSpaceW;
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
    
     public PostingInfo(Long id, Double tf){
        this.id = id;
        this.tf = tf;
        positions = new ArrayList<>();
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

    public Double getVectorSpaceW() {
        return vectorSpaceW;
    }

    public void setVectorSpaceW(Double vectorSpaceW) {
        this.vectorSpaceW = vectorSpaceW;
    }
    
    public ArrayList<Long> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Long> positions) {
        this.positions = positions;
    }
    
    
}
