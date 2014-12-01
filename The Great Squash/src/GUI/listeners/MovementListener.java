/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.listeners;

import GUI.GameGUI;
import GUI.tests.TestMovementGUI;
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
            CREATURE.moveSelf(4,4);
            GUITestMain.updateGUI();
        } else if(keyCode == 38) {
            //move up
            CREATURE.moveSelf(4,4);
            GUITestMain.updateGUI();
        } else if(keyCode == 39) {
            //move right
            CREATURE.moveSelf(4,4);
            GUITestMain.updateGUI();
        } else if(keyCode == 40) {
            //move down
            CREATURE.moveSelf(4,4);
            GUITestMain.updateGUI();
        }  
        
        if(keyCode == 32) {
            CREATURE.interactWithSurroundings();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
