
package railmap;

import java.util.ArrayList;

/**
 *
 * @author jared
 */
public class StationObject {
    private final String name;
    private final Availability mode;
    private final ArrayList<TransferObject> transfers;
    
    public StationObject(String name, Availability mode) {
        transfers = new ArrayList<>();
        this.name = name;
        this.mode = mode;
    }
    
    public String getName() {
        return name;
    }
    
    public Availability getMode() {
        return mode;
    }
    
    public void addTransfer(LineColor color) {
        transfers.add(new TransferObject(color));
    }
    
    public ArrayList<TransferObject> getTransferObjects() {
        return transfers;
    }
}
