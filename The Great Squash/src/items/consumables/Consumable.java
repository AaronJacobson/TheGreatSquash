/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.consumables;

import gameworld.Displayable;
import items.Item;

/**
 *
 * @author ros_dmlamarca
 */
public abstract class Consumable implements Item, Displayable {
    private char SPRITE = (char)(164);
    // 164 = ¤
    
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
}
