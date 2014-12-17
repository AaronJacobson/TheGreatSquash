/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN;

import tools.CommandHolder;
import gameworld.Board;
import tools.CreateFromDocument;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

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

    public Server(int connections,String mapName) {
        CONNECTIONS = connections;
        THE_BOARD = CreateFromDocument.createFromMaps(mapName);
        IPS = new ArrayList<String>();
        INITS = new boolean[connections];
        for (int currentInit = 0; currentInit < CONNECTIONS; currentInit++) {
            INITS[currentInit] = false;
        }
        System.out.println("Server: \n" + THE_BOARD);
        SERVER_CLIENT_CONNECTIONS = new ServerClientConnection[CONNECTIONS];
        SERVER_CHAT_CONNECTIONS = new ServerClientChat[CONNECTIONS];
    }
    
    public void makeServer(){
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
    public void waitForClientConnections(){
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
