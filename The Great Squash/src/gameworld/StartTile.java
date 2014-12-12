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
    
    private boolean HAS_CREATURE;
    public StartTile(int y,int x,char sprite){
        HAS_CREATURE = false;
        LABEL = ObjectCounter.getObstacleCount(TypeHolder.OB_START);
        TYPE = TypeHolder.OB_START;
        LOCATION_Y = y;
        LOCATION_X = x;
        SPRITE = sprite;
        PASSABLE = true;
    }
    
    public void setHasCreature(boolean toSet){
        HAS_CREATURE = toSet;
    }
    
    public boolean hasCreature(){
        return HAS_CREATURE;
    }
}
