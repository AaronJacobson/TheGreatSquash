/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import GUI.tests.TestMovementGUI;
import LAN.Client;
import LAN.CommandHolder;
import LAN.Server;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The board stores everything on the level
 *
 * @author ros_aljacobson001
 */
public class Board {

    private Tile[][] GAME_BOARD;
    private int SIZE_X;
    private int SIZE_Y;
    private Graphics GRAPHICS;
    private ArrayList<Creature> CREATURES = new ArrayList<Creature>();
    private ArrayList<Obstacle> OBSTACLES = new ArrayList<Obstacle>();
    private Client MY_CLIENT;
    private boolean SHOULD_PLAYER = false;

    public Board(int y, int x, Graphics graphics) {
        GAME_BOARD = new Tile[y][x];
        SIZE_X = x;
        SIZE_Y = y;
        GRAPHICS = graphics;
    }

    public Board(int y, int x) {
        GAME_BOARD = new Tile[x][y];
        SIZE_X = y;
        SIZE_Y = x;
        GRAPHICS = null;
    }

    public void show() {
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                System.out.print(GAME_BOARD[j][i]);
            }
            System.out.println();
        }
    }

    public void setBoardTilesNull() {
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                GAME_BOARD[j][i] = new Tile(null, null, null);
            }
        }
    }

    public Tile[][] getGameBoard() {
        return GAME_BOARD;
    }

    public Tile getTile(int y, int x) {
        return GAME_BOARD[y][x];
    }

    public void setTile(int y, int x, Tile toSetTo) {
        GAME_BOARD[y][x] = toSetTo;
    }

    public void addCreature(Creature creature) {
        getTile(creature.getY(),creature.getX()).setCreature(creature);
        CREATURES.add(creature);
    }
    
    public void removeCreature(int y,int x){
        getTile(y,x).setCreature(null);
    }

    public void setTileObstacle(int y, int x, Obstacle obstacle) {
        getTile(y, x).setObstacle(obstacle);
        OBSTACLES.add(obstacle);
    }

    public Obstacle getTileObstacle(int y, int x) {
        return getTile(y, x).getObstacle();
    }
    
    public void setTileCreature(Creature creature, int y, int x) {
        getTile(y,x).setCreature(null);
    }
    
    public Creature getTileCreature(int y, int x) {
        return getTile(y,x).getCreature();
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                output += GAME_BOARD[j][i];
            }
            output += "\n";
        }
        return output;
    }

    public int getY() {
        return SIZE_Y;
    }

    public int getX() {
        return SIZE_X;
    }

    public Creature getCreature(String name) {
        name = name.toLowerCase();
        for (int i = 0; i < CREATURES.size(); i++) {
            Creature current = CREATURES.get(i);
            if (current.getName().toLowerCase().equals(name)) {
                return current;
            }
        }
        return null;
    }

    public ArrayList<Creature> getCreatures() {
        return CREATURES;
    }

    public Client getClient() {
        return MY_CLIENT;
    }

    public ArrayList<Obstacle> getObstacles() {
        return OBSTACLES;
    }

    public void setShouldPlayer(boolean toSet) {
        SHOULD_PLAYER = toSet;
    }

    public boolean getShouldPlayer() {
        return SHOULD_PLAYER;
    }

    public boolean hasCreature(String name) {
        boolean toReturn = false;
        for (int currentCreature = 0; currentCreature < CREATURES.size(); currentCreature++) {
            if (CREATURES.get(currentCreature).getName().equals(name)) {
                toReturn = true;
            }
        }
        System.out.println("Board: " + toReturn);
        return toReturn;
    }
}
