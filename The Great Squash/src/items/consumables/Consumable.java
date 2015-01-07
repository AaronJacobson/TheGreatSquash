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
import tools.CreateFromDocument;

/**
 *
 * @author ros_dmlamarca
 */
public class Consumable implements Item, Displayable, Sendable {

    private char SPRITE = (char) (164);// 164 = ¤
    private String NAME = "UNITIALIZED_CONSUMABLE";
    private int HEALTH_ADDED = 0;
    private int MANA_ADDED = 0;
//    private int STRENGTH_BONUS = 0;
//    private int ENDURANCE_BONUS = 0;
//    private int INTELLIGENCE_BONUS = 0;
//    private int DEXTERITY_BONUS = 0;
//    private int BONUS_LENGTH = 1;

    public Consumable(String name) {
        String fileDirectory = "src/items/consumables/" + name + ".consumable";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadFromFile(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Consumable: File not found at this location:");
            System.out.println(fileDirectory);
        }
    }

    public void loadFromFile(Scanner fileScanner) {
        NAME = CreateFromDocument.getLineElement(fileScanner.nextLine());
        SPRITE = CreateFromDocument.getLineElement(fileScanner.nextLine()).charAt(0);
        HEALTH_ADDED = Integer.parseInt(CreateFromDocument.getLineElement(fileScanner.nextLine()));
        MANA_ADDED = Integer.parseInt(CreateFromDocument.getLineElement(fileScanner.nextLine()));
    }

    public String getName() {
        return NAME;
    }

    public char getSprite() {
        return SPRITE;
    }
    
    public int getHealthAdded() {
        return HEALTH_ADDED;
    }
    
    public int getManaAdded() {
        return MANA_ADDED;
    }

    public String toString() {
        return NAME;
    }

    public String toServerData() {
        //return CommandHolder.CONSUMABLE + NAME + " " + SPRITE + " " + HEALTH_ADDED + " " + MANA_ADDED + " " + STRENGTH_BONUS + " " + ENDURANCE_BONUS + " " + INTELLIGENCE_BONUS + " " + DEXTERITY_BONUS + " " + BONUS_LENGTH;
        return "(.)(.)";
    }
}
