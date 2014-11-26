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
public class Wall extends Obstacle implements Cloneable {

    public Wall(Board board, int y, int x) {
        super('#', "", false, board, y, x, TypeHolder.OB_WALL);
    }
    public Wall(char sprite, String label, boolean passable, Board board, int y, int x,String type){
        super(sprite,label,passable,board,y,x,type);
    }
    //char sprite, String label, boolean passable, Board board, int y, int x,String type
    public Wall() {
        super('#', "wall", false);
    }

    public Wall clone() {
        Wall clone = new Wall();
        clone.setLocation(BOARD, LOCATION_Y, LOCATION_X);
        return clone;
    }

    @Override
    public String toServerData() {
        return super.getServerData();
    }
}
