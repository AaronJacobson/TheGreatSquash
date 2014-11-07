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
        System.out.println(lolita.getType());
        System.out.println(lolita.getSprite());
        System.out.println("Spd: " + lolita.getSpeedMod());
        System.out.println("End: " + lolita.getEnduranceMod());
        System.out.println("Str: " + lolita.getStrengthMod());
        System.out.println("Int: " + lolita.getIntelligenceMod());
        System.out.println("Dex: " + lolita.getDexterityMod());

        for (int i = 0; i < 10; i++) {
            lolita.changeLevel(1);
            System.out.println("Level: " + lolita.getLevel());
            System.out.println("Spd: " + lolita.getSpeed());
            System.out.println("End: " + lolita.getEndurance());
            System.out.println("Str: " + lolita.getStrength());
            System.out.println("Int: " + lolita.getIntelligence());
            System.out.println("Dex: " + lolita.getDexterity());
            System.out.println();
        }
    }
}
