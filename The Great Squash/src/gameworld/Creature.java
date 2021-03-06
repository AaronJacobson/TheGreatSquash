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
    protected Inventory INVENTORY = new Inventory(23);
    protected Weapon EQUIPT_WEAPON;
    protected boolean PASSABLE;
    protected boolean ALIVE;
    protected int LOCATION_X;
    protected int LOCATION_Y;
    protected int LEVEL;
    protected int XP;
    protected int MOVEMENT_POINTS;
    protected int MAX_HEALTH = 0;
    protected int CURRENT_HEALTH;
    protected int SPEED;
    protected int ENDURANCE;
    protected int STRENGTH;
    protected int INTELLIGENCE;
    protected int DEXTERITY;
    protected boolean DONE_WITH_TURN;

    public Creature(char sprite, Board board, int y, int x, String name, String type) {
        this();
        TYPE = type;
        NAME = name;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
        PASSABLE = false;
        MOVEMENT_POINTS = SPEED;
        DONE_WITH_TURN = false;
    }
    
    public Creature(char sprite, Board board, int y, int x, String name, String type, int currentHealth, int maxHealth, int speed, int endurance, int strength, int intelligence, int dexterity) {
        this();
        TYPE = type;
        NAME = name;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
        PASSABLE = false;
        CURRENT_HEALTH = currentHealth;
        MAX_HEALTH = maxHealth;
        SPEED = speed;
        ENDURANCE = endurance;
        STRENGTH = strength;
        INTELLIGENCE = intelligence;
        DEXTERITY = dexterity;
        MOVEMENT_POINTS = SPEED;
        DONE_WITH_TURN = false;
    }

    public Creature() {
        ALIVE = true;
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
    
    public void attackCreature(Tile tile) {
        if (tile.getCreature() != null) {
            GameRunner.CLIENT.getHandler().sendAttack(NAME, tile.getCreature().getName());
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

    public void updateLife() {
        if(CURRENT_HEALTH <= 0) {
            ALIVE = false;
            PASSABLE = true;
        } else {
            ALIVE = true;
            PASSABLE = false;
        }
    } 
    
    public boolean getLife() {
        return ALIVE;
    }
//----------------------------------------------------------------------------------------
    
    public Inventory getInventory() {
        return INVENTORY;
    }
    
    public void setEquipt(Weapon weapon) {
        EQUIPT_WEAPON = weapon;
    }

    public void addToInventory(Item item) {
        INVENTORY.addToInventory(item);
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
    
    public int getDexterity() {
        return DEXTERITY;
    }
    public void setDexterity(int dexterity) {
        DEXTERITY = dexterity;
    }
    
//----------------------------------------------------------------------------------------

    public int getIntelligence() {
        return INTELLIGENCE;
    }

    public void setIntelligence(int intelligence) {
        INTELLIGENCE = intelligence;
    }

//----------------------------------------------------------------------------------------    
    
    public int getStrength() {
        return STRENGTH;
    }
    
    public void setStrength(int strength) {
        STRENGTH = strength;
    }

//----------------------------------------------------------------------------------------    
    
    public int getSpeed() {
        return SPEED;
    }
    
    public void setSpeed(int speed) {
        SPEED = speed;
    }

//----------------------------------------------------------------------------------------
    
    public int getEndurance() {
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
    
    public int getMovementPoints(){
        return MOVEMENT_POINTS;
    }
        
    public void setMovementPoints(int movementPoints){
        MOVEMENT_POINTS = movementPoints;
    }
    
//----------------------------------------------------------------------------------------
    
    public boolean getDoneTurn(){
        return DONE_WITH_TURN;
    }
        
    public void setDoneTurn(boolean doneWith){
        DONE_WITH_TURN = doneWith;
    }
    
//----------------------------------------------------------------------------------------
    
    public boolean getPassable() {
        return PASSABLE;
    }
    
//----------------------------------------------------------------------------------------
 
    public String toServerData() {
        String output = " | ";
        output += NAME + " ";
        output += LOCATION_Y + " ";
        output += LOCATION_X + " ";
        output += CURRENT_HEALTH + " ";
        output += MAX_HEALTH + " ";
        output += TYPE + " ";
        output += SPRITE + " ";
        output += SPEED + " ";
        output += ENDURANCE + " ";
        output += STRENGTH + " ";
        output += INTELLIGENCE + " ";
        output += DEXTERITY;
        return output;
    }
}
