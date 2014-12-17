/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld.obstacles;

import tools.TypeHolder;
import gameworld.Board;
import gameworld.Obstacle;
import tools.ObjectCounter;
import tools.SpriteHolder;

/**
 *
 * @author ros_aljacobson001
 */
public class StartTile extends Obstacle {
    
    public StartTile(Board board, int y,int x){
        this();
        BOARD = board;
        LOCATION_Y = y;
        LOCATION_X = x;
        PASSABLE = true;
    }
    
    public StartTile(){
        SPRITE = SpriteHolder.OB_START;
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
