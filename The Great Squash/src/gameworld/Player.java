/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import items.armors.Armor;
import items.consumables.Consumable;
import items.weapons.Weapon;
import tools.TypeHolder;
import tools.CreateFromDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ros_dmlamarca
 */
public class Player extends Creature {

    private String SPECIES;
    private String CLASS;

    public Player(char sprite, Board board, int y, int x, String name) {
        super(sprite, board, y, x, name, TypeHolder.PLAYER);
    }

    public Player(String name, String playerClass, String species, char sprite) {
        super();
        TYPE = TypeHolder.PLAYER;
        CLASS = playerClass;
        SPRITE = sprite;
        SPECIES = species;
        NAME = name;
    }

    public Player(String name) {
        NAME = name;
        String fileDirectory = "src/gameworld/players/" + name + ".player";
        loadFromFile(fileDirectory);
    }

    public void loadFromFile(String name) {
        try {
            Scanner fileScanner = new Scanner(new File(name));
            loadStats(CreateFromDocument.getNextFileElement(fileScanner));
            
            CreateFromDocument.getNextFileElement(fileScanner);
            String weaponElement = CreateFromDocument.getNextFileElement(fileScanner);
            
            CreateFromDocument.getNextFileElement(fileScanner);
            String armourElement = CreateFromDocument.getNextFileElement(fileScanner);
            
            CreateFromDocument.getNextFileElement(fileScanner);
            String consumableElement = CreateFromDocument.getNextFileElement(fileScanner);
            
            loadInventory(weaponElement, armourElement, consumableElement);
        } catch (FileNotFoundException ex) {
            System.out.println("Player: File not found: " + name);
        }

    }

    public void saveToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src/gameworld/players/" + NAME + ".player", "UTF-8");
            writer.println(NAME + " *Name*");
            writer.println(SPECIES + " *Race*");
            writer.println(CLASS + " *Class*");
            writer.println(SPRITE + " *Sprite*");
            writer.println(MAX_HEALTH + " *Health*");
            writer.println(SPEED + " *Speed*");
            writer.println(ENDURANCE + " *Endurance*");
            writer.println(STRENGTH + " *Strength*");
            writer.println(INTELLIGENCE + " *Intelligence*");
            writer.println(DEXTERITY + " *Dexterity*");
            writer.println(LEVEL + " *Level");
            writer.println(XP + " *Experience Points*");
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry bub, file wasn't there... even though I'm supposed to be making it");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException.. whatever the hell that means");
        } finally {
            writer.close();
        }
    }

    private void loadStats(String statElement) {
        Scanner playerScanner = new Scanner(statElement);
        super.setName(CreateFromDocument.getLineElement(playerScanner.nextLine()));
        this.SPECIES = CreateFromDocument.getLineElement(playerScanner.nextLine());
        this.CLASS = CreateFromDocument.getLineElement(playerScanner.nextLine());
        super.SPRITE = (CreateFromDocument.getLineElement(playerScanner.nextLine()).charAt(0));
        super.setMaxHealth(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setSpeed(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setEndurance(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setStrength(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setIntelligence(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setDexterity(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setLevel(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
        super.setXP(Integer.parseInt(CreateFromDocument.getLineElement(playerScanner.nextLine())));
    }
    
    private void loadInventory(String weaponElement, String armourElement, String consumableElement) {
        Scanner weaponScanner = new Scanner(weaponElement);
        while(weaponScanner.hasNext()) {
            Weapon currentWeapon = new Weapon(CreateFromDocument.getLineElement(weaponScanner.nextLine()));
            INVENTORY.addToInventory(currentWeapon);
        }
        
        Scanner armourScanner = new Scanner(armourElement);
        while(armourScanner.hasNext()) {
            Armor currentArmour = new Armor(CreateFromDocument.getLineElement(armourScanner.nextLine()));
            INVENTORY.addToInventory(currentArmour);
        }
        
        Scanner consumableScanner = new Scanner(consumableElement);
        while(consumableScanner.hasNext()) {
            Consumable currentConsumable = new Consumable(CreateFromDocument.getLineElement(consumableScanner.nextLine()));
            INVENTORY.addToInventory(currentConsumable);
        }
    }

    public void initateStats(int speed, int endurance, int strength, int intelligence, int dexterity) {
        SPEED = speed;
        ENDURANCE = endurance;
        MAX_HEALTH = 10 + (ENDURANCE * 5);
        STRENGTH = strength;
        INTELLIGENCE = intelligence;
        DEXTERITY = dexterity;
    }

    public String getSpecies() {
        return SPECIES;
    }
    
    public String getPlayerClass() {
        return CLASS;
    }

    public void loadTest(Scanner playerScanner) {
        int tokenNumber = 0;
        while (playerScanner.hasNext()) {
            tokenNumber++;
            System.out.println(tokenNumber + " " + playerScanner.next());
        }
    }

    public String toServerData() {
        String toReturn = super.toServerData() + " ";
        return toReturn;
    }

    public String toString() {
        String output = NAME + " the " + CLASS + ":\n";
        output += SPECIES + "\n";
        output += SPRITE + "\n";
        output += "Health: " + MAX_HEALTH + "\n";
        output += "Spd: " + SPEED + "\n";
        output += "End: " + ENDURANCE + "\n";
        output += "Str: " + STRENGTH + "\n";
        output += "Int: " + INTELLIGENCE + "\n";
        output += "Dex: " + DEXTERITY + "\n";
        output += "\n" + INVENTORY;
        return output;
    }
}
