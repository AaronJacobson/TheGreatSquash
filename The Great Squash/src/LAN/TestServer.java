package LAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TestServer {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Users[] USERS;
    private String[] ips;

    public TestServer() {
        USERS = new Users[10];
        ips = new String[10];
        System.out.println("Starting server...");
        try {
            serverSocket = new ServerSocket(4180);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Server started...");
        for (int currentConnection = 0; currentConnection < Users.CONNECTIONS; currentConnection++) {
            try {
                socket = serverSocket.accept();
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                Users temp = new Users(out, in, USERS);
                USERS[currentConnection] = temp;
                ips[currentConnection] = socket.getInetAddress().toString();
                System.out.println("Connection from: " + socket.getInetAddress());
                System.out.println("User is " + USERS[currentConnection]);
                Thread thread = new Thread(USERS[currentConnection]);
                thread.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            System.in.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class Users implements Runnable {

    public static int CONNECTIONS = 10;
    DataOutputStream STREAM_OUT;
    DataInputStream STREAM_IN;
    Users[] USERS;

    public Users(DataOutputStream out, DataInputStream in, Users[] users) {
        STREAM_OUT = out;
        STREAM_IN = in;
        USERS = users;
    }

    public void run() {
        while (!false) {
            try {
                try{
                String message = STREAM_IN.readUTF();
                for (int currentUser = 0; currentUser < CONNECTIONS; currentUser++) {
                    if (USERS[currentUser] != null) {
                        System.out.println("Message sent: " + message);
                        USERS[currentUser].STREAM_OUT.writeUTF(message);
                    }
                }
                }catch(SocketException se){
                    System.out.println("A client has disconnected");
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
//how to do threads
//http://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html
