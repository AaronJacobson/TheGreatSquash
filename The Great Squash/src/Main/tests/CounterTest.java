/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.Door;
import gameworld.Wall;
import gameworld.monsters.Monster;
import gameworld.tools.ObjectCounter;

/**
 *
 * @author Dylan
 */
public class CounterTest {

    public static void main(String[] args) {
        ObjectCounter.clearCounters();
        for (int i = 0; i < 10; i++) {
            if(i%2 == 0) {
                Wall wally = new Wall();
                ObjectCounter.getObstacleCount("Wall");
            }
            if(i%2 == 0) {
                Door wally = new Door(true);
                ObjectCounter.getObstacleCount("Door");
            }
        }
    }
}
