/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld.obstacles;

import tools.TypeHolder;
import Main.GameRunner;
import gameworld.Board;
import gameworld.Obstacle;
import tools.ObjectCounter;
import tools.SpriteHolder;

/**
 *
 * @author ros_dmlamarca
 */
public class Wall extends Obstacle implements Cloneable {

    public Wall(Board board, int y, int x) {
        this();
        BOARD = board;
        LOCATION_Y = y;
        LOCATION_X = x;
        PASSABLE = false;
    }

    public Wall() {
        SPRITE = SpriteHolder.OB_WALL;
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_WALL);
        TYPE = TypeHolder.OB_WALL;
        PASSABLE = false;
    }

    public Wall clone(Board board) {
        BOARD = board;
        Wall clone = new Wall();
        return clone;
    }

    @Override
    public String toServerData() {
        return super.toServerData();
    }
}
