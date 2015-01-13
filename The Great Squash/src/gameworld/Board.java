/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import LAN.Client;
import gameworld.obstacles.StartTile;
import java.awt.Graphics;
import java.util.ArrayList;
import tools.TypeHolder;

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
    private ArrayList<Player> PLAYERS = new ArrayList<>();
    private ArrayList<Obstacle> OBSTACLES = new ArrayList<Obstacle>();
    private ArrayList<StartTile> START_TILES = new ArrayList<StartTile>();
    private int START_TILE_NUMBER = 0;
    private boolean SHOULD_PLAYER = false;

    public Board(int y, int x, Graphics graphics) {
        GAME_BOARD = new Tile[y][x];
        SIZE_X = x;
        SIZE_Y = y;
        GRAPHICS = graphics;
        setBoardTilesNull();
    }

    public Board(int y, int x) {
        GAME_BOARD = new Tile[x][y];
        SIZE_X = y;
        SIZE_Y = x;
        GRAPHICS = null;
        setBoardTilesNull();
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
                GAME_BOARD[j][i] = new Tile(null, null);
            }
        }
    }

    public ArrayList<StartTile> getStartTiles() {
        return START_TILES;
    }

    public void placePlayer(Player player) {
        player.LOCATION_X = START_TILES.get(START_TILE_NUMBER).LOCATION_X;
        player.LOCATION_Y = START_TILES.get(START_TILE_NUMBER).LOCATION_Y;
        START_TILE_NUMBER++;
        if (START_TILE_NUMBER >= START_TILES.size()) {
            START_TILE_NUMBER = 0;
        }
        System.out.println("Board: Placed player: " + player.getName());
        addCreature(player);
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
        getTile(creature.getY(), creature.getX()).setCreature(creature);
        if(creature instanceof Player){
            PLAYERS.add((Player) creature);
        }
        CREATURES.add(creature);
    }

    public void addObstacle(Obstacle obstacle) {
        getTile(obstacle.getY(), obstacle.getX()).setObstacle(obstacle);
        OBSTACLES.add(obstacle);
    }

    public void moveCreature(int y, int x, Creature creature) {
        removeCreature(creature.getY(), creature.getX());
        creature.setY(y);
        creature.setX(x);
        setTileCreature(creature, y, x);
        creature.setMovementPoints(creature.getMovementPoints()-1);
        System.out.println("Board: " + creature.getName() + " has " + creature.getMovementPoints() + " movement points left.");
    }

    public void removeCreature(int y, int x) {
        getTile(y, x).setCreature(null);
    }

    public void setTileObstacle(int y, int x, Obstacle obstacle) {
        getTile(y, x).setObstacle(obstacle);
        obstacle.setX(x);
        obstacle.setY(y);
        if (obstacle instanceof StartTile) {
            START_TILES.add((StartTile) obstacle);
        }
        OBSTACLES.add(obstacle);
    }

    public Obstacle getTileObstacle(int y, int x) {
        return getTile(y, x).getObstacle();
    }

    public void setTileCreature(Creature creature, int y, int x) {
        getTile(y, x).setCreature(creature);
    }

    public Creature getTileCreature(int y, int x) {
        return getTile(y, x).getCreature();
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
        for (int i = 0; i < CREATURES.size(); i++) {
            if(CREATURES.get(i).getName().equalsIgnoreCase(name)){
                return CREATURES.get(i);
            }
        }
        return null;
    }
    
    public Obstacle getObstacle(String name){
        for(int i = 0;i < OBSTACLES.size();i++){
            if(OBSTACLES.get(i).getLabel().equalsIgnoreCase(name)){
                return OBSTACLES.get(i);
            }
        }
        return null;
    }
    
    public Player getPlayer(String name){
        for(int i = 0;i < PLAYERS.size();i++){
            if(PLAYERS.get(i).getName().equalsIgnoreCase(name)){
                return PLAYERS.get(i);
            }
        }
        return null;
    }

    public ArrayList<Creature> getCreatures() {
        return CREATURES;
    }

    public ArrayList<Obstacle> getObstacles() {
        return OBSTACLES;
    }
    
    public ArrayList<Player> getPlayers(){
        return PLAYERS;
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
        return toReturn;
    }
}
