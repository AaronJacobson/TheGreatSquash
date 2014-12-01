/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items.armours;

import Auras.Aura;
import Bonuses.Bonus;
import LAN.Sendable;
import gameworld.Displayable;
import items.Item;
import items.Item;
import java.util.ArrayList;

/**
 *
 * @author ros_dmlamarca
 */
public abstract class Armor implements Item, Displayable, Sendable {
    private char SPRITE = (char)(177);// 177 = ±
    private int ARMOR_CLASS = 10;//must finalize
    private double DURABILITY = 100;//must finalize
    private ArrayList<Bonus> BONUSES;
    private ArrayList<Aura> AURAS;
    
    public Armor(char sprite,int ac,double dur){
        SPRITE = sprite;
        ARMOR_CLASS = ac;
        DURABILITY = dur;
    }
    
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }
    
    public char getSprite() {
        return SPRITE;
    }
    
    public void setAC(int toSet){
        ARMOR_CLASS = toSet;
    }
    
    public int getAC(){
        return ARMOR_CLASS;
    }
    
    public void setDurability(double toSet){
        DURABILITY = toSet;
    }
    
    public double getDurability(){
        return DURABILITY;
    }
    
    public String toServerData(){
        return " | " + SPRITE + " " + ARMOR_CLASS + " " + DURABILITY;
    }
}
