/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import enchantments.Enchantment;
import items.armors.Armor;
import items.consumables.Consumable;
import items.weapons.Weapon;

/**
 *
 * @author ros_aljacobson001
 */
public class ItemLoadingTests {

    public static void main(String[] args) {
//        loadConsumable("pancake");
//        loadEnchantment("bloodSoaked");
//        loadWeapon("slimefork");
//        loadArmor("battletuxedo");
    }

    public static void loadConsumable(String name) {
        Consumable pancake = new Consumable(name);
        System.out.println(pancake.toServerData());
    }

    public static void loadWeapon(String name) {
        Weapon slimeFork = new Weapon(name);
        System.out.println(slimeFork.toServerData());
    }

    public static void loadArmor(String name) {
        Armor battleTuxedo = new Armor(name);
        System.out.println(battleTuxedo.toServerData());
    }

    public static void loadEnchantment(String name) {
        Enchantment bloodSoaked = new Enchantment(name);
        System.out.println(bloodSoaked.toServerData());
    }
}
