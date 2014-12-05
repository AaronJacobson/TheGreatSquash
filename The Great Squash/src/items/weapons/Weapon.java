/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.weapons;

import LAN.CommandHolder;
import LAN.Sendable;
import enchantments.Enchantment;
import gameworld.Displayable;
import items.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ros_dmlamarca
 */
public class Weapon implements Item, Displayable, Sendable {

    private String NAME = "UNITIALIZED_WEAPON";
    private char SPRITE = (char) (167);// 167 = §
    private String DAMAGE_STAT = "UNITIALIZED_STAT";
    private int DEFENSE;
    private int ATTACK;
    private int RANGE;
    private ArrayList<Enchantment> ENCHANTMENTS;

    public class DamageStats {

        public static final String DEXTERITY = "DAMAGE_IS_DONE_WITH_DEXTERITY";
        public static final String STRENGTH = "DAMAGE_IS_DONE_WITH_STRENGTH";
        public static final String INTELLIGENCE = "DAMAGE_IS_DONE_WITH_INTELLIGENCE";
        public static final String SPEED = "DAMAGE_IS_DONE_WITH_SPEED";
        public static final String ENDURANCE = "DAMAGE_IS_DONE_WITH_ENDURANCE";
    }

    public Weapon(String name, char sprite, String stat, int defense, int attack, int range) {
        NAME = name;
        SPRITE = sprite;
        DEFENSE = defense;
        ATTACK = attack;
        RANGE = range;
        ENCHANTMENTS = new ArrayList<Enchantment>();
    }

    public Weapon(String name) {
        ENCHANTMENTS = new ArrayList<Enchantment>();
        String fileDirectory = "src/items/weapons/" + name + ".weapon";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadStats(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Weapon: Unable to load file from: ");
            System.out.println(fileDirectory);
        }
    }

    public void loadStats(Scanner fileScanner) {
        fileScanner.next();
        NAME = fileScanner.next();
        fileScanner.next();
        SPRITE = fileScanner.next().charAt(0);
        fileScanner.next();
        DAMAGE_STAT = fileScanner.next();
        fileScanner.next();
        ATTACK = fileScanner.nextInt();
        fileScanner.next();
        DEFENSE = fileScanner.nextInt();
        fileScanner.next();
        RANGE = fileScanner.nextInt();
        loadEnchantments(fileScanner);
    }

    public void loadEnchantments(Scanner fileScanner) {
        fileScanner.next();
        while (fileScanner.hasNext()) {
            fileScanner.next();
            String name = fileScanner.next();
            fileScanner.next();
            int damageBonus = fileScanner.nextInt();
            fileScanner.next();
            int acBonus = fileScanner.nextInt();
            fileScanner.next();
            int mPBonus = fileScanner.nextInt();
            fileScanner.next();
            int healthBonus = fileScanner.nextInt();
            fileScanner.next();
            int manaBonus = fileScanner.nextInt();
            fileScanner.next();
            int strengthBonus = fileScanner.nextInt();
            fileScanner.next();
            int enduranceBonus = fileScanner.nextInt();
            fileScanner.next();
            int intelligenceBonus = fileScanner.nextInt();
            fileScanner.next();
            int dexterityBonus = fileScanner.nextInt();
            Enchantment bonus = new Enchantment(name,damageBonus, acBonus, mPBonus, healthBonus, manaBonus, strengthBonus, enduranceBonus, intelligenceBonus, dexterityBonus);
            addEnchantment(bonus);
        }
    }

    public void addEnchantment(Enchantment bonusToAdd) {
        ENCHANTMENTS.add(bonusToAdd);
    }

    public boolean removeEnchantment(String name) {
        for (int currentBonus = 0; currentBonus < ENCHANTMENTS.size(); currentBonus++) {
            if (ENCHANTMENTS.get(currentBonus).getName().equals(name)) {
                ENCHANTMENTS.remove(currentBonus);
                return true;
            }
        }
        return false;
    }

    public void setSprite(char sprite) {
        SPRITE = sprite;
    }

    public char getSprite() {
        return SPRITE;
    }

    public String enchantmentServerData() {
        String toReturn = "";
        if (ENCHANTMENTS.size() == 0) {
            toReturn = "NO_BONUSES";
        } else {
            for (int currentBonus = 0; currentBonus < ENCHANTMENTS.size(); currentBonus++) {
                toReturn += ENCHANTMENTS.get(currentBonus).toServerData();
            }
        }
        return toReturn;
    }

    public String toServerData() {
        return CommandHolder.WEAPON + NAME + " " + SPRITE + " " + DEFENSE + " " + ATTACK + " " + RANGE + " " + enchantmentServerData();
    }
}
