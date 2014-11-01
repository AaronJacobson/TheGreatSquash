/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld;

import GUI.TestMovementGUI;
import LAN.Client;
import LAN.CommandHolder;
import LAN.Server;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The board stores everything on the level
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
    
    /**
     *
     * @param x
     * @param y
     */
    public Board(int y,int x,boolean toServer,Graphics graphics,TestMovementGUI gui){
        if(toServer){
            CreateServer temp = new CreateServer(this,10);
            Thread serverThread = new Thread(temp);
            serverThread.start();
        }
        GAME_BOARD = new Tile[y][x];
        MY_CLIENT = new Client(CommandHolder.AARON_WORK_IP,CommandHolder.COMMAND_PORT_NUMBER,this,gui);
        SIZE_X = x;
        SIZE_Y = y;
        GRAPHICS = graphics;
    }
    
    public Board(int y,int x,boolean toServer,TestMovementGUI gui){
        if(toServer){
            CreateServer temp = new CreateServer(this,10);
            Thread serverThread = new Thread(temp);
            serverThread.start();
        }
        GAME_BOARD = new Tile[x][y];
        MY_CLIENT = new Client(CommandHolder.AARON_WORK_IP,CommandHolder.COMMAND_PORT_NUMBER,this,gui);
        SIZE_X = y;
        SIZE_Y = x;
        GRAPHICS = null;
    }
    
    public Board(int y,int x) {
        GAME_BOARD = new Tile[x][y];
        SIZE_X = y;
        SIZE_Y = x;
        GRAPHICS = null;
    }
    
    public Board(Board board, boolean toServer, TestMovementGUI gui) {
        if(toServer){
            CreateServer temp = new CreateServer(this,10);
            Thread serverThread = new Thread(temp);
            serverThread.start();
        }
        GAME_BOARD = board.getGameBoard();
        MY_CLIENT = new Client(CommandHolder.AARON_WORK_IP,CommandHolder.COMMAND_PORT_NUMBER,this,gui);
        SIZE_X = board.getY();
        SIZE_Y = board.getX();
        GRAPHICS = null;
    }
    
    public void updateDisplay() {
        MY_CLIENT.getGUI().getDisplay().setText(this.toString());
    }
    
    public void show(){
        for(int i = 0; i < SIZE_X; i++) {
            for(int j = 0; j < SIZE_Y; j++) {
                System.out.print(GAME_BOARD[j][i]);
            }
            System.out.println();
        }
    }
    
    /**
     *
     */
    public void setBoardTilesNull() {
        for(int i = 0; i < SIZE_X; i++) {
            for(int j = 0; j < SIZE_Y; j++) {
                GAME_BOARD[j][i] = new Tile(null,null,null);
            }
        }
    }
    
    /**
     *
     * @return The game board
     */
    public Tile[][] getGameBoard(){
        return GAME_BOARD;
    }
    
    /**
     *
     * @param y
     * @param x
     * @return The tile specified by the x and y values given
     */
    public Tile getTile(int y,int x){
        return GAME_BOARD[y][x];
    }
    
    /**
     * Changes the specified tile (by x and y values) to the given Tile
     * @param y
     * @param x
     * @param toSetTo
     */
    public void setTile(int y,int x,Tile toSetTo){
        GAME_BOARD[y][x] = toSetTo;
    }
    
    public void setTileCreature(int y, int x, Creature creature) {
        getTile(y,x).setCreature(creature);
        CREATURES.add(creature);
    }
    
    public void setTileObstacle(int y, int x, Obstacle obstacle) {
        getTile(y,x).setObstacle(obstacle);
        OBSTACLES.add(obstacle);
    }
    
    public Obstacle getTileObstacle(int y, int x) {
        return getTile(y,x).getObstacle();
    }
    
    public String toString() {
        String output = "";
        for(int i = 0; i < SIZE_X; i++) {
            for(int j = 0; j < SIZE_Y; j++) {
                output += GAME_BOARD[j][i];
            }
            output += "\n";
        }
        return output;
    }
    
    public int getY(){
        return SIZE_Y;
    }
    
    public int getX(){
        return SIZE_X;
    }
    
    public Creature getCreature(String name) {
        for(int i = 0; i < CREATURES.size(); i++) {
            Creature current = CREATURES.get(i);
            if(current.getName().equals(name)) {
                return current;
            }
        }
        return null;
    }
    
    public ArrayList<Creature> getCreatures(){
        return CREATURES;
    }
    
    public Client getClient(){
        return MY_CLIENT;
    }
    
    public ArrayList<Obstacle> getObstacles(){
        return OBSTACLES;
    }
    
    public void setShouldPlayer(boolean toSet){
        SHOULD_PLAYER = toSet;
    }
    
    public boolean getShouldPlayer(){
        return SHOULD_PLAYER;
    }
}
class CreateServer implements Runnable{
    
    private Board THE_BOARD;
    private int CONNECTIONS;
    
    public CreateServer(Board theBoard,int connections){
        THE_BOARD = theBoard;
        CONNECTIONS = connections;
    }
    @Override
    public void run() {
        Server theServer = new Server(CONNECTIONS,THE_BOARD);
    }   
}
