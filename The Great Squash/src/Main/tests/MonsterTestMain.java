/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.monsters.Monster;

/**
 *
 * @author ros_Dmlamarca
 */
public class MonsterTestMain {

    public static void main(String[] args) {
        Monster lolita = new Monster("human");


        System.out.println("Level: " + lolita.getLevel());
        System.out.println("Hlth: " + lolita.getCurrentHealth() + "/" + lolita.getMaxHealth());
        System.out.println("Spd: " + lolita.getSpeed());
        System.out.println("End: " + lolita.getEndurance());
        System.out.println("Str: " + lolita.getStrength());
        System.out.println("Int: " + lolita.getIntelligence());
        System.out.println("Dex: " + lolita.getDexterity());
        System.out.println();
    }
}
