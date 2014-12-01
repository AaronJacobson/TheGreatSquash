/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.TypeHolder;

/**
 *
 * @author ros_dmlamarca
 */
public class Door extends Obstacle implements Interactive, Cloneable {
    private boolean DOOR_POSITION;
    private char CLOSE_SPRITE = '+';
    private char OPEN_SPRITE = '_';
    
    public Door(Board board, boolean open, int y, int x) {
        super(TypeHolder.ERROR_CHAR,"Door",open, board, y, x,TypeHolder.OB_DOOR);
        if(open) {
            super.setSprite(OPEN_SPRITE);
        } else {
            super.setSprite(CLOSE_SPRITE);
        }
        DOOR_POSITION = open;
    }
    
    public Door(boolean open) {
        super(TypeHolder.ERROR_CHAR,"Door",open);
        if(open) {
            super.setSprite(OPEN_SPRITE);
        } else {
            super.setSprite(CLOSE_SPRITE);
        }
        DOOR_POSITION = open;
    }
    
    public void update() {
        if(DOOR_POSITION) {
            super.setSprite(CLOSE_SPRITE);
            super.setPassable(false);
        } else {
            super.setSprite(OPEN_SPRITE);
            super.setPassable(true);
        }
    }
    
     public Door clone() {
        Door clone = new Door(DOOR_POSITION);
        clone.setLocation(BOARD, LOCATION_Y, LOCATION_X);
        return clone;
    }

    @Override
    public String toServerData() {
        return super.toServerData();
    }
}
