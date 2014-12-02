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
    private int HEALTH_BONUS = 0;
    private int MANA_BONUS = 0;
    private int STRENGTH_BONUS = 0;
    private int ENDURANCE_BONUS = 0;
    private int INTELLIGENCE_BONUS = 0;
    private int DEXTERITY_BONUS = 0;
    private boolean ACTIVE = true;
    private String NAME = "UNITIALIZED_BONUS";
    
    public Bonus(String name,int ac,int mp,int health,int manaBonus,int strengthBonus,int enduranceBonus,int intelligenceBonus,int dexterityBonus){
        NAME = name;
        A_C_BONUS = ac;
        M_P_BONUS = mp;
        HEALTH_BONUS = health;
        MANA_BONUS = manaBonus;
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
    
    public String getName(){
        return NAME;
    }
    
    public void setName(String newName){
        NAME = newName;
    }
    
    public void setACBonus(int newACBonus){
        A_C_BONUS = newACBonus;
    }
    
    public int getACBonus(){
        return A_C_BONUS;
    }
    
    public int getMPBonus(){
        return M_P_BONUS;
    }
    
    public void setMPBonus(int newMPBonus){
        M_P_BONUS = newMPBonus;
    }
    
    public int getHealthBonus(){
        return HEALTH_BONUS;
    }
    
    public void setHealthBonus(int newHealthBonus){
        HEALTH_BONUS = newHealthBonus;
    }
    
    public int getManaBonus(){
        return MANA_BONUS;
    }
    
    public void setManaBonus(int newManaBonus){
        MANA_BONUS = newManaBonus;
    }
    
    public int getStrengthBonus(){
        return STRENGTH_BONUS;
    }
    
    public void setStrengthBonus(int newStrengthBonus){
        STRENGTH_BONUS = newStrengthBonus;
    }
    
    public int getEnduranceBonus(){
        return ENDURANCE_BONUS;
    }
    
    public void setEnduranceBonus(int newEnduranceBonus){
        ENDURANCE_BONUS = newEnduranceBonus;
    }
    
    public int getIntelligenceBonus(){
        return INTELLIGENCE_BONUS;
    }
    
    public void setIntelligenceBonus(int newIntelligenceBonus){
        INTELLIGENCE_BONUS = newIntelligenceBonus;
    }
    
    public int getDexterityBonus(){
        return DEXTERITY_BONUS;
    }
    
    public void setDexterityBonus(int newDexterityBonus){
        DEXTERITY_BONUS = newDexterityBonus;
    }

    @Override
    public String toServerData() {
        return " | " + NAME + " " + A_C_BONUS + " " + M_P_BONUS + " " + HEALTH_BONUS + " " + MANA_BONUS + " " + STRENGTH_BONUS + " " + ENDURANCE_BONUS + " " + INTELLIGENCE_BONUS + " " + DEXTERITY_BONUS;
    }
}
