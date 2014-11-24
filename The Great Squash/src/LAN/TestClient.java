package LAN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import GUI.tests.LANMessanger;

public class TestClient {

    private LANMessanger messanger;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public TestClient(String IP, LANMessanger messanger) {
        connectToServer(IP);
        this.messanger = messanger;
    }

    public void connectToServer(String IP) {
        System.out.println("Connecting to server...");
        try {
            socket = new Socket(IP, 4180);
            System.out.println("Connection successful.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Input streamIn = new Input(in, this);
            Thread thread = new Thread(streamIn);
            thread.start();
        } catch (IOException io) {
        }
    }

    public void sendMessage(String input) {
        try {
            String outMessage = input;
            out.writeUTF(outMessage);
        } catch (IOException io) {
            System.out.println("Sorry-bub, didn' work.");
        }
    }

    public LANMessanger getMessanger() {
        return messanger;
    }
}

class Input implements Runnable {

    DataInputStream STREAM_IN;
    TestClient client;

    public Input(DataInputStream in, TestClient client) {
        this.client = client;
        STREAM_IN = in;
    }

    public void run() {
        while (!false) {
            try {
                try {
                    LANMessanger messanger = client.getMessanger();
                    String message = STREAM_IN.readUTF();
                    //messanger.printToDisplay(message);
                } catch (SocketException se) {
                    System.out.println("\nConnection Terminated.\nBeat it Fucker");
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.exit(0);
    }
}