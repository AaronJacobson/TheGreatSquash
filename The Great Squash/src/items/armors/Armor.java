/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.armors;

import Auras.Aura;
import tools.CommandHolder;
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
public class Armor implements Item, Displayable, Sendable {
    private String NAME = "UNITIALIZED_ARMOR";
    private char SPRITE = (char)(177);// 177 = ±
    private int ARMOR_CLASS = 10;
    private double DURABILITY = 100;
    private ArrayList<Enchantment> ENCHANTMENTS;
    private ArrayList<Aura> AURAS;
    
    public Armor(String name,char sprite,int ac,double dur){
        ENCHANTMENTS = new ArrayList<Enchantment>();
        AURAS = new ArrayList<Aura>();
        NAME = name;
        SPRITE = sprite;
        ARMOR_CLASS = ac;
        DURABILITY = dur;
    }
    
    public Armor(String name){
        ENCHANTMENTS = new ArrayList<Enchantment>();
        AURAS = new ArrayList<Aura>();
        String fileDirectory = "src/items/armors/" + name + ".armor";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadStats(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Armor: Unable to load the file with the directory of: ");
            System.out.println(fileDirectory);
        }
    }
    
    public void loadStats(Scanner fileScanner){
        fileScanner.next();
        NAME = fileScanner.next();
        fileScanner.next();
        SPRITE = fileScanner.next().charAt(0);
        fileScanner.next();
        ARMOR_CLASS = fileScanner.nextInt();
        fileScanner.next();
        DURABILITY = fileScanner.nextInt();
        loadEnchantments(fileScanner);
    }
    
    public void loadEnchantments(Scanner fileScanner){
        fileScanner.next();
        while(fileScanner.hasNext()){
            fileScanner.next();
            String name = fileScanner.next();
            fileScanner.next();
            int damageBonus = fileScanner.nextInt();
            fileScanner.next();
            int aCBonus = fileScanner.nextInt();
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
            Enchantment enchantment = new Enchantment(name,damageBonus,aCBonus,mPBonus,healthBonus,manaBonus,strengthBonus,enduranceBonus,intelligenceBonus,dexterityBonus);
            ENCHANTMENTS.add(enchantment);
        }
    }
    
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
    
    public void setAC(int toSet){
        ARMOR_CLASS = toSet;
    }
    
    public int getAC(){
        return ARMOR_CLASS;
    }
    
    public void setDurability(double toSet){
        DURABILITY = toSet;
    }
    
    public double getDurability(){
        return DURABILITY;
    }
    
    public void addEnchantment(Enchantment newEnchantment){
        ENCHANTMENTS.add(newEnchantment);
    }
    
    public boolean removeEnchantment(String name){
        for(int currentEnchantment = 0;currentEnchantment < ENCHANTMENTS.size();currentEnchantment++){
            if(ENCHANTMENTS.get(currentEnchantment).getName().equals(name)){
                ENCHANTMENTS.remove(currentEnchantment);
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        return NAME;
    }
    
    public String enchantmentServerData(){
        String toReturn = "";
        if(ENCHANTMENTS.size() == 0){
            toReturn = "NO_ENCHANTMENTS";
        }else{
            for(int currentEnchantment = 0;currentEnchantment < ENCHANTMENTS.size();currentEnchantment++){
                toReturn += ENCHANTMENTS.get(currentEnchantment).toServerData();
            }
        }
        return toReturn;
    }
    
    public String toServerData(){
        return CommandHolder.ARMOR + NAME + " " + SPRITE + " " + ARMOR_CLASS + " " + DURABILITY + " " + enchantmentServerData();
    }
}
