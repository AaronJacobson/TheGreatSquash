package LAN;

import gameworld.Board;
import gameworld.Chest;
import gameworld.Creature;
import gameworld.Displayable;
import gameworld.Door;
import gameworld.Obstacle;
import gameworld.Player;
import gameworld.Wall;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ServerDataHandler implements Runnable {

    private DataInputStream STREAM_IN;
    private DataOutputStream STREAM_OUT;
    private Client MY_CLIENT;
    private boolean WAIT_FOR_PARAMETERS = true;
    private boolean WAIT_FOR_FLOORS = true;
    private boolean WAIT_FOR_OBSTACLES = true;
    private boolean WAIT_FOR_CREATURES = true;

    public ServerDataHandler(DataInputStream in, DataOutputStream out, Client myClient) {
        STREAM_IN = in;
        STREAM_OUT = out;
        MY_CLIENT = myClient;
    }

    @Override
    public void run() {
        InitEverythingThread initStuff = new InitEverythingThread(this);
        Thread initBoardThread = new Thread(initStuff);
        initBoardThread.start();
        while (!false) {
            try {
                String serverData = STREAM_IN.readUTF();
                System.out.println("Client: " + serverData);
                interpretServerData(serverData);
            } catch (IOException ex) {
                System.out.println("Sorry but we lost connection to the server");
                break;
            }
        }
    }

    public void interpretServerData(String serverData) {
        Scanner messageScanner = new Scanner(serverData);
        String theCommand = messageScanner.next();
        System.out.println("Client: Recieved the following command: " + theCommand + " what do?");
        if (theCommand.equals(CommandHolder.MOVE_CREATURE)) {
            int newY = messageScanner.nextInt();
            int newX = messageScanner.nextInt();
            String name = messageScanner.next();
            int oldY = messageScanner.nextInt();
            int oldX = messageScanner.nextInt();
            MY_CLIENT.getBoard().removeCreature(oldY, oldX);
            MY_CLIENT.getBoard().addCreature(MY_CLIENT.getBoard().getCreature(name));
        } else if (theCommand.equals(CommandHolder.THE_CREATURES)) {
            WAIT_FOR_CREATURES = false;
            System.out.println("Client: Recieved the creatures.");
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
                    Player john = new Player(sprite, MY_CLIENT.getBoard(), newY, newX, label);
                    MY_CLIENT.getBoard().getCreatures().add(john);
                }
                //this is where other types of creatures go
            }
            WAIT_FOR_CREATURES = false;
            MY_CLIENT.getBoard().setShouldPlayer(true);
        } else if (theCommand.equals(CommandHolder.THE_OBSTACLES)) {
            System.out.println("Client: Recieved the obstacles");
            int numberOfObstacles = messageScanner.nextInt();
            //| LABEL LOCATION_Y LOCATION_X PASSABLE HEALTH SPRITE TYPE;
            for (int currentObject = 0; currentObject < numberOfObstacles; currentObject++) {
                messageScanner.next();
                String label = messageScanner.next();
                int newY = messageScanner.nextInt();
                int newX = 1;
//                System.out.println("wub: " + messageScanner.next());
                newX = messageScanner.nextInt();
                boolean passable = messageScanner.nextBoolean();
                double health = messageScanner.nextDouble();
                char sprite = messageScanner.next().charAt(0);
                String type = messageScanner.next();
                if (type.equals(TypeHolder.OB_CHEST)) {
                    Chest chest = new Chest(MY_CLIENT.getBoard(), newY, newX);
                } else if (type.equals(TypeHolder.OB_DOOR)) {
                    Door door = new Door(MY_CLIENT.getBoard(), passable, newY, newX);
                } else if (type.equals(TypeHolder.OB_WALL)) {
                    Wall wall = new Wall(MY_CLIENT.getBoard(), newY, newX);
                }
            }
            WAIT_FOR_OBSTACLES = false;
        } else if (theCommand.equals(CommandHolder.THE_FLOORS)) {
        } else if (theCommand.equals(CommandHolder.CREATE_CREATURE)) {
            messageScanner.next();
            String name = messageScanner.next();
            int locY = messageScanner.nextInt();
            int locX = messageScanner.nextInt();
            double health = messageScanner.nextDouble();
            String type = messageScanner.next();
            char sprite = messageScanner.next().charAt(0);
            if (!MY_CLIENT.getBoard().hasCreature(name)) {
                if (type.equals(TypeHolder.PLAYER)) {
                    Player john = new Player(sprite, MY_CLIENT.getBoard(), locY, locX, name);
                    MY_CLIENT.getBoard().addCreature(john);
                }
            }
        } else if (theCommand.equals(CommandHolder.BOARD_SIZE)) {
            int boardy = messageScanner.nextInt();
            int boardx = messageScanner.nextInt();
            MY_CLIENT.setBoard(new Board(boardy, boardx));
            WAIT_FOR_PARAMETERS = false;
        }
    }

    public void sendCreature(Creature toSend) {
        String commandToSend = CommandHolder.CREATE_CREATURE + toSend.toServerData();
        sendCommand(commandToSend);
    }

    public void sendCommand(String toSend) {
        try {
            STREAM_OUT.writeUTF(toSend);
        } catch (IOException ex) {
            System.out.println("Client: Connection with the server lost");
        }
    }

    public void sendMove(int newY, int newX, int oldY, int oldX, Creature theCreature) {
        System.out.println(theCreature);
        String toSend = CommandHolder.MOVE_CREATURE + " " + newY + " " + newX + " " + theCreature.getName() + " " + oldY + " " + oldX;
        sendCommand(toSend);
    }

    public void sendLocation(Displayable displayable) {
        String toSend = "";
        if (displayable instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) (displayable);
            toSend = CommandHolder.MOVE_CREATURE + " " + obstacle.getY() + " " + obstacle.getX() + " " + obstacle.getLabel() + obstacle.getPassable();
        }
    }

    public void initEverything() {
        try {
            STREAM_OUT.writeUTF(CommandHolder.SEND_THE_BOARD_PARAMETERS);
            while (WAIT_FOR_PARAMETERS) {
            }
//            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_FLOORS);
//            while (WAIT_FOR_FLOORS) {
//            }
            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_OBSTACLES);
            while (WAIT_FOR_OBSTACLES) {
            }
            System.out.println("Client: Obstacles have been initialized");
            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_CREATURES);
            while (WAIT_FOR_CREATURES) {
            }
            System.out.println("Client: The creatures have been initialized");
        } catch (IOException ex) {
            System.out.println("Unable to communicate with the server");
        }
    }
}

class InitEverythingThread implements Runnable {

    private ServerDataHandler HANDLER;

    public InitEverythingThread(ServerDataHandler handler) {
        HANDLER = handler;
    }

    @Override
    public void run() {
        HANDLER.initEverything();
    }
}