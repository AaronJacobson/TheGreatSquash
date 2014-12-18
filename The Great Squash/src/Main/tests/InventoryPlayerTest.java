/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.Inventory;
import gameworld.Player;
import items.consumables.Consumable;

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
        Inventory inventory = new Inventory(10);
        player.addToInventory(pancake);
        System.out.println(player.getInventory());
    }
}
