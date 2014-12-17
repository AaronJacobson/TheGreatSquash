/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.consumables;

import tools.CommandHolder;
import LAN.Sendable;
import gameworld.Displayable;
import items.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ros_dmlamarca
 */
public class Consumable implements Item, Displayable, Sendable {
    private char SPRITE = (char)(164);// 164 = ¤
    private String NAME = "UNITIALIZED_CONSUMABLE";
    private int HEALTH_ADDED = 0;
    private int MANA_ADDED = 0;
    private int STRENGTH_BONUS = 0;
    private int ENDURANCE_BONUS = 0;
    private int INTELLIGENCE_BONUS = 0;
    private int DEXTERITY_BONUS = 0;
    private int BONUS_LENGTH = 1;
    public Consumable(String name){
        String fileDirectory = "src/items/consumables/" + name + ".consumable";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadFromFile(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Consumable: File not found at this location:");
            System.out.println(fileDirectory);
        }
    }
    
    public void loadFromFile(Scanner fileScanner){
        NAME = fileScanner.next();
        fileScanner.next();
        SPRITE = fileScanner.next().charAt(0);
        fileScanner.next();
        HEALTH_ADDED = fileScanner.nextInt();
        fileScanner.next();
        MANA_ADDED = fileScanner.nextInt();
        fileScanner.next();
        STRENGTH_BONUS = fileScanner.nextInt();
        fileScanner.next();
        ENDURANCE_BONUS = fileScanner.nextInt();
        fileScanner.next();
        INTELLIGENCE_BONUS = fileScanner.nextInt();
        fileScanner.next();
        DEXTERITY_BONUS = fileScanner.nextInt();
        fileScanner.next();
        BONUS_LENGTH = fileScanner.nextInt();
    }
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
    
    public String toString() {
        return NAME;
    }
    
    public String toServerData(){
        return CommandHolder.CONSUMABLE + NAME + " " + SPRITE + " " + HEALTH_ADDED + " " + MANA_ADDED + " " + STRENGTH_BONUS + " " + ENDURANCE_BONUS + " " + INTELLIGENCE_BONUS + " " + DEXTERITY_BONUS + " " + BONUS_LENGTH;
    }
}
