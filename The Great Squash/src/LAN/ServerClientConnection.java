package LAN;

import tools.TypeHolder;
import tools.CommandHolder;
import gameworld.Board;
import gameworld.Obstacle;
import gameworld.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

class ServerClientConnection implements Runnable {

    DataInputStream STREAM_IN;
    DataOutputStream STREAM_OUT;
    ServerClientConnection[] SERVER_CLIENT_CONNECTIONS;
    ArrayList<String> IPS;
    boolean[] INITS;
    Server THE_SERVER;
    String IP;

    public ServerClientConnection(DataInputStream in, DataOutputStream out, ServerClientConnection[] serverClientConnections, ArrayList<String> ips, Board gameBoard, Server server, boolean[] inits,String ip) {
        SERVER_CLIENT_CONNECTIONS = serverClientConnections;
        THE_SERVER = server;
        STREAM_IN = in;
        STREAM_OUT = out;
        IPS = ips;
        IP = ip;
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
                THE_SERVER.removeConnection(IP);
                System.out.println("ServerClientConnection: A client has disconnected from " + IP);
                break;
            }
        }
    }

    public void interpretMessage(String theMessage) {
        Scanner messageScanner = new Scanner(theMessage);
        String theCommand = messageScanner.next();
        if (theCommand.equals(CommandHolder.ASK_TO_MOVE)) {
            int y = messageScanner.nextInt();
            int x = messageScanner.nextInt();
            String creatureName = messageScanner.next();
            if (THE_SERVER.getBoard().hasCreature(creatureName)) {
                try {
                    try {
                        Obstacle moveTo = THE_SERVER.getBoard().getTileObstacle(y, x);
                        if (moveTo.getPassable()) {
                            sendCommand(CommandHolder.MOVE_CREATURE + " " + y + " " + x + " " + creatureName + " " + THE_SERVER.getBoard().getCreature(creatureName).getY() + " " + THE_SERVER.getBoard().getCreature(creatureName).getX());
                            THE_SERVER.getBoard().moveCreature(y, x, THE_SERVER.getBoard().getCreature(creatureName));
                        } else {
                            System.out.println("ServerClientConnection: Inpassable");
                        }
                    } catch (NullPointerException e) {
                        sendCommand(CommandHolder.MOVE_CREATURE + " " + y + " " + x + " " + creatureName + " " + THE_SERVER.getBoard().getCreature(creatureName).getY() + " " + THE_SERVER.getBoard().getCreature(creatureName).getX());
                        THE_SERVER.getBoard().moveCreature(y, x, THE_SERVER.getBoard().getCreature(creatureName));
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("ServerClientConnection: Out of bounds");
                }
            } else {
                System.out.println("ServerClientConnection: Error, I have been given a creature whom I don't have. Why do you hate me so?");
            }
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
                    Player player = new Player(sprite, THE_SERVER.getBoard(), locY, locX, name);
                    THE_SERVER.getBoard().placePlayer(player);
                    sendCommand(CommandHolder.MOVE_CREATURE + " " + player.getY() + " " + player.getX() + " " + name + " " + locY + " " + locX);
                }
            }
        } else if (theCommand.equals(CommandHolder.SEND_THE_BOARD_PARAMETERS)) {
            String theParameters = CommandHolder.BOARD_SIZE + " " + THE_SERVER.getBoard().getY() + " " + THE_SERVER.getBoard().getX();
            sendBoardInit(theParameters);
        }

    }

    public void sendFloors() {
    }

    public void sendObstacles() {
        String toSend = CommandHolder.THE_OBSTACLES + " " + THE_SERVER.getBoard().getObstacles().size();
        for (int currentObstacle = 0; currentObstacle < THE_SERVER.getBoard().getObstacles().size(); currentObstacle++) {
            toSend += THE_SERVER.getBoard().getObstacles().get(currentObstacle).toServerData();
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