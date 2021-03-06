/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld.obstacles;

import tools.TypeHolder;
import gameworld.Board;
import gameworld.Creature;
import gameworld.Interactive;
import gameworld.Obstacle;
import tools.ObjectCounter;
import tools.SpriteHolder;

/**
 *
 * @author ros_dmlamarca
 */
public class Door extends Obstacle implements Interactive, Cloneable {

    private boolean DOOR_POSITION;

    public Door(Board board, boolean open, int y, int x) {
        this(open);
        BOARD = board;
        LOCATION_Y = y;
        LOCATION_X = x;
        PASSABLE = true;
    }

    public Door(boolean open) {
        DOOR_POSITION = open;
        if (open) {
            SPRITE = SpriteHolder.OB_OPEN_DOOR;
        } else {
            SPRITE = SpriteHolder.OB_CLOSED_DOOR;
        }
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_DOOR);
        TYPE = TypeHolder.OB_DOOR;
        PASSABLE = true;
    }

    public void interact(Creature creature) {
        if (DOOR_POSITION) {
            //Close door
            SPRITE = SpriteHolder.OB_CLOSED_DOOR;
            System.out.println("Door: I have been closed!");
            PASSABLE = false;
        } else {
            //Open door
            SPRITE = SpriteHolder.OB_OPEN_DOOR;
            System.out.println("Door: I have been opened!");
            PASSABLE = true;
        }
    }

    public Door clone(Board board) {
        BOARD = board;
        Door clone = new Door(DOOR_POSITION);
        clone.setLocation(BOARD, LOCATION_Y, LOCATION_X);
        return clone;
    }

    @Override
    public String toServerData() {
        return super.toServerData();
    }
}
