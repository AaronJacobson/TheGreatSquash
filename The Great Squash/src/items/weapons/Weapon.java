/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.weapons;

import bonuses.Bonus;
import LAN.Sendable;
import gameworld.Displayable;
import items.Item;
import java.util.ArrayList;

/**
 *
 * @author ros_dmlamarca
 */
public class Weapon implements Item, Displayable, Sendable {
     private char SPRITE = (char)(167);// 167 = §
     private int DEFENSE;
     private int ATTACK;
     private int RANGE;
     private String NAME;
     private ArrayList<Bonus> BONUSES;
    
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
