/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import GUI.*;
import gameworld.Board;
import gameworld.Creature;
import gameworld.DocumentToBoard;
import gameworld.tools.CreatureCounter;
import gameworld.Inventory;
import gameworld.monsters.Monster;

/**
 *
 * @author ros_Dmlamarca
 */
public class GUITestMain {
    public static void main(String[] args) {
        CreatureCounter.clearCounter();
        GameGUI gui = new GameGUI();
        
        Board board = DocumentToBoard.getFromMaps("map01.map");
        board.show();
        
        gui.updateBoard(board);
        
        Creature jerry = board.getCreature("Human_002");
        gui.setCreature(jerry);
        gui.updateInventoryDisplay();
        Inventory inventory = new Inventory(27, 3, 4, 5);
        //System.out.println(inventory);
        
        
        
//        StartMenu gui = new StartMenu();
    }
}
