package gameworld.obstacles;

import tools.TypeHolder;
import gameworld.Board;
import gameworld.Creature;
import gameworld.HasInventory;
import gameworld.Interactive;
import gameworld.Inventory;
import gameworld.Obstacle;
import items.*;
import tools.ObjectCounter;
import tools.SpriteHolder;

/**
 *
 * @author Dylan
 */
public class Chest extends Obstacle implements Interactive, HasInventory {

    private Inventory INVENTORY;

    public Chest(Board board, int y, int x) {
        this();
        BOARD = board;
        LOCATION_Y = y;
        LOCATION_X = x;
        PASSABLE = false;
    }

    public Chest() {
        INVENTORY = new Inventory(10);
        SPRITE = SpriteHolder.OB_CHEST;
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_CHEST);
        TYPE = TypeHolder.OB_CHEST;
        PASSABLE = false;
    }

    public void interact(Creature creature) {
    }

    public Inventory getInventory() {
        return INVENTORY;
    }

    @Override
    public String toServerData() {
        return super.toServerData();
    }
}
