/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.listeners;

import Main.GameRunner;
import gameworld.Creature;
import gameworld.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author ros_dmlamarca
 */
public class MovementListener extends KeyAdapter {

    private Creature CREATURE;
    private int LAST_KEY_CODE = 0;

    public MovementListener() {
    }

    public void setCreature(Creature creature) {
        CREATURE = creature;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            //move left
            CREATURE.moveSelf(CREATURE.getY() - 1, CREATURE.getX());
        } else if (keyCode == KeyEvent.VK_S) {
            //move down
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() + 1);
        } else if (keyCode == KeyEvent.VK_D) {
            //move right
            CREATURE.moveSelf(CREATURE.getY() + 1, CREATURE.getX());
        } else if (keyCode == KeyEvent.VK_W) {
            //move up
            CREATURE.moveSelf(CREATURE.getY(), CREATURE.getX() - 1);
        } else if (keyCode == KeyEvent.VK_SPACE) {
//            System.out.println("MovementListener: Space has been pressed.");
        } else if (keyCode == KeyEvent.VK_UP) {
            if (LAST_KEY_CODE == KeyEvent.VK_SPACE) {
                System.out.println("MovementListener: Interacting with the tile above");
                CREATURE.interactWith(GameRunner.GAME_BOARD.getTile(CREATURE.getY(), CREATURE.getX() - 1));
            } else if (LAST_KEY_CODE == KeyEvent.VK_1) {
                System.out.println("MovementListener: Attacking the creature above");
                CREATURE.attackCreature(GameRunner.GAME_BOARD.getTile(CREATURE.getY(), CREATURE.getX() - 1));
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (LAST_KEY_CODE == KeyEvent.VK_SPACE) {
                System.out.println("MovementListener: Interacting with the tile to the right");
                CREATURE.interactWith(GameRunner.GAME_BOARD.getTile(CREATURE.getY() + 1, CREATURE.getX()));
            } else if (LAST_KEY_CODE == KeyEvent.VK_1) {
                System.out.println("MovementListener: Attacking the creature to the right");
                CREATURE.attackCreature(GameRunner.GAME_BOARD.getTile(CREATURE.getY() + 1, CREATURE.getX()));
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (LAST_KEY_CODE == KeyEvent.VK_SPACE) {
                System.out.println("MovementListener: Interacting with the tile to the left");
                CREATURE.interactWith(GameRunner.GAME_BOARD.getTile(CREATURE.getY() - 1, CREATURE.getX()));
            } else if (LAST_KEY_CODE == KeyEvent.VK_1) {
                System.out.println("MovementListener: Attacking the creature to the left");
                CREATURE.attackCreature(GameRunner.GAME_BOARD.getTile(CREATURE.getY() - 1, CREATURE.getX()));
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            System.out.println(keyCode + " " + KeyEvent.VK_1);
            if (LAST_KEY_CODE == KeyEvent.VK_SPACE) {
                System.out.println("MovementListener: Interacting with the tile below");
                CREATURE.interactWith(GameRunner.GAME_BOARD.getTile(CREATURE.getY(), CREATURE.getX() + 1));
            } else if (LAST_KEY_CODE == KeyEvent.VK_1) {
                System.out.println("MovementListener: Attacking the creature below");
                CREATURE.attackCreature(GameRunner.GAME_BOARD.getTile(CREATURE.getY(), CREATURE.getX() + 1));
            }
        } else if (keyCode == KeyEvent.VK_ENTER) {
            if (CREATURE instanceof Player) {
                GameRunner.CLIENT.getHandler().sendDoneTurn(CREATURE.getName());
            } else {
            }
        } else if (keyCode == KeyEvent.VK_TAB) {
            System.out.println("MovementListener: Switching controlled creature.");
            if (GameRunner.GAME_GUI.CURRENT_CREATURE == GameRunner.GAME_GUI.getCreatures().size() - 1) {
                GameRunner.GAME_GUI.setCreature(0);
            } else {
                GameRunner.GAME_GUI.setCreature(GameRunner.GAME_GUI.CURRENT_CREATURE + 1);
            }
            System.out.println("MovementListener: The creature is " + GameRunner.GAME_GUI.getCreatures().get(GameRunner.GAME_GUI.CURRENT_CREATURE).toServerData());
        } else if (keyCode == KeyEvent.VK_0) {
            System.out.println("MovementListener: Setting the GM to done.");
            GameRunner.SERVER.setGMDone(true);
        }
        LAST_KEY_CODE = keyCode;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
