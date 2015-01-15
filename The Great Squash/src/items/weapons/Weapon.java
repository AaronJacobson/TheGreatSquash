/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.weapons;

import tools.NetworkInfo;
import LAN.Sendable;
import enchantments.Enchantment;
import gameworld.Displayable;
import items.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import tools.CreateFromDocument;

/**
 *
 * @author ros_dmlamarca
 */
public class Weapon implements Item, Displayable, Sendable {
    private String FILE_NAME;
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
        FILE_NAME = name;
        String fileDirectory = "src/items/weapons/" + name + ".weapon";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadFromFile(CreateFromDocument.getNextFileElement(fileScanner));
        } catch (FileNotFoundException ex) {
            System.out.println("Weapon: Unable to load file from: ");
            System.out.println(fileDirectory);
        }
    }

    public void loadFromFile(String fileElement) {
        Scanner elementScanner = new Scanner(fileElement);
        NAME = CreateFromDocument.getLineElement(elementScanner.nextLine());
        SPRITE = CreateFromDocument.getLineElement(elementScanner.nextLine()).charAt(0);
        DAMAGE_STAT = CreateFromDocument.getLineElement(elementScanner.nextLine());
        ATTACK = Integer.parseInt(CreateFromDocument.getLineElement(elementScanner.nextLine()));
        DEFENSE = Integer.parseInt(CreateFromDocument.getLineElement(elementScanner.nextLine()));
        RANGE = Integer.parseInt(CreateFromDocument.getLineElement(elementScanner.nextLine()));
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

    public char getSprite() {
        return SPRITE;
    }
    
    public String getName() {
        return NAME;
    }
    
    public String getFileName() {
        return FILE_NAME;
    }
    
    public String getDamageStat() {
        return DAMAGE_STAT;
    }
    
    public int getDefense() {
        return DEFENSE;
    }

    public int getAttack() {
        return ATTACK;
    }
    
    public int getRange() {
        return RANGE;
    }
    
    public String toString() {
        return NAME;
    }

    public String toServerData() {
//        return NetworkInfo.WEAPON + NAME + " " + SPRITE + " " + DEFENSE + " " + ATTACK + " " + RANGE + " " + enchantmentServerData();
        return "8====D";
    }
}
