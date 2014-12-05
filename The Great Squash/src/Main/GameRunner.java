package Main;

import GUI.CreateCharacter;
import GUI.GameGUI;
import GUI.StartMenu;
import LAN.Client;
import LAN.Server;
import gameworld.Board;
import gameworld.tools.ObjectCounter;

public class GameRunner {
    public static Board GAME_BOARD;
    public static StartMenu START_MENU;
    public static GameGUI GAME_GUI;
    public static Client CLIENT;
    public static Server SERVER;
    
    public static void main(String[] args) {
        ObjectCounter.clearCounters();
        CreateCharacter gui = new CreateCharacter();
        //START_MENU = new StartMenu();
    }
    
    public static void createServer(String mapName){
        START_MENU.closeMenu();
        System.out.println("Map: " + mapName);
        SERVER = new Server(10,mapName);
        SERVER.makeServer();
        ClientConnectionsThread clientConnections = new ClientConnectionsThread(SERVER);
        Thread clientConnectionsThread = new Thread(clientConnections);
        clientConnectionsThread.start();
        System.out.println(SERVER);
        CLIENT = new Client("127.0.0.1");
        CLIENT.connectToServer();
        System.out.println(CLIENT);
    }
    
    public static void startGame() {
        GAME_BOARD = CLIENT.getBoard();
        GAME_GUI = new GameGUI();
    } 
    
    public static void connectToServer(String ip){
        START_MENU.closeMenu();
        CLIENT = new Client(ip);
    }
}
class ClientConnectionsThread implements Runnable{
    private Server SERVER;
    
    public ClientConnectionsThread(Server server){
        SERVER = server;
    }
    @Override
    public void run() {
        SERVER.waitForClientConnections();
    }
    
}