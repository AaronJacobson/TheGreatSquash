package gameworld;

import items.armors.Armor;
import items.consumables.Consumable;
import items.Item;
import items.spellbooks.SpellBook;
import items.weapons.Weapon;
import java.util.ArrayList;

/**
 *
 * @author ros_dmlamarca
 */
public class Inventory {

    ArrayList<Item> ITEM_LIST;
    int INVENTORY_SIZE;

    public Inventory(int inventorySize) {
        ITEM_LIST = new ArrayList<Item>();
        INVENTORY_SIZE = inventorySize;
    }

    public void addToInventory(Item item) {
        if(ITEM_LIST.size() < INVENTORY_SIZE) {
            ITEM_LIST.add(item);
        }
    }



    public String listToString(String title) {
        String output = title.toUpperCase() + ":\n";
        return output;
    }

    public String toString() {
        String output = "";
        
        return output;
    }
    
    public String toServerData(){
        String serverData = "";
//        for(int currentConsumable = 0;currentConsumable < CONSUMABLES.size();currentConsumable++){
//            serverData += CONSUMABLES.get(currentConsumable).toServerData();
//        }
//        for(int currentArmor = 0;currentArmor < ARMORS.size();currentArmor++){
//            serverData += ARMORS.get(currentArmor).toServerData();
//        }
//        for(int currentWeapon = 0;currentWeapon < WEAPONS.size();currentWeapon++){
//            serverData += WEAPONS.get(currentWeapon).toServerData();
//        }
        return serverData;
    }
}
