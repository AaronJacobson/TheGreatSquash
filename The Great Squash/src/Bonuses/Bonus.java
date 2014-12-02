/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bonuses;

import LAN.Sendable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ros_aljacobson001
 */
public class Bonus implements Sendable{
    private int A_C_BONUS = 0;
    private int M_P_BONUS = 0;
    private double HEALTH_BONUS = 0;
    private int MANA_BONUS = 0;
    private int STRENGTH_BONUS = 0;
    private int ENDURANCE_BONUS = 0;
    private int INTELLIGENCE_BONUS = 0;
    private int DEXTERITY_BONUS = 0;
    private String NAME = "UNITIALIZED_BONUS";
    
    public Bonus(int ac,int mp,double health,int mana,int manaBonus,int strengthBonus,int enduranceBonus,int intelligenceBonus,int dexterityBonus){
        A_C_BONUS = ac;
        M_P_BONUS = mp;
        HEALTH_BONUS = health;
        MANA_BONUS = mana;
        STRENGTH_BONUS = strengthBonus;
        ENDURANCE_BONUS = enduranceBonus;
        INTELLIGENCE_BONUS = intelligenceBonus;
        DEXTERITY_BONUS = dexterityBonus;
    }
    
    public Bonus(String name){
        String fileDirectory = "src/bonuses/" + name + ".bonus";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadStats(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Bonus: File not found at this location: ");
            System.out.println(fileDirectory);
        }
        System.out.println(toServerData());
    }
    
    public void loadStats(Scanner fileScanner){
        fileScanner.next();
        NAME = fileScanner.next();
        fileScanner.next();
        A_C_BONUS = fileScanner.nextInt();
        fileScanner.next();
        M_P_BONUS = fileScanner.nextInt();
        fileScanner.next();
        HEALTH_BONUS = fileScanner.nextInt();
        fileScanner.next();
        MANA_BONUS = fileScanner.nextInt();
        fileScanner.next();
        STRENGTH_BONUS = fileScanner.nextInt();
        fileScanner.next();
        ENDURANCE_BONUS = fileScanner.nextInt();
        fileScanner.next();
        INTELLIGENCE_BONUS = fileScanner.nextInt();
        fileScanner.next();
        DEXTERITY_BONUS = fileScanner.nextInt();
    }
    
    public void setACBonus(int aCBonus){
        A_C_BONUS = aCBonus;
    }
    
    public int getACBONUS(){
        return A_C_BONUS;
    }

    @Override
    public String toServerData() {
        return " | " + NAME + " " + A_C_BONUS + " " + M_P_BONUS + " " + HEALTH_BONUS + " " + MANA_BONUS + " " + STRENGTH_BONUS + " " + ENDURANCE_BONUS + " " + INTELLIGENCE_BONUS + " " + DEXTERITY_BONUS;
    }
}
