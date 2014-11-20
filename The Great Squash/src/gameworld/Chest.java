package gameworld;

import LAN.TypeHolder;
import items.*;

/**
 *
 * @author Dylan
 */
public class Chest extends Obstacle implements Interactive,HasInventory {
    private Inventory INVENTORY;
    
    public Chest(Board board, int y, int x) {
        super((char)(199), "Chest", false, board, y, x,TypeHolder.OB_CHEST);
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
        return super.getServerData();
    }
}
