/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.listeners;

import GUI.GameGUI;
import Main.tests.GUITestMain;
import gameworld.Creature;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ros_dmlamarca
 */
public class MovementListener implements KeyListener {
    private Creature CREATURE;
    
    public MovementListener() {
    }
    
    public void setCreature(Creature creature) {
        CREATURE = creature;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println(ke.getExtendedKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getExtendedKeyCode();
        if(keyCode == 37) {
            //move left
            System.out.println("left| " + CREATURE.getY() + "," + CREATURE.getX());
            CREATURE.moveSelf(CREATURE.getY(),CREATURE.getX() - 1);
//            GUITestMain.updateGUI();
        } else if(keyCode == 38) {
            //move up
            System.out.println("up| "+ CREATURE.getY() + "," + CREATURE.getX());
            CREATURE.moveSelf(CREATURE.getY() - 1,CREATURE.getX());
//            GUITestMain.updateGUI();
        } else if(keyCode == 39) {
            //move right
            System.out.println("right| " + CREATURE.getY() + "," + CREATURE.getX());
            CREATURE.moveSelf(CREATURE.getY(),CREATURE.getX() + 1);
//            GUITestMain.updateGUI();
        } else if(keyCode == 40) {
            //move down
            System.out.println("down |" + CREATURE.getY() + "," + CREATURE.getX());
            CREATURE.moveSelf(CREATURE.getY() + 1,CREATURE.getX());
//            GUITestMain.updateGUI();
        }  
        
        if(keyCode == 32) {
            CREATURE.interactWithSurroundings();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
