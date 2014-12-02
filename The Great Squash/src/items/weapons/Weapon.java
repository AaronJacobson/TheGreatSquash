/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.weapons;

import bonuses.Bonus;
import LAN.Sendable;
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
    private int DEFENSE;
    private int ATTACK;
    private int RANGE;
    private ArrayList<Bonus> BONUSES;

    public Weapon(String name,char sprite,int defense,int attack,int range){
        NAME = name;
        SPRITE = sprite;
        DEFENSE = defense;
        ATTACK = attack;
        RANGE = range;
        BONUSES = new ArrayList<Bonus>();
    }
    public Weapon(String name){
        BONUSES = new ArrayList<Bonus>();
        String fileDirectory = "src/items/weapons/" + name + ".weapon";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadStats(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Weapon: Unable to load file from: ");
            System.out.println(fileDirectory);
        }
        System.out.println(toServerData());
    }
    
    public void loadStats(Scanner fileScanner){
        fileScanner.next();
        NAME = fileScanner.next();
        fileScanner.next();
        SPRITE = fileScanner.next().charAt(0);
        fileScanner.next();
        ATTACK = fileScanner.nextInt();
        fileScanner.next();
        DEFENSE = fileScanner.nextInt();
        fileScanner.next();
        RANGE = fileScanner.nextInt();
        loadBonuses(fileScanner);
    }
    
    public void loadBonuses(Scanner fileScanner){
        fileScanner.next();
        while(fileScanner.hasNext()){
            fileScanner.next();
            String name = fileScanner.next();
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
            Bonus bonus = new Bonus(name,acBonus,mPBonus,healthBonus,manaBonus,strengthBonus,enduranceBonus,intelligenceBonus,dexterityBonus);
        }
    }
    
    public void addBonus(Bonus bonusToAdd){
        BONUSES.add(bonusToAdd);
    }
    
    public boolean removeBonus(String name){
        for(int currentBonus = 0;currentBonus < BONUSES.size();currentBonus++){
            if(BONUSES.get(currentBonus).getName().equals(name)){
                BONUSES.remove(currentBonus);
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
    
    public String bonusServerData(){
        String toReturn = "";
        if(BONUSES.size() == 0){
            toReturn = "NO_BONUSES";
        }else{
            for(int currentBonus = 0;currentBonus < BONUSES.size();currentBonus++){
                
            }
        }
        return toReturn;
    }

    public String toServerData() {
        return " | " + NAME + " " + SPRITE + " " + DEFENSE + " " + ATTACK + " " + RANGE + " " + bonusServerData();
    }
}
