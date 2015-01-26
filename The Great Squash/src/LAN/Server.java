/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN;

import gameworld.Board;
import gameworld.Creature;
import gameworld.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import tools.NetworkInfo;
import tools.CreateFromDocument;
import tools.TypeHolder;

/**
 *
 * @author ros_aljacobson001
 */
public class Server {

    //Commands
    public static final String MOVE_CREATURE = "MOVE_THIS_CREATURE";
    public static final String ASK_TO_MOVE = "CAN_I_MOVE_THIS_CREATURE_HERE?";
    public static final String OBSTACLE = "THIS_IS_AN_OBSTACLE";
    public static final String WEAPON = "THIS_IS_A_WEAPON";
    public static final String ARMOR = "THIS_IS_A_PIECE_OF_ARMOR";
    public static final String ENCHANTMENT = "THIS_IS_AN_ENCHANTMENT";
    public static final String CONSUMABLE = "THIS_IS_A_CONSUMABLE";
    public static final String SPELL_BOOK = "THIS_IS_A_SPELL_BOOK";
    public static final String INITIALIZE_CREATURES = "GIVE_ME_THE_CREATURES";
    public static final String INITIALIZE_OBSTACLES = "GIVE_ME_THE_OBSTACLES";
    public static final String THE_CREATURES = "HERE_ARE_THE_CREATURES_TAKE_THEM_I_DON'T_WANT_THEM_I_NEVER_LIKED_YOU_ANYWAYS";
    public static final String THE_OBSTACLES = "HERE_ARE_THE_OBSTACLES";
    public static final String CREATE_CREATURE = "CREATE_THIS_CREATURE";
    public static final String BOARD_SIZE = "HERE_ARE_THE_BOARD_PARAMETERS";
    public static final String SEND_THE_BOARD_PARAMETERS = "MAY_I_HAVE_THE_BOARD_PARAMETERS";
    public static final String DISCONNECT_ME = "PLS_DISCONNECT_ME_FROM_THE_SERVER";
    public static final String INTERACT_WITH = "I_WANT_TO_INTERACT";
    public static final String ATTACK = "ATTACK";
    public static final String DONE_TURN = "I_AM_DONE_WITH_MY_TURN";
    public static final String BEGIN_TURN = "BEGIN_YOUR_TURN";
    //Other stuff
    private ServerSocket SERVER_SOCKET;
    private ServerSocket CHAT_SERVER_SOCKET;
    private Socket SOCKET;
    private Socket CHAT_SOCKET;
    private DataOutputStream CHAT_OUT;
    private DataInputStream CHAT_IN;
    private DataOutputStream DATA_OUT;
    private DataInputStream DATA_IN;
    private ArrayList<String> IPS;
    private ArrayList<Boolean> INITS;
    private ArrayList<ServerClientConnection> SERVER_CLIENT_CONNECTIONS;
//    private ServerClientConnection[] SERVER_CLIENT_CONNECTIONS;
    private ArrayList<ServerClientChat> SERVER_CHAT_CONNECTIONS;
    private int PORT_NUMBER = NetworkInfo.COMMAND_PORT_NUMBER;
    private int CHAT_PORT_NUMBER = NetworkInfo.CHAT_PORT_NUMBER;
    private Board THE_BOARD;
    private String SERVER_NAME = "";
    private boolean GM_DONE;

    public Server(String mapName) {
        THE_BOARD = CreateFromDocument.createFromMaps(mapName);
        IPS = new ArrayList<>();
        INITS = new ArrayList<>();;
        for (int currentInit = 0; currentInit < INITS.size(); currentInit++) {
            INITS.set(currentInit, false);
        }
//        System.out.println("Server: \n" + THE_BOARD);
        SERVER_CLIENT_CONNECTIONS = new ArrayList<>();
        SERVER_CHAT_CONNECTIONS = new ArrayList<>();
    }

    public void makeServer() {
        System.out.println("Server: Starting server...");
        //keeps creating the server on different ports until an unused one is found
        while (true) {
            try {
                SERVER_SOCKET = new ServerSocket(PORT_NUMBER);
                CHAT_SERVER_SOCKET = new ServerSocket(CHAT_PORT_NUMBER);
                break;
            } catch (IOException ex) {
                CHAT_PORT_NUMBER++;
                PORT_NUMBER++;
            }
        }
        try {
            System.out.println("Server: Created the server on port #" + PORT_NUMBER + " with the ip adress of " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            System.out.println("Server: Could not get local host address.");
        }
    }

    public void waitForClientConnections() {
        //waits for all the clients to connect
        ClientConnectionThread clientConnections = new ClientConnectionThread(SOCKET,SERVER_SOCKET,DATA_OUT,DATA_IN,IPS,SERVER_CLIENT_CONNECTIONS,this);
        clientConnections.start();
    }

    public void runGame() {
        //game starts
        System.out.println("Server: Starting game");
        while (true) {
            if (arePlayersDone()) {
                System.out.println("Server: The players are done.");
                if (GM_DONE) {
                    GM_DONE = false;
                    System.out.println("Server: The GM has finished their turn.");
                    setPlayersBegin();
                    refreshPlayerMovement();
                    System.out.println("Server: Refreshed player movement points.");
                    for (int i = 0; i < SERVER_CLIENT_CONNECTIONS.size(); i++) {
                        SERVER_CLIENT_CONNECTIONS.get(i).sendCommand(BEGIN_TURN);
                    }
                } else {
                    GM_DONE = true;
                    refreshMonsterMovement();
                    System.out.println("Server: Refreshed monster movement points.");
                }
            } else {
            }
        }
    }

    public boolean arePlayersDone() {
        for (int currentPlayer = 0; currentPlayer < THE_BOARD.getPlayers().size(); currentPlayer++) {
            try {
                if (!THE_BOARD.getPlayers().get(currentPlayer).getEnded()) {
                    return false;
                }
            } catch (NullPointerException e) {
                System.out.println("Server: null");
            }
        }
        if (THE_BOARD.getPlayers().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void setPlayersBegin() {
        for (int currentPlayer = 0; currentPlayer < THE_BOARD.getPlayers().size(); currentPlayer++) {
            THE_BOARD.getPlayers().get(currentPlayer).setEnded(false);
        }
    }
    
    public void refreshMonsterMovement(){
        for(Creature C : THE_BOARD.getCreatures()){
            if(!C.getType().equals(TypeHolder.PLAYER)){
                C.setMovementPoints(C.getDexterity());
                System.out.println("Server: Refreshed " + C.getName() + "'s movement points. They now have " + C.getMovementPoints() + " movement points.");
            }
        }
    }

    public void refreshPlayerMovement() {
        for (Player P : THE_BOARD.getPlayers()) {
            for (Creature C : THE_BOARD.getCreatures()) {
                if (P.getName().equals(C.getName())) {
                    C.setMovementPoints(P.getSpeed());
                    System.out.println("Server: Refreshed " + C.getName() + "'s movement points. They now have " + C.getMovementPoints() + " movement points.");
                }
            }
        }
    }

    public Board getBoard() {
        return THE_BOARD;
    }

    public boolean getGMDone() {
        return GM_DONE;
    }

    public void setGMDone(boolean gMDone) {
        GM_DONE = gMDone;
    }

    public ArrayList<String> getIPS() {
        return IPS;
    }

    public void setServerName(String toSet) {
        SERVER_NAME = toSet;
    }

    public String getServerName() {
        return SERVER_NAME;
    }

    public int getPortNumber() {
        return PORT_NUMBER;
    }

    public void removeConnection(String IP) {
        IPS.remove(IP);
    }
}

class ClientConnectionThread extends Thread {
    
    private Socket SOCKET;
    private ServerSocket SERVER_SOCKET;
    private DataOutputStream DATA_OUT;
    private DataInputStream DATA_IN;
    private ArrayList<String> IPS;
    private ArrayList<ServerClientConnection> SERVER_CLIENT_CONNECTIONS;
    private Server SERVER;
    public ClientConnectionThread(Socket socket,ServerSocket serverSocket,DataOutputStream out,DataInputStream in,ArrayList<String> ips,ArrayList<ServerClientConnection> connections,Server server){
        SOCKET = socket;
        SERVER_SOCKET = serverSocket;
        DATA_OUT = out;
        DATA_IN = in;
        IPS = ips;
        SERVER_CLIENT_CONNECTIONS = connections;
        SERVER = server;
    }
    public void run() {
        while (true) {
            try {
                SOCKET = SERVER_SOCKET.accept();
                DATA_OUT = new DataOutputStream(SOCKET.getOutputStream());
                DATA_IN = new DataInputStream(SOCKET.getInputStream());
                IPS.add(SOCKET.getInetAddress().toString());
                System.out.println("Server: Connection from " + IPS.get(IPS.size() - 1));
                ServerClientConnection newConnect = new ServerClientConnection(DATA_IN, DATA_OUT, SERVER_CLIENT_CONNECTIONS, IPS, SERVER.getBoard(),SERVER, IPS.get(IPS.size() - 1));
                SERVER_CLIENT_CONNECTIONS.add(newConnect);
                Thread CurrentConnection = new Thread(newConnect);
                CurrentConnection.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class ServerClientChat implements Runnable {

    DataInputStream STREAM_IN;
    DataOutputStream STREAM_OUT;
    ServerClientChat[] SERVER_CHAT_CONNECTIONS;

    public ServerClientChat(DataInputStream in, DataOutputStream out, ServerClientChat[] serverChatConnections) {
        STREAM_IN = in;
        STREAM_OUT = out;
        SERVER_CHAT_CONNECTIONS = serverChatConnections;
    }

    @Override
    public void run() {
        while (!false) {
            try {
                String toSend = STREAM_IN.readUTF();
                System.out.println("Incoming Chat " + toSend);
                for (int currentConnection = 0; currentConnection < SERVER_CHAT_CONNECTIONS.length; currentConnection++) {
                    try {
                        SERVER_CHAT_CONNECTIONS[currentConnection].STREAM_OUT.writeUTF(toSend);
                    } catch (SocketException ex) {
                        System.out.println("A client has disonnected");
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
