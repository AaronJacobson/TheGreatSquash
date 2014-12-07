/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

import gameworld.Board;
import gameworld.tools.CreateFromDocument;
import gameworld.Wall;

/**
 *
 * @author ros_dmlamarca
 */
public class DocumentToBoardTestMain {

    public static void main(String[] args) {
        Board board = CreateFromDocument.createBoard("H:\\testboard.txt");
        board.show();
    }
}
