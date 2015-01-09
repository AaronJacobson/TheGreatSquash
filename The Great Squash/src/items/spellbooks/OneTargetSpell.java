/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items.spellbooks;

import java.util.Scanner;

/**
 *
 * @author ros_aljacobson001
 */
public class OneTargetSpell extends SpellBook {

    private int RANGE;

    public OneTargetSpell(String name, char sprite, String school, int manaCost, int range) {
        NAME = name;
        SPRITE = sprite;
        SCHOOL = school;
        MANA_COST = manaCost;
        RANGE = range;
    }

    public String toServerData() {
        return super.toServerData() + " " + RANGE;
    }

    @Override
    public void loadFromFile(String fileElement) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
