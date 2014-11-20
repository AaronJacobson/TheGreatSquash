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
    
    public Player(char sprite, Board board, int y, int x,String name) {
         super(sprite,board,y,x,name,TypeHolder.PLAYER);
    }
    
    public Player(String name){
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
        while(playerScanner.hasNext()){
            if(!statsLoaded){
                loadStats(playerScanner);
                statsLoaded = true;
            }else if(!equipmentLoaded){
                
            }else if(!inventoryLoaded){
                
            }else if(!spellsLoaded){
                //we need to implement spells
            }
        }
    }
    
    public void loadStats(Scanner playerScanner){
        playerScanner.next();
        String name = playerScanner.next();
        playerScanner.next();
        char symbol = playerScanner.next().charAt(0);
        System.out.println(playerScanner.next());
        System.out.println(playerScanner.next());
        
    }
    
    public void loadTest(Scanner playerScanner){
        int tokenNumber = 0;
        while(playerScanner.hasNext()){
            tokenNumber++;
            System.out.println(tokenNumber + " " + playerScanner.next());
        }
    }
}
