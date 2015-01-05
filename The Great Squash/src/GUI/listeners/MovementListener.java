/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.listeners;

import gameworld.Creature;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author ros_dmlamarca
 */
public class MovementListener extends KeyAdapter{

    private Creature CREATURE;
    private int LAST_KEY_CODE = 0;

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
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_A){
            //move left
            CREATURE.moveSelf(CREATURE.getY() - 1, CREATURE.getX());
        }else if(keyCode == KeyEvent.VK_S){
            //move down
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() + 1);
        }else if(keyCode == KeyEvent.VK_D){
            //move right
            CREATURE.moveSelf(CREATURE.getY() + 1, CREATURE.getX());
        }else if(keyCode == KeyEvent.VK_W){
            //move up
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() - 1);
        }else if(keyCode == KeyEvent.VK_SPACE){
            
        }else if(keyCode == KeyEvent.VK_UP){
            if(LAST_KEY_CODE == KeyEvent.VK_SPACE){
                
            }
        }else if(keyCode == KeyEvent.VK_RIGHT){
            if(LAST_KEY_CODE == KeyEvent.VK_SPACE){
                
            }
        }else if(keyCode == KeyEvent.VK_LEFT){
            if(LAST_KEY_CODE == KeyEvent.VK_SPACE){
                
            }
        }else if(keyCode == KeyEvent.VK_DOWN){
            if(LAST_KEY_CODE == KeyEvent.VK_SPACE){
                
            }
        }
        LAST_KEY_CODE = keyCode;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
