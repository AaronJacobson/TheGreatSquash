package gameworld;

import items.*;

/**
 *
 * @author Dylan
 */
public class Chest extends Obstacle implements Interactive,HasInventory {
    private Inventory INVENTORY;
    
    public Chest(Board board, int y, int x) {
        super((char)(199), "Chest", false, board, y, x);
        INVENTORY = new Inventory(10,10,10,10);
        // Ç = 199
    }
    

    public void update() {
        
    }
        
    public Inventory getInventory() {
        return INVENTORY;
    }  

    @Override
    public String toServerData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
