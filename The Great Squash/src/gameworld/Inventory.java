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

    public ArrayList<Item> getItems(String type) {
        ArrayList<Item> list = new ArrayList<Item>();
        Item itemType = null;
        
        if(type.toLowerCase().equals("weapon")) {
            itemType = new Weapon(null);
        } else if(type.toLowerCase().equals("armour")) {
            itemType = new Armor(null);
        }else if(type.toLowerCase().equals("consumable")) {
            itemType = new Consumable(null);
        }
        
        try {
            for(int count = 0; count < ITEM_LIST.size(); count++) {
                Item current = ITEM_LIST.get(count);
                if(current.getClass() == itemType.getClass()) {
                    list.add(current);
                }
            }
        } catch(NullPointerException ex) {}
        
        return list;
    }

    public String listToString(ArrayList<Item> list, String type) {
        String output = type.toUpperCase() + ": ";
        if(list.size() > 0) {
            for(int count = 0; count < list.size(); count++) {
                try {
                    Item current = list.get(count);
                    output += "  -" + current;
                } catch(IndexOutOfBoundsException ex) {
                    output += "  -[N/A]";
                }
                if(count != list.size() - 1) {
                    output += "\n";
                }
            }
        }
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
