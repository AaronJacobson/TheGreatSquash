/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.monsters.Monster;
import gameworld.tools.CreatureCounter;

/**
 *
 * @author Dylan
 */
public class CounterTest {
    public static void main(String[] args) {
        CreatureCounter.clearCounter();
        for(int i = 0; i < 50; i++) {
            Monster ghosty = null;
            if(i%2 == 0) {
                ghosty = new Monster("human");
                System.out.println(ghosty.getName());
            }
            if(i%3 == 0) {
                ghosty = new Monster("kobold");
                System.out.println(ghosty.getName());
            }
            if(i%4 == 0) {
                ghosty = new Monster("liger");
                System.out.println(ghosty.getName());
            }
            if(i%5 == 0) {
                ghosty = new Monster("alot");
                System.out.println(ghosty.getName());
            }
            if(i%6 == 0) {
                ghosty = new Monster("landshark");
                System.out.println(ghosty.getName());
            }
            if(i%7 == 0) {
                ghosty = new Monster("tarrasque");
                System.out.println(ghosty.getName());
            }
        }
    }
}
