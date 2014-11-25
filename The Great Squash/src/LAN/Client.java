/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN;

import GUI.GameGUI;
import gameworld.Board;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ros_aljacobson001
 */
public class Client {

    private DataInputStream STREAM_IN;
    private DataOutputStream STREAM_OUT;
    private Socket SOCKET;
    private DataInputStream CHAT_IN;
    private DataOutputStream CHAT_OUT;
    private Socket CHAT_SOCKET;
    private Board MY_BOARD;
    private GameGUI GUI;
    private ServerDataHandler DATA_HANDLER;
    private String IP;

    public Client(String ip) {
        IP = ip;
    }

    public void connectToServer() {
        ConnectToServerThread connectToServerThread = new ConnectToServerThread(this, IP);
        Thread serverConnection = new Thread(connectToServerThread);
        serverConnection.start();
    }

    public void connectToServerBackEnd(String ip) {
        int portNumber = CommandHolder.COMMAND_PORT_NUMBER;
        System.out.println("Connecting to server at " + ip);
        while (true) {
            try {
                SOCKET = new Socket(ip, portNumber);
                System.out.println("Client: Connection successful");
                STREAM_IN = new DataInputStream(SOCKET.getInputStream());
                STREAM_OUT = new DataOutputStream(SOCKET.getOutputStream());
                DATA_HANDLER = new ServerDataHandler(STREAM_IN, STREAM_OUT, this);
                Thread serverDataThread = new Thread(DATA_HANDLER);
                serverDataThread.start();
                CHAT_SOCKET = new Socket(ip, portNumber++);
                System.out.println("Client: Connection successful.");
                CHAT_IN = new DataInputStream(CHAT_SOCKET.getInputStream());
                CHAT_OUT = new DataOutputStream(CHAT_SOCKET.getOutputStream());
                ChatInput chatHandler = new ChatInput(CHAT_IN, CHAT_OUT, this);
                Thread chatThread = new Thread(chatHandler);
                chatThread.start();
                break;
            } catch (UnknownHostException ex) {
                System.out.println("Client: Cant connect to the server with that ip address");
                break;
            } catch (IOException ex) {
                System.out.println("Trying a different port");
                portNumber += 2;
            }
        }

    }

    public void setBoard(Board toSet) {
        MY_BOARD = toSet;
    }

    public Board getBoard() {
        return MY_BOARD;
    }

    public GameGUI getGUI() {
        return GUI;
    }

    public ServerDataHandler getHandler() {
        return DATA_HANDLER;
    }
}

class ChatInput implements Runnable {

    DataInputStream STREAM_IN;
    DataOutputStream STREAM_OUT;
    Client CLIENT;

    public ChatInput(DataInputStream in, DataOutputStream out, Client client) {
        STREAM_IN = in;
        STREAM_OUT = out;
        CLIENT = client;
    }

    @Override
    public void run() {
        while (!false) {
            try {
                try {
                    String message = STREAM_IN.readUTF();
                    //this is where the client would send the message to the GUI to display
                } catch (SocketException se) {
                    System.out.println("Connection to chat server lost.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendChatMessage(String toSend) {
        try {
            STREAM_OUT.writeUTF(toSend);
        } catch (IOException ex) {
            System.out.println("Failure to send chat message.");
        }
    }
}

class ConnectToServerThread implements Runnable {

    Client CLIENT;
    String IP = "";

    public ConnectToServerThread(Client client, String ip) {
        CLIENT = client;
        IP = ip;
    }

    @Override
    public void run() {
        CLIENT.connectToServerBackEnd(IP);
    }
}
