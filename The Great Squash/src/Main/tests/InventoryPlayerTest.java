/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.Creature;
import gameworld.Inventory;
import gameworld.Player;
import items.Item;
import items.consumables.Consumable;
import items.weapons.Weapon;

/**
 *
 * @author ros_dmlamarca
 */
public class InventoryPlayerTest {
    public static void main(String[] args) {
        Player player = new Player("Hardcore");
        Consumable pancake = new Consumable("pancake");
        System.out.println(pancake);
        player.addToInventory(pancake);
        player.addToInventory(pancake);
        Inventory inventory = new Inventory(10);
        player.addToInventory(new Weapon("slimefork"));
        System.out.println(player.getInventory());
        //System.out.println(player.getInventory().);
    }
}
