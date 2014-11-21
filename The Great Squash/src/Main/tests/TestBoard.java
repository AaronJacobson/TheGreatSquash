/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import GUI.TestMovementGUI;
import gameworld.Board;
import gameworld.CreateDungeon;
import gameworld.Creature;
import gameworld.Door;
import gameworld.Player;
import gameworld.Wall;
import java.util.Scanner;

/**
 *
 * @author ros_dmlamarca
 */
public class TestBoard {

    public static void main(String[] args) throws InterruptedException {
        TestMovementGUI gui = new TestMovementGUI();
        gui.getBoard().setBoardTilesNull();
        while (!gui.getBoard().getShouldPlayer()) {
        }
        Player playerBilly = new Player((char) (198), gui.getBoard(), 1, 1, "Guardian");
        gui.getBoard().getClient().getHandler().sendCreature(playerBilly);
        gui.setCreature(playerBilly);

        //CreateDungeon.drawRoom(board, 11, 11, 1, 1);
        //Door door = new Door(board, 6, 11);

        // 198 = Æ
        // 223 = ß
        //  64 = @
        //board.show();  

    }
}
