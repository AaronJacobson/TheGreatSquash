/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Sendable;
import Main.GameRunner;
import gameworld.tools.ObjectCounter;

/**
 *
 * @author ros_aljacobson001
 */
public abstract class Obstacle implements Displayable, Sendable, Cloneable {
    protected String LABEL = "Obstacle";
    protected String TYPE = "ABSTRACT";
    protected boolean PASSABLE;
    protected int LOCATION_X;
    protected int LOCATION_Y;
    protected Board BOARD;
    protected char SPRITE;
    protected double HEALTH;
    
     public Obstacle(char sprite, String label, boolean passable, Board board, int y, int x,String type) {
        LABEL = ObjectCounter.getObstacleCount(label);
        TYPE = type;
        PASSABLE = passable;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
        setLocation(BOARD,LOCATION_X,LOCATION_Y);
    }
     
     public Obstacle(char sprite, String label, boolean passable,String type) {
        LABEL = ObjectCounter.getObstacleCount(label);
        TYPE = type;
        PASSABLE = passable;
        SPRITE = sprite;
    }
     
     public void setBoard(Board board){
         BOARD = board;
     }
     
     public String getLabel() {
         return LABEL;
     }
     
     /**
     *
     * @return
     */
    public boolean getPassable() {
         return PASSABLE;
     }
    
    public void setPassable(boolean passable) {
        PASSABLE = passable;
    }
    
    @Override
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
    
    public void setLocation(Board board, int y, int x) {
        board.setTileObstacle(y, x, this);
        LOCATION_X = x;
        LOCATION_Y = y;
    }
    
    public String toServerData(){
        return " | " + LABEL + " " + LOCATION_Y + " " + LOCATION_X + " " + PASSABLE + " " + HEALTH + " " + SPRITE + " " + TYPE;
    }
}
