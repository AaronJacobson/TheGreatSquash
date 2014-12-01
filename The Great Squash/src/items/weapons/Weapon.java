/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.weapons;

import LAN.Sendable;
import gameworld.Displayable;
import items.Item;

/**
 *
 * @author ros_dmlamarca
 */
public abstract class Weapon implements Item, Displayable, Sendable {
     private char SPRITE = (char)(167);
     // 167 = §
     private int DEFENSE;
     private int ATTACK;
     private int SPEED;
    
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
    
    public String toServerData(){
        String serverData = "";
        return serverData;
    }
}
