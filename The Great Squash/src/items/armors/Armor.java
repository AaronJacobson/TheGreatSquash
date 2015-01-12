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
import tools.CreateFromDocument;

/**
 *
 * @author ros_dmlamarca
 */
public class Armor implements Item, Displayable, Sendable {

    private String NAME = "UNITIALIZED_ARMOR";
    private String FILE_NAME;
    private char SPRITE = (char) (177); // 177 = ±
    private int ARMOR_CLASS = 10;
    private ArrayList<Enchantment> ENCHANTMENTS;
    private ArrayList<Aura> AURAS;

    public Armor(String name, char sprite, int ac) {
        ENCHANTMENTS = new ArrayList<Enchantment>();
        AURAS = new ArrayList<Aura>();
        NAME = name;
        FILE_NAME = name;
        SPRITE = sprite;
        ARMOR_CLASS = ac;
    }

    public Armor(String name) {
        ENCHANTMENTS = new ArrayList<Enchantment>();
        AURAS = new ArrayList<Aura>();
        FILE_NAME = name;
        String fileDirectory = "src/items/armors/" + name + ".armor";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadFromFile(CreateFromDocument.getNextFileElement(fileScanner));
        } catch (FileNotFoundException ex) {
            System.out.println("Armor: Unable to load the file with the directory of: ");
            System.out.println(fileDirectory);
        }
    }

    public void loadFromFile(String fileElement) {
        Scanner elementScanner = new Scanner(fileElement);
        NAME = CreateFromDocument.getLineElement(elementScanner.nextLine());
        SPRITE = CreateFromDocument.getLineElement(elementScanner.nextLine()).charAt(0);
        ARMOR_CLASS = Integer.parseInt(CreateFromDocument.getLineElement(elementScanner.nextLine()));
    }

    public String getName() {
        return NAME;
    }

    public char getSprite() {
        return SPRITE;
    }

    public void setAC(int toSet) {
        ARMOR_CLASS = toSet;
    }

    public int getAC() {
        return ARMOR_CLASS;
    }

    public String toString() {
        return NAME;
    }

    public String toServerData() {
//        return CommandHolder.ARMOR + NAME + " " + SPRITE + " " + ARMOR_CLASS + " " + DURABILITY + " " + enchantmentServerData();
        return "([{}])";
    }
}
