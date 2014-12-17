/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.obstacles.Chest;
import gameworld.obstacles.Door;
import gameworld.obstacles.Wall;
import gameworld.monsters.Monster;
import tools.ObjectCounter;

/**
 *
 * @author Dylan
 */
public class CounterTest {

    public static void main(String[] args) {
        ObjectCounter.clearCounters();
        for (int i = 0; i < 50; i++) {
            if(i%2 == 0) {
                Wall wally = new Wall();
                System.out.println(wally.getLabel());
            }
            if(i%3 == 0) {
                Door wally = new Door(true);
                System.out.println(wally.getLabel());
            }
            if(i%4 == 0) {
                Chest wally = new Chest();
                System.out.println(wally.getLabel());
            }
        }
    }
}
