/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.TypeHolder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ros_dmlamarca
 */
public class Player extends Creature {
    
    private String SPECIES;
    public Player(char sprite, Board board, int y, int x,String name) {
         super(sprite,board,y,x,name,TypeHolder.PLAYER);
    }
    
    public Player(File theFile){
        super();
        try {
            Scanner fileScanner = new Scanner(theFile);
            loadFromFile(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Player: The file that was given isn't working.");
        }
        
    }
    
    public Player(String name){
        super(name);
        String fileDirectory = "src/gameworld/players/" + name + ".player";
        try {
            Scanner fileScanner = new Scanner(new File(fileDirectory));
            loadFromFile(fileScanner);
        } catch (FileNotFoundException ex) {
            System.out.println("Player: File not found: " + fileDirectory);
        }
    }
    
    public void loadFromFile(Scanner playerScanner){
        boolean statsLoaded = false;
        boolean equipmentLoaded = false;
        boolean inventoryLoaded = false;
        boolean spellsLoaded = false;
        loadStats(playerScanner);
//        while(playerScanner.hasNext()){
//            if(!statsLoaded){
//                loadStats(playerScanner);
//                statsLoaded = true;
//            }else if(!equipmentLoaded){
//                
//            }else if(!inventoryLoaded){
//                
//            }else if(!spellsLoaded){
//                //we need to implement spells
//            }
//        }
    }
    
    public void loadStats(Scanner playerScanner){
        super.setName(getLineElement(playerScanner.nextLine()));
        super.setSprite(getLineElement(playerScanner.nextLine()).charAt(0));
        this.SPECIES = getLineElement(playerScanner.nextLine());
        super.setMaxHealth(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setLevel(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setXP(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setSpeed(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setEndurance(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setStrength(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setIntelligence(Integer.parseInt(getLineElement(playerScanner.nextLine())));
        super.setDexterity(Integer.parseInt(getLineElement(playerScanner.nextLine())));
    }
    
    private String getLineElement(String line) {
        String output = "";
        Scanner lineScanner = new Scanner(line);
        boolean first = true;
        while(lineScanner.hasNext()) {
            String current = lineScanner.next();
            //System.out.println(current);
            if(!current.contains("*") && first) {
                output += current;
                first = false;
            } else if(!current.contains("*")) {
                output += " " + current;
            }
        }
        //System.out.println(output);
        return output;
    }
    
    public String getSpecies() {
        return SPECIES;
    }
    
    public void loadTest(Scanner playerScanner){
        int tokenNumber = 0;
        while(playerScanner.hasNext()){
            tokenNumber++;
            System.out.println(tokenNumber + " " + playerScanner.next());
        }
    }
    
    public String toServerData(){
        String toReturn = super.toServerData() + " ";
        return toReturn;
    }
}
