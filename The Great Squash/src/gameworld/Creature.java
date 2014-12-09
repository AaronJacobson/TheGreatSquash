/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Sendable;
import LAN.TypeHolder;
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
    protected Inventory INVENTORY = new Inventory(2, 6, 5, 10);
    protected int LOCATION_X;
    protected int LOCATION_Y;
    protected int LEVEL;
    protected int XP;
    protected int MAX_HEALTH = 0;
    protected double CURRENT_HEALTH;
    protected int ENDURANCE_MODIFIER;
    protected int ENDURANCE;
    protected int SPEED_MODIFIER;
    protected int SPEED;
    protected int STRENGTH_MODIFIER;
    protected int STRENGTH;
    protected int INTELLIGENCE_MODIFIER;
    protected int INTELLIGENCE;
    protected int DEXTERITY_MODIFIER;
    protected int DEXTERITY;

    public Creature(char sprite, Board board, int y, int x, String name, String type) {
        TYPE = type;
        NAME = name;
        SPRITE = sprite;
        LOCATION_X = x;
        LOCATION_Y = y;
        BOARD = board;
//        if (!TYPE.equals(TypeHolder.PLAYER)) {
//            BOARD.getClient().getHandler().sendCreature(this);
//        }
    }

    public Creature(String name, char sprite, int health, int level, int speed, int endurance, int strength, int intelligence, int dexterity) {
        NAME = name;
        SPRITE = sprite;
        LEVEL = level;
        MAX_HEALTH = health;
        SPEED = speed;
        ENDURANCE = endurance;
        STRENGTH = strength;
        INTELLIGENCE = intelligence;
        DEXTERITY = dexterity;
    }
    
    public Creature(String name){
        NAME = name;
    }

    public Creature() {
    }

    public void moveSelf(int y, int x) {
        System.out.println("Start");
        System.out.println(y + "|" + x);
        try {
            try {
                System.out.print("Test Obstacle: ");
                Obstacle moveTo = BOARD.getTileObstacle(y, x);
                if (moveTo.getPassable()) {
                    System.out.print("Passable\n");
                    move(y,x);
                } else {
                    System.out.print("Inpassable\n");
                }
            } catch (NullPointerException e) {
                System.out.print("No Obstacle\n");
                move(y,x);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Out of bounds");
            //BOARD.getClient().getHandler().sendMove(LOCATION_Y, LOCATION_X, LOCATION_Y, LOCATION_X, this);
        }
    }

    private void move(int y, int x) {
        System.out.println("move");
        BOARD.removeCreature(LOCATION_X, LOCATION_Y);
        
        LOCATION_Y = y;
        LOCATION_X = x;
        System.out.println(LOCATION_X + "|" + LOCATION_Y);
        BOARD.getTile(LOCATION_X, LOCATION_Y).setCreature(this);
    }

    @Override
    public void setSprite(char sprite) {
        SPRITE = sprite;
    }

    public void interactWithSurroundings() {
        ArrayList<Tile> surroundingTiles = getSurroundingTiles();
        for (int i = 0; i < surroundingTiles.size(); i++) {
            Tile current = surroundingTiles.get(i);
            try {
                Obstacle currentObstacle = current.getObstacle();
                if (currentObstacle instanceof Interactive) {
                    Interactive currentInteractive = (Interactive) (currentObstacle);
                    currentInteractive.update();
                }
            } catch (NullPointerException ex) {
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

    public void changeLevel(int change) {
        LEVEL += change;
        updateStats();
    }

    public int getLevel() {
        return LEVEL;
    }
    
    public void setLevel(int level) {
        LEVEL = level;
    }

    private void updateStats() {
        SPEED = (int)(SPEED_MODIFIER + (LEVEL * ((double) (SPEED_MODIFIER) / 8)));
        ENDURANCE = (int)(ENDURANCE_MODIFIER + (LEVEL * ((double) (ENDURANCE_MODIFIER) / 2)));
        STRENGTH = (int)(STRENGTH_MODIFIER + (LEVEL * ((double) (STRENGTH_MODIFIER) / 3)));
        INTELLIGENCE = (int)(INTELLIGENCE_MODIFIER + (LEVEL * ((double) (INTELLIGENCE_MODIFIER) / 3)));
        DEXTERITY = (int)(DEXTERITY_MODIFIER + (LEVEL * ((double) (DEXTERITY_MODIFIER) / 2)));
    }

    @Override
    public char getSprite() {
        return SPRITE;
    }

    public void setBoard(Board board) {
        BOARD = board;
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

    public void setName(String toSet) {
        NAME = toSet;
    }

    public String getName() {
        return NAME;
    }

    public void setType(String type) {
        TYPE = type;
    }

    public String getType() {
        return TYPE;
    }

    public void setDexterityMod(int dexterityMod) {
        DEXTERITY_MODIFIER = dexterityMod;
    }

    public int getDexterityMod() {
        return DEXTERITY_MODIFIER;
    }

    public void setIntelligenceMod(int intelligenceMod) {
        INTELLIGENCE_MODIFIER = intelligenceMod;
    }

    public int getIntelligenceMod() {
        return INTELLIGENCE_MODIFIER;
    }

    public void setStrengthMod(int strengthMod) {
        STRENGTH_MODIFIER = strengthMod;
    }

    public int getStrengthMod() {
        return STRENGTH_MODIFIER;
    }

    public void setSpeedMod(int speedMod) {
        SPEED_MODIFIER = speedMod;
    }

    public int getSpeedMod() {
        return SPEED_MODIFIER;
    }

    public void setEnduranceMod(int enduranceMod) {
        ENDURANCE_MODIFIER = enduranceMod;
    }

    public int getEnduranceMod() {
        return ENDURANCE_MODIFIER;
    }

    public void setDexterity(int dexterity) {
        DEXTERITY = dexterity;
    }

    public double getDexterity() {
        return DEXTERITY;
    }

    public void setIntelligence(int intelligence) {
        INTELLIGENCE = intelligence;
    }

    public double getIntelligence() {
        return INTELLIGENCE;
    }

    public void setStrength(int strength) {
        STRENGTH = strength;
    }

    public double getStrength() {
        return STRENGTH;
    }

    public void setSpeed(int speed) {
        SPEED = speed;
    }

    public double getSpeed() {
        return SPEED;
    }

    public void setEndurance(int endurance) {
        ENDURANCE = endurance;
    }

    public double getEndurance() {
        return ENDURANCE;
    }

    public String toServerData() {
        return " | " + NAME + " " + LOCATION_Y + " " + LOCATION_X + " " + MAX_HEALTH + " " + TYPE + " " + SPRITE;
    }
    
    public int getMaxHealth(){
        return MAX_HEALTH;
    }
    
    public void setMaxHealth(int health){
        MAX_HEALTH = health;
    }
    
    public double getCurrentHealth(){
        return CURRENT_HEALTH;
    }
    
    public void setCurrentHealth(double newHealth){
        CURRENT_HEALTH = newHealth;
    }
    
    public int getXP(){
        return XP;
    }
    
    public void setXP(int newXp){
        XP = newXp;
    }
}
