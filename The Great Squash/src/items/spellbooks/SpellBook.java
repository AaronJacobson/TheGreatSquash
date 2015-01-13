/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.spellbooks;

import tools.NetworkInfo;
import LAN.Sendable;
import LAN.Server;
import gameworld.Displayable;
import items.Item;

/**
 *
 * @author ros_dmlamarca
 */
public abstract class SpellBook implements Item, Displayable, Sendable {

    protected char SPRITE = (char) (187);// 187 = »
    protected String NAME;
    protected String SCHOOL;
    protected int MANA_COST;

    public void setSprite(char sprite) {
        SPRITE = sprite;
    }

    public char getSprite() {
        return SPRITE;
    }

    public String toServerData() {
        String serverData = Server.SPELL_BOOK + " " + NAME + " " + SPRITE + " " + SCHOOL + " " + MANA_COST;
        return serverData;
    }
}
