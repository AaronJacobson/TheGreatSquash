/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.TypeHolder;
import gameworld.tools.ObjectCounter;

/**
 *
 * @author ros_aljacobson001
 */
public class StartTile extends Obstacle{
    
    public StartTile(int y,int x,char sprite){
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_START);
        TYPE = TypeHolder.OB_START;
        LOCATION_Y = y;
        LOCATION_X = x;
        SPRITE = sprite;
        PASSABLE = true;
    }
    
    public StartTile(){
        SPRITE = '&';
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_START);
        TYPE = TypeHolder.OB_START;
        PASSABLE = true;
    }
    
    public StartTile clone(Board board){
        BOARD = board;
        StartTile clone = new StartTile();
        return clone;
    }
}
