/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Sendable;
import tools.ObjectCounter;

/**
 *
 * @author ros_aljacobson001
 */
public abstract class Obstacle implements Displayable, Sendable, Cloneable {

    protected String LABEL = "Obstacle";
    protected String TYPE = "ABSTRACT_OBSTACLE";
    protected boolean PASSABLE;
    protected int LOCATION_X;
    protected int LOCATION_Y;
    protected Board BOARD;
    protected char SPRITE;
    protected double HEALTH;

    public void setBoard(Board board) {
        BOARD = board;
    }

    public String getLabel() {
        return LABEL;
    }

    public boolean getPassable() {
        return PASSABLE;
    }

    public void setPassable(boolean passable) {
        PASSABLE = passable;
    }

    public void setSprite(char sprite) {
        SPRITE = sprite;
    }

    @Override
    public char getSprite() {
        return SPRITE;
    }

    public int getX() {
        return LOCATION_X;
    }

    public int getY() {
        return LOCATION_Y;
    }

    public void setX(int x) {
        LOCATION_X = x;
    }

    public void setY(int y) {
        LOCATION_Y = y;
    }

    public void setLocation(Board board, int y, int x) {
        board.setTileObstacle(y, x, this);
        LOCATION_X = x;
        LOCATION_Y = y;
    }

    public String toServerData() {
        return " | " + LABEL + " " + LOCATION_Y + " " + LOCATION_X + " " + PASSABLE + " " + HEALTH + " " + SPRITE + " " + TYPE;
    }
}
