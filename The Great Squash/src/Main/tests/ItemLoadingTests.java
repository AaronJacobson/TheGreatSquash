/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main.tests;

import bonuses.Bonus;
import items.armours.Armor;
import items.consumables.Consumable;
import items.weapons.Weapon;

/**
 *
 * @author ros_aljacobson001
 */
public class ItemLoadingTests {
    public static void main(String [] args){
//        loadConsumable("pancake");
//        loadBonus("bloodSoaked");
        loadWeapon("slimefork");
    }
    
    public static void loadConsumable(String name){
        Consumable pancake = new Consumable(name);
    }
    
    public static void loadWeapon(String name){
        Weapon slimeFork = new Weapon(name);
    }
    
    public static void loadArmor(String name){
    }
    
    public static void loadBonus(String name){
        Bonus bloodSoaked = new Bonus(name);
    }
}
