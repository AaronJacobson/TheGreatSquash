/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Sendable;

/**
 *
 * @author ros_aljacobson001
 */
public abstract class Obstacle implements Displayable, Sendable, Cloneable {
    String LABEL;
    boolean PASSABLE;
    int LOCATION_X;
    int LOCATION_Y;
    Board BOARD;
    char SPRITE;
    double HEALTH;
    String TYPE;
    
     public Obstacle(char sprite, String label, boolean passable, Board board, int y, int x,String type) {
        LABEL = label;
        TYPE = type;
        PASSABLE = passable;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;

        BOARD.getTile(y, x).setObstacle(this);
    }
     
     public Obstacle(char sprite, String label, boolean passable) {
        //BOARD.getClient().getHandler()
        LABEL = label;
        PASSABLE = passable;
        SPRITE = sprite;
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
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
        //BOARD.getTile(y, x).setObstacle(this);
    }
    
    public String getServerData(){
        return " | " + LABEL + " " + LOCATION_Y + " " + LOCATION_X + " " + PASSABLE + " " + HEALTH + " " + SPRITE;
    }
}
