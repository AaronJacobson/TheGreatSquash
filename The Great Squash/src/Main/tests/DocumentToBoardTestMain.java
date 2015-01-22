/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import GUI.GameGUI;
import gameworld.Board;
import gameworld.Creature;
import tools.CreateFromDocument;
import gameworld.obstacles.Wall;
import java.util.ArrayList;
import tools.ObjectCounter;

/**
 *
 * @author ros_dmlamarca
 */
public class DocumentToBoardTestMain {

    public static void main(String[] args) {
        Board board = CreateFromDocument.createFromMaps("map01");
        board.show();
        
        ArrayList<Creature> creatures = board.getCreatures();
        
        for(int i = 0; i < creatures.size(); i++) {
            Creature current = creatures.get(i);
            System.out.println(current.getName());
            System.out.println(current.getCurrentHealth() + "/" + current.getMaxHealth() + "\n");
        }
        ObjectCounter.clearCounters();
        
        GameGUI gui = new GameGUI();
        gui.setCreature(creatures.get(0));
    }
}
