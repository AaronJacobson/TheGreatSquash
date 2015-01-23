package Main;

import GUI.GameGUI;
import GUI.StartMenu;
import LAN.Client;
import LAN.Server;
import gameworld.Board;
import gameworld.Creature;
import gameworld.Player;
import java.util.ArrayList;
import tools.ObjectCounter;
import tools.TypeHolder;

public class GameRunner {

    public static Board GAME_BOARD;
    public static StartMenu START_MENU;
    public static GameGUI GAME_GUI;
    public static Client CLIENT;
    public static Server SERVER;

    public static void main(String[] args) {
        ObjectCounter.clearCounters();
        START_MENU = new StartMenu();
//        CreateCharacter gui = new CreateCharacter();
    }

    public static void setBoard(Board newBoard) {
        GAME_BOARD = newBoard;
    }

    public static Board getBoard() {
        return GAME_BOARD;
    }

    public static Client getClient() {
        return CLIENT;
    }

    public static Server getServer() {
        return SERVER;
    }

    public static void createServer(String mapName) {
        System.out.println("Map: " + mapName);
        SERVER = new Server(mapName);
        SERVER.makeServer();
        SERVER.waitForClientConnections();
        ClientConnectionsThread clientConnections = new ClientConnectionsThread(SERVER);
        Thread clientConnectionsThread = new Thread(clientConnections);
        clientConnectionsThread.start();
        CLIENT = new Client("127.0.0.1");
        CLIENT.connectToServer();
        startGame(START_MENU.getPlayer());
        RunGameThread runGame = new RunGameThread(SERVER);
        runGame.start();
//        GAME_BOARD = CLIENT.getBoard();
//        GAME_GUI = new GameGUI();
//        GAME_GUI.setBoard(GAME_BOARD);
        START_MENU.closeMenu();
    }

    public static void startGame(Player player) {
        GAME_BOARD = CLIENT.getBoard();
        GAME_GUI = new GameGUI();
        GAME_GUI.setBoard(GAME_BOARD);
        GAME_GUI.setCreature(player);
    }

    public static void connectToServer(String ip) {
        CLIENT = new Client(ip);
        CLIENT.connectToServer();
        startGame(START_MENU.getPlayer());
        START_MENU.closeMenu();
    }

    public static void updateBoard() {
        GAME_GUI.updateBoard(GAME_BOARD);
    }
}

class ClientConnectionsThread implements Runnable {

    private Server SERVER;

    public ClientConnectionsThread(Server server) {
        SERVER = server;
    }

    @Override
    public void run() {
        SERVER.waitForClientConnections();
    }
}

class RunGameThread extends Thread {
    private Server SERVER;
    public RunGameThread(Server server){
        SERVER = server;
    }
    
    public void run(){
        SERVER.runGame();
    }
}
