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
        Weapon weapon = new Weapon("quarterstaff");
        System.out.println(weapon.getName());
        System.out.println(weapon.getSprite());
        System.out.println(weapon.getAttack());
        System.out.println(weapon.getDefense());
        System.out.println(weapon.getRange());
        System.out.println(weapon.getDamageStat() + "\n");
        
        Armor armor = new Armor("battletuxedo");
        System.out.println(armor.getName());
        System.out.println(armor.getSprite());
        System.out.println(armor.getAC() + "\n");
        
        Consumable consumable = new Consumable("pancake");
        System.out.println(consumable.getName());
        System.out.println(consumable.getSprite());
        System.out.println(consumable.getHealthAdded());
        System.out.println(consumable.getManaAdded());
    }
}
