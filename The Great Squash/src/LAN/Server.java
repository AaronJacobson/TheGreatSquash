/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN;

import gameworld.Board;
import gameworld.DocumentToBoard;
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
import java.util.Scanner;

/**
 *
 * @author ros_aljacobson001
 */
public class Server {

    private ServerSocket SERVER_SOCKET;
    private ServerSocket CHAT_SERVER_SOCKET;
    private Socket SOCKET;
    private Socket CHAT_SOCKET;
    private DataOutputStream CHAT_OUT;
    private DataInputStream CHAT_IN;
    private DataOutputStream DATA_OUT;
    private DataInputStream DATA_IN;
    private ArrayList<String> IPS;
    private boolean[] INITS;
    private ServerClientConnection[] SERVER_CLIENT_CONNECTIONS;
    private ServerClientChat[] SERVER_CHAT_CONNECTIONS;
    private int PORT_NUMBER = CommandHolder.COMMAND_PORT_NUMBER;
    private int CHAT_PORT_NUMBER = CommandHolder.CHAT_PORT_NUMBER;
    private Board THE_BOARD;
    private int CONNECTIONS;
    private String SERVER_NAME = "";

    public Server(int connections) {
        CONNECTIONS = connections;
        //This is where the board would be read from a txt file
        Scanner askForMap = new Scanner(System.in);
        System.out.println("Please enter the name of the map file:");
        THE_BOARD = DocumentToBoard.createBoard("src/gameworld/maps/" + askForMap.next());
        IPS = new ArrayList<String>();
        INITS = new boolean[connections];
        for (int currentInit = 0; currentInit < CONNECTIONS; currentInit++) {
            INITS[currentInit] = false;
        }
        SERVER_CLIENT_CONNECTIONS = new ServerClientConnection[CONNECTIONS];
        SERVER_CHAT_CONNECTIONS = new ServerClientChat[CONNECTIONS];
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
        
        //waits for all the clients to connect
        for (int currentConnection = 0; currentConnection < CONNECTIONS; currentConnection++) {
            try {
                SOCKET = SERVER_SOCKET.accept();
                DATA_OUT = new DataOutputStream(SOCKET.getOutputStream());
                DATA_IN = new DataInputStream(SOCKET.getInputStream());
                IPS.add(SOCKET.getInetAddress().toString());
                System.out.println("Server: Connection from " + IPS.get(currentConnection));
                ServerClientConnection newConnect = new ServerClientConnection(DATA_IN, DATA_OUT, SERVER_CLIENT_CONNECTIONS, IPS, THE_BOARD, this, INITS);
                SERVER_CLIENT_CONNECTIONS[currentConnection] = newConnect;
                Thread CurrentConnection = new Thread(newConnect);
                CurrentConnection.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    public Board getBoard() {
        return THE_BOARD;
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
    
    public int getConnections(){
        return CONNECTIONS;
    }
    
    public int getPortNumber(){
        return PORT_NUMBER;
    }
}

class ServerClientConnection implements Runnable {

    DataInputStream STREAM_IN;
    DataOutputStream STREAM_OUT;
    ServerClientConnection[] SERVER_CLIENT_CONNECTIONS;
    ArrayList<String> IPS;
    boolean[] INITS;
    Server THE_SERVER;

    public ServerClientConnection(DataInputStream in, DataOutputStream out, ServerClientConnection[] serverClientConnections, ArrayList<String> ips, Board gameBoard, Server server, boolean[] inits) {
        SERVER_CLIENT_CONNECTIONS = serverClientConnections;
        THE_SERVER = server;
        STREAM_IN = in;
        STREAM_OUT = out;
        IPS = ips;
        INITS = inits;
    }

    @Override
    public void run() {
        while (!false) {
            try {
                String toSend = STREAM_IN.readUTF();
                sendCommand(toSend);
                System.out.println("Server: Incoming message: " + toSend);
                interpretMessage(toSend);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void interpretMessage(String theMessage) {
        Scanner messageScanner = new Scanner(theMessage);
        String theCommand = messageScanner.next();
        if (theCommand.equals(CommandHolder.MOVE_CREATURE)) {
            int newY = messageScanner.nextInt();
            int newX = messageScanner.nextInt();
            String name = messageScanner.next();
            int oldY = messageScanner.nextInt();
            int oldX = messageScanner.nextInt();
            THE_SERVER.getBoard().removeCreature(oldX, oldX);
            THE_SERVER.getBoard().addCreature(THE_SERVER.getBoard().getCreature(name));
        } else if (theCommand.equals(CommandHolder.INITIALIZE_CREATURES)) {
            sendCreatures();
        } else if (theCommand.equals(CommandHolder.INITIALIZE_OBSTACLES)) {
            sendObstacles();
        } else if (theCommand.equals(CommandHolder.INITIALIZE_FLOORS)) {
            sendFloors();
        } else if (theCommand.equals(CommandHolder.CREATE_CREATURE)) {
            messageScanner.next();
            String name = messageScanner.next();
            int locY = messageScanner.nextInt();
            int locX = messageScanner.nextInt();
            double health = messageScanner.nextDouble();
            String type = messageScanner.next();
            char sprite = messageScanner.next().charAt(0);
            if (!THE_SERVER.getBoard().hasCreature(name)) {
                if (type.equals(TypeHolder.PLAYER)) {
                    Player john = new Player(sprite, THE_SERVER.getBoard(), locY, locX, name);
                    THE_SERVER.getBoard().addCreature(john);
                }
            }
        }else if(theCommand.equals(CommandHolder.SEND_THE_BOARD_PARAMETERS)){
            String theParameters = CommandHolder.BOARD_SIZE + " " + THE_SERVER.getBoard().getY() + " " + THE_SERVER.getBoard().getX();
            sendBoardInit(theParameters);
        }

    }

    public void sendFloors() {
    }

    public void sendObstacles() {
        String toSend = CommandHolder.THE_OBSTACLES + " " + THE_SERVER.getBoard().getObstacles().size();
        for (int currentObstacle = 0; currentObstacle < THE_SERVER.getBoard().getObstacles().size(); currentObstacle++) {
            toSend += THE_SERVER.getBoard().getObstacles().get(currentObstacle).getServerData();
        }
        sendBoardInit(toSend);
    }

    public void sendCreatures() {
        String toSend = CommandHolder.THE_CREATURES + " " + THE_SERVER.getBoard().getCreatures().size();
        for (int currentCreature = 0; currentCreature < THE_SERVER.getBoard().getCreatures().size(); currentCreature++) {
            toSend += THE_SERVER.getBoard().getCreatures().get(currentCreature).toServerData();
        }
        Scanner messageScanner = new Scanner(toSend);
        messageScanner.next();
        int numberOfCreatures = messageScanner.nextInt();
        for (int currentCreature = 0; currentCreature < numberOfCreatures; currentCreature++) {
            messageScanner.next();
            String label = messageScanner.next();
            int newY = messageScanner.nextInt();
            int newX = messageScanner.nextInt();
            double health = messageScanner.nextDouble();
            String type = messageScanner.next();
            char sprite = messageScanner.next().charAt(0);
            if (type.equals(TypeHolder.PLAYER)) {
                Player john = new Player(sprite, THE_SERVER.getBoard(), newY, newX, label);
                THE_SERVER.getBoard().getCreatures().add(john);
            }
            //this is where other types of creatures go
        }
        sendBoardInit(toSend);
    }

    public void sendBoardInit(String toSend) {
        for (int currentConnection = 0; currentConnection < SERVER_CLIENT_CONNECTIONS.length; currentConnection++) {
            try {
                SERVER_CLIENT_CONNECTIONS[currentConnection].STREAM_OUT.writeUTF(toSend);
            } catch (IOException ex) {
                System.out.println("Server: Unable to connect to a client at: " + IPS.get(currentConnection));
            } catch (NullPointerException ex) {
            }
        }
    }

    public void sendCommand(String toSend) {
        for (int currentConnection = 0; currentConnection < SERVER_CLIENT_CONNECTIONS.length; currentConnection++) {
            try {
                try {
                    SERVER_CLIENT_CONNECTIONS[currentConnection].STREAM_OUT.writeUTF(toSend);
                } catch (IOException ex) {
                    System.out.println("Server: A client has disconnected: " + IPS.get(currentConnection));
                }
            } catch (NullPointerException ex) {
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