package Main;

import GUI.StartMenu;
import LAN.Client;
import LAN.Server;
import gameworld.Board;
import gameworld.tools.ObjectCounter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRunner {
    public static Board GAME_BOARD;
    public static Client CLIENT;
    public static Server SERVER;
    
    public static void main(String[] args) {
        ObjectCounter.clearCounter();
        StartMenu startMenu = new StartMenu();
    }
    
    public static void createServer(String mapName){
        System.out.println("Map: " + mapName);
        CreateServer createServer = new CreateServer(SERVER,mapName);
        Thread serverThread = new Thread(createServer);
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("GameRunner: Thread interupted.");
        }
        CLIENT = new Client("127.0.0.1");
        CLIENT.connectToServer();
        System.out.println(CLIENT);
    }
    
    public static void connectToServer(String ip){
        CLIENT = new Client(ip);
    }
}

class CreateServer implements Runnable{
    private Server SERVER;
    private String MAP_NAME;
    public CreateServer(Server server,String mapName){
        SERVER = server;
        MAP_NAME = mapName;
    }
    
    @Override
    public void run() {
        SERVER = new Server(10,MAP_NAME);
    }
    
}