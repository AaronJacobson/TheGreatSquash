/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import GUI.GameGUI;
import gameworld.tools.CreatureCounter;
import gameworld.Inventory;
import gameworld.monsters.Monster;

/**
 *
 * @author ros_Dmlamarca
 */
public class GUITestMain {
    public static void main(String[] args) {
        //for(int i = 0; i < 101; i++) {
        Monster fluffy = new Monster("liger");
        CreatureCounter.getCount(fluffy.getType());
        //}
//        GameGUI gui = new GameGUI();
//        
//        Monster fluffy = new Monster("liger");
//        
//        gui.setCreature(fluffy);
//        gui.updateInventoryDisplay();
//        Inventory inventory = new Inventory(27, 3, 4, 5);
//        System.out.println(inventory);
    }
}
