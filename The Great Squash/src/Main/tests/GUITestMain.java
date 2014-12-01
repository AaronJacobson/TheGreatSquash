/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import GUI.*;
import gameworld.Board;
import gameworld.Creature;
import gameworld.tools.DocumentToBoard;
import gameworld.tools.ObjectCounter;
import gameworld.Inventory;
import gameworld.monsters.Monster;

/**
 *
 * @author ros_Dmlamarca
 */
public class GUITestMain {
    private static GameGUI GUI; 
    private static Board BOARD = DocumentToBoard.getFromMaps("map01.map");
    
    public static void main(String[] args) {
        ObjectCounter.clearCounters(); 
        
        BOARD.show();
        
        Creature jerry = BOARD.getCreature("Alot_000");
        
        GUI = new GameGUI(jerry);
        GUI.updateBoard(BOARD);
    }
    
    public static void updateGUI() {
        GUI.updateBoard(BOARD);
    }
}
