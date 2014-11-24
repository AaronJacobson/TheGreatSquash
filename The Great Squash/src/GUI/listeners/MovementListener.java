/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.listeners;

import GUI.tests.TestMovementGUI;
import LAN.ServerDataHandler;
import gameworld.Creature;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.activation.DataHandler;

/**
 *
 * @author ros_dmlamarca
 */
public class MovementListener implements KeyListener {
    private ServerDataHandler DATA_HANDLER;
    private Creature CREATURE;
    private TestMovementGUI GUI;
    
    public MovementListener(TestMovementGUI gui) {
        GUI = gui;
        DATA_HANDLER = GUI.getBoard().getClient().getHandler();
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
            CREATURE.moveSelf(CREATURE.getY() - 1, CREATURE.getX());
        } else if(keyCode == 38) {
            //move up
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() - 1);
        } else if(keyCode == 39) {
            //move right
            CREATURE.moveSelf(CREATURE.getY() + 1, CREATURE.getX());
        } else if(keyCode == 40) {
            //move down
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() + 1);
        }  
        
        if(keyCode == 32) {
            CREATURE.interactWithSurroundings();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
