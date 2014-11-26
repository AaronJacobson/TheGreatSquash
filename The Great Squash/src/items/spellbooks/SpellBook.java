/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.spellbooks;

import gameworld.Displayable;
import items.Item;

/**
 *
 * @author ros_dmlamarca
 */
public abstract class SpellBook implements Item, Displayable {
    private char SPRITE = (char)(187);
    // 187 = »
    
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
}
