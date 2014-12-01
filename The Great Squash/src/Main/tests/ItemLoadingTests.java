/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main.tests;

import items.armours.Armor;
import items.consumables.Consumable;
import items.weapons.Weapon;

/**
 *
 * @author ros_aljacobson001
 */
public class ItemLoadingTests {
    public static void main(String [] args){
        loadConsumable("pancake");
    }
    
    public static void loadConsumable(String name){
        Consumable ted = new Consumable(name);
    }
    
    public static void loadWeapon(String name){
    }
    
    public static void loadArmor(String name){
    }
}
