/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Sendable;
import Main.GameRunner;
import items.Item;
import items.weapons.Weapon;
import java.util.ArrayList;

/**
 *
 * @author ros_aljacobson001
 */
//Feel free to move this class to another package. I put it here because creatures go on the board.
public class Creature implements Displayable, Sendable {

    protected char SPRITE = 'X';
    protected Board BOARD;
    protected String NAME = "Creature";
    protected String TYPE = "abstract";
    protected Inventory INVENTORY = new Inventory(25);
    protected Weapon EQUIPT_WEAPON;
    protected int LOCATION_X;
    protected int LOCATION_Y;
    protected int LEVEL;
    protected int XP;
    protected int MOVEMENT_POINTS;
    protected int MAX_HEALTH = 0;
    protected int CURRENT_HEALTH;
    protected int ENDURANCE;
    protected int SPEED;
    protected int STRENGTH;
    protected int INTELLIGENCE;
    protected int DEXTERITY;
    protected boolean DONE_WITH_TURN;

    public Creature(char sprite, Board board, int y, int x, String name, String type) {
        TYPE = type;
        NAME = name;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
        MOVEMENT_POINTS = SPEED;
        DONE_WITH_TURN = false;
    }

    public Creature() {
    }

    public void moveSelf(int y, int x) {
        GameRunner.CLIENT.getHandler().sendMove(y, x, this);
    }

    public void interactWithSurroundings() {
        ArrayList<Tile> surroundingTiles = getSurroundingTiles();
        for (int i = 0; i < surroundingTiles.size(); i++) {
            Tile current = surroundingTiles.get(i);
            try {
                Obstacle currentObstacle = current.getObstacle();
                if (currentObstacle instanceof Interactive) {
                    Interactive currentInteractive = (Interactive) (currentObstacle);
                    currentInteractive.interact(this);
                }
            } catch (NullPointerException ex) {
            }
        }
    }

    public void interactWith(Tile tile) {
        if (tile.getObstacle() != null) {
            if (tile.getObstacle() instanceof Interactive) {
                GameRunner.CLIENT.getHandler().sendInteract(this.getName(), tile.getObstacle().getLabel());
            }
        }
    }

    private ArrayList<Tile> getSurroundingTiles() {
        ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    try {
                        Tile current = BOARD.getTile(LOCATION_Y + i, LOCATION_X + j);
                        surroundingTiles.add(current);
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            }
        }
        return surroundingTiles;
    }

    public Inventory getInventory() {
        return INVENTORY;
    }

    public void addToInventory(Item item) {
        INVENTORY.addToInventory(item);
    }

    public void setEquipt(Weapon weapon) {
        EQUIPT_WEAPON = weapon;
    }

//----------------------------------------------------------------------------------------
    public int getLevel() {
        return LEVEL;
    }

    public void setLevel(int level) {
        LEVEL = level;
    }

    public void changeLevel(int change) {
        LEVEL += change;
    }

//----------------------------------------------------------------------------------------
    
    public int getXP() {
        return XP;
    }

    public void setXP(int xp) {
        XP = xp;
    }

    public void changeXP(int change) {
        XP += change;
    }

//----------------------------------------------------------------------------------------  
    
    public char getSprite() {
        return SPRITE;
    }

    public void setBoard(Board board) {
        BOARD = board;
    }

//----------------------------------------------------------------------------------------  
    
    public int getX() {
        return LOCATION_X;
    }

    public void setX(int x) {
        LOCATION_X = x;
    }

    public int getY() {
        return LOCATION_Y;
    }

    public void setY(int y) {
        LOCATION_Y = y;
    }

//----------------------------------------------------------------------------------------  
    
     public String getName() {
        return NAME;
    }
    
    public void setName(String toSet) {
        NAME = toSet;
    }

//----------------------------------------------------------------------------------------   

    public String getType() {
        return TYPE;
    }
    
    public void setType(String type) {
        TYPE = type;
    }
    
//----------------------------------------------------------------------------------------
    
    public double getDexterity() {
        return DEXTERITY;
    }
    public void setDexterity(int dexterity) {
        DEXTERITY = dexterity;
    }
    
//----------------------------------------------------------------------------------------

    public double getIntelligence() {
        return INTELLIGENCE;
    }

    public void setIntelligence(int intelligence) {
        INTELLIGENCE = intelligence;
    }

//----------------------------------------------------------------------------------------    
    
    public double getStrength() {
        return STRENGTH;
    }
    
    public void setStrength(int strength) {
        STRENGTH = strength;
    }

//----------------------------------------------------------------------------------------    
    
    public double getSpeed() {
        return SPEED;
    }
    
    public void setSpeed(int speed) {
        SPEED = speed;
    }

//----------------------------------------------------------------------------------------
    
    public double getEndurance() {
        return ENDURANCE;
    }
    
    public void setEndurance(int endurance) {
        ENDURANCE = endurance;
    }

//----------------------------------------------------------------------------------------

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public void setMaxHealth(int health) {
        MAX_HEALTH = health;
    }

    public double getCurrentHealth() {
        return CURRENT_HEALTH;
    }

    public void setCurrentHealth(int newHealth) {
        CURRENT_HEALTH = newHealth;
    }
//----------------------------------------------------------------------------------------
    
    public void setMovementPoints(int movementPoints){
        MOVEMENT_POINTS = movementPoints;
    }
    
    public int getMovementPoints(){
        return MOVEMENT_POINTS;
    }
    
//----------------------------------------------------------------------------------------
    
    public void setDoneTurn(boolean doneWith){
        DONE_WITH_TURN = doneWith;
    }
    
    public boolean getDoneTurn(){
        return DONE_WITH_TURN;
    }
    
//----------------------------------------------------------------------------------------
 
    public String toServerData() {
        return " | " + NAME + " " + LOCATION_Y + " " + LOCATION_X + " " + MAX_HEALTH + " " + TYPE + " " + SPRITE + " " + SPEED;
    }
}
