/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.Creature;
import gameworld.Inventory;
import gameworld.Player;
import items.Item;
import items.armors.Armor;
import items.consumables.Consumable;
import items.weapons.Weapon;

/**
 *
 * @author ros_dmlamarca
 */
public class InventoryPlayerTest {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(5);
        inventory.addToInventory(new Consumable("pancake"));
        inventory.addToInventory(new Consumable("pancake"));
        inventory.addToInventory(new Weapon("slimefork"));
        inventory.addToInventory(new Armor("battletuxedo"));
        System.out.println(inventory);
    }
}
