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

    ArrayList<Consumable> CONSUMABLES;
    int CONSUMABLES_SIZE_LIMIT;
    ArrayList<SpellBook> SPELLBOOKS;
    int SPELLBOOKS_SIZE_LIMIT;
    ArrayList<Armor> ARMORS;
    int ARMORS_SIZE_LIMIT;
    ArrayList<Weapon> WEAPONS;
    int WEAPONS_SIZE_LIMIT;

    public Inventory(int weaponSize, int armourSize, int spellbookSize , int consumableSize) {
        CONSUMABLES = new ArrayList<Consumable>();
        SPELLBOOKS = new ArrayList<SpellBook>();
        ARMORS = new ArrayList<Armor>();
        WEAPONS = new ArrayList<Weapon>();

        CONSUMABLES_SIZE_LIMIT = consumableSize;
        SPELLBOOKS_SIZE_LIMIT = spellbookSize;
        ARMORS_SIZE_LIMIT = armourSize;
        WEAPONS_SIZE_LIMIT = weaponSize;
    }

    public void addToInventory(Item item) {
        if (item instanceof Weapon && WEAPONS.size() < WEAPONS_SIZE_LIMIT) {
            WEAPONS.add((Weapon) (item));
        } else if (item instanceof Armor && ARMORS.size() < ARMORS_SIZE_LIMIT) {
            ARMORS.add((Armor) (item));
        } else if (item instanceof SpellBook && SPELLBOOKS.size() < SPELLBOOKS_SIZE_LIMIT) {
            SPELLBOOKS.add((SpellBook) (item));
        } else if (item instanceof Consumable && CONSUMABLES.size() < CONSUMABLES_SIZE_LIMIT) {
            CONSUMABLES.add((Consumable) (item));
        }
    }

    public ArrayList<Weapon> getWeaponList() {
        return WEAPONS;
    }

    public ArrayList<Armor> getArmourList() {
        return ARMORS;
    }

    public ArrayList<SpellBook> getSpellBookList() {
        return SPELLBOOKS;
    }

    public ArrayList<Consumable> getConsumableList() {
        return CONSUMABLES;
    }

    public String listToString(String title) {
        ArrayList<Item> list = new ArrayList<Item>();
        int listLimit = 1;
        
        if(title.toLowerCase().equals("weapon") || title.toLowerCase().equals("weapons")) {
            list.addAll(WEAPONS);
            listLimit = WEAPONS_SIZE_LIMIT;
        } else if(title.toLowerCase().equals("armour") || title.toLowerCase().equals("armours")) {
            list.addAll(ARMORS);
            listLimit = ARMORS_SIZE_LIMIT;
        } else if(title.toLowerCase().equals("consumable") || title.toLowerCase().equals("consumables")) {
            list.addAll(CONSUMABLES);
            listLimit = CONSUMABLES_SIZE_LIMIT;
        } else if(title.toLowerCase().equals("spellbook") || title.toLowerCase().equals("spellbooks")) {
            list.addAll(SPELLBOOKS);
            listLimit = SPELLBOOKS_SIZE_LIMIT;
        }
        
        String output = title.toUpperCase() + ":\n";
        for (int i = 1; i < listLimit; i++) {
            try {
                Item current = list.get(i);
                output += "  -" + current.toString() + "\n";
            } catch (IndexOutOfBoundsException ex) {
                output += "  -[N/A]\n";
            }
        }
        try {
            Item current = list.get(listLimit - 1);
            output += "  -" + current.toString();
        } catch (IndexOutOfBoundsException ex) {
            output += "  -[N/A]";
        }

        return output;
    }

    public String toString() {
        String output = "";
        output += listToString("weapon") + "\n";
        output += listToString("armour") + "\n";
        output += listToString("spellbook") + "\n";
        output += listToString("consumable");
        return output;
    }
    
    public String toServerData(){
        String serverData = "";
        for(int currentConsumable = 0;currentConsumable < CONSUMABLES.size();currentConsumable++){
            serverData += CONSUMABLES.get(currentConsumable).toServerData();
        }
        for(int currentArmor = 0;currentArmor < ARMORS.size();currentArmor++){
            serverData += ARMORS.get(currentArmor).toServerData();
        }
        for(int currentWeapon = 0;currentWeapon < WEAPONS.size();currentWeapon++){
            serverData += WEAPONS.get(currentWeapon).toServerData();
        }
        return serverData;
    }
}
