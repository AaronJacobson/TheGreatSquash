/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

/**
 *
 * @author ros_aljacobson001
 */
public class Tile {

    private char EMPTY_SPACE_SPRITE = '.';
    private Obstacle OBSTACLE;
    private Creature CREATURE;

    public Tile(Obstacle obstacle, Creature creature) {
        OBSTACLE = obstacle;
        CREATURE = creature;
    }

    public void setCreature(Creature creature) {
        CREATURE = creature;
    }

    public Creature getCreature() {
        return CREATURE;
    }

    public void setObstacle(Obstacle obstacle) {
        OBSTACLE = obstacle;
    }

    public Obstacle getObstacle() {
        return OBSTACLE;
    }
    
    public boolean getPassable() {
        boolean creaturePassable = true;
        boolean obstaclePassable = true;
        
        try {
            if(!CREATURE.getPassable()) {
                creaturePassable = false;
            }
        } catch(NullPointerException ex) {}
        
        try {
            if(!OBSTACLE.getPassable()) {
                creaturePassable = false;
            }
        } catch(NullPointerException ex) {}
        
        return creaturePassable && obstaclePassable;
    }

    @Override
    public String toString() {
        String output = "";
        if (CREATURE != null) {
            output += CREATURE.getSprite();
        } else if (OBSTACLE != null) {
            output += OBSTACLE.getSprite();
        } else {
            output += EMPTY_SPACE_SPRITE;
        }
        return output;
    }

    public String toServerData() {
        String toReturn = "";
        if (CREATURE != null) {
            toReturn += CREATURE.getSprite();
        } else {
            toReturn += ".";
        }
        return toReturn;
    }
}
