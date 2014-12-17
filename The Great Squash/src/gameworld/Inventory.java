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
//        System.out.println(item);
//        if (item instanceof Weapon && WEAPONS.size() < WEAPONS_SIZE_LIMIT) {
//            System.out.println("Weapon");
//            WEAPONS.add((Weapon) (item));
//        } else if (item instanceof Armor && ARMORS.size() < ARMORS_SIZE_LIMIT) {
//            System.out.println("Armour");
//            ARMORS.add((Armor) (item));
//        } else if (item instanceof SpellBook && SPELLBOOKS.size() < SPELLBOOKS_SIZE_LIMIT) {
//            System.out.println("Spellbook");
//            SPELLBOOKS.add((SpellBook) (item));
//        } else if (item instanceof Consumable && CONSUMABLES.size() < CONSUMABLES_SIZE_LIMIT) {
//            System.out.println("Consumable");
//            CONSUMABLES.add((Consumable) (item));
//        } else {
//            System.err.println("Couldn't add " + item + " to Inventory");
//        }
    }



    public String listToString(String title) {
        int listLimit = 1;
   
        String output = title.toUpperCase() + ":\n";
        for (int i = 0; i < listLimit; i++) {
            try {
                Item current = ITEM_LIST.get(i);
                output += "  -" + current.toString();
            } catch (IndexOutOfBoundsException ex) {
                output += "  -[N/A]";
            }
            if(i != listLimit - 1) {
                output += "\n";
            }
        }
        return output;
    }

    public String toString() {
        String output = "";
//        output += listToString("weapon") + "\n";
//        output += listToString("armour") + "\n";
//        output += listToString("spellbook") + "\n";
//        output += listToString("consumable");
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
