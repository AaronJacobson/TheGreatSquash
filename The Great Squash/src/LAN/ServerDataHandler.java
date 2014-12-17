package LAN;

import tools.TypeHolder;
import tools.CommandHolder;
import Main.GameRunner;
import gameworld.Board;
import gameworld.obstacles.Chest;
import gameworld.Creature;
import gameworld.Displayable;
import gameworld.obstacles.Door;
import gameworld.Obstacle;
import gameworld.Player;
import gameworld.obstacles.StartTile;
import gameworld.obstacles.Wall;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ServerDataHandler implements Runnable {

    private DataInputStream STREAM_IN;
    private DataOutputStream STREAM_OUT;
    private boolean WAIT_FOR_PARAMETERS = true;
    private boolean WAIT_FOR_FLOORS = true;
    private boolean WAIT_FOR_OBSTACLES = true;
    private boolean WAIT_FOR_CREATURES = true;

    public ServerDataHandler(DataInputStream in, DataOutputStream out) {
        STREAM_IN = in;
        STREAM_OUT = out;
    }

    @Override
    public void run() {
        InitEverythingThread initStuff = new InitEverythingThread(this);
        Thread initBoardThread = new Thread(initStuff);
        initBoardThread.start();
        while (!false) {
            try {
                String serverData = STREAM_IN.readUTF();
//                System.out.println("ServerDataHandler: " + serverData);
                interpretServerData(serverData);
            } catch (IOException ex) {
                System.out.println("Sorry but we lost connection to the server");
                break;
            }
        }
    }

    public void interpretServerData(String serverData) throws IOException {
        Scanner messageScanner = new Scanner(serverData);
        String theCommand = messageScanner.next();
        if (theCommand.equals(CommandHolder.MOVE_CREATURE)) {
            int newY = messageScanner.nextInt();
            int newX = messageScanner.nextInt();
            String name = messageScanner.next();
            int oldY = messageScanner.nextInt();
            int oldX = messageScanner.nextInt();
            GameRunner.getBoard().removeCreature(oldY, oldX);
            GameRunner.getBoard().getCreature(name).setY(newY);
            GameRunner.getBoard().getCreature(name).setX(newX);
            GameRunner.getBoard().addCreature(GameRunner.getBoard().getCreature(name));
            GameRunner.updateBoard();
            System.out.println("ServerDataHandler: " + newY + " " + newX);
            System.out.println("ServerDataHandler: " + GameRunner.GAME_GUI.getCreature().getY() + " " + GameRunner.GAME_GUI.getCreature().getX());
        } else if (theCommand.equals(CommandHolder.THE_CREATURES)) {
            System.out.println("ServerDataHandler: Recieved the creatures.");
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
                    Player john = new Player(sprite, GameRunner.getBoard(), newY, newX, label);
                    GameRunner.getBoard().addCreature(john);
                } else{
                    Creature bill = new Creature(sprite,GameRunner.getBoard(),newY,newX,label,type);
                    GameRunner.getBoard().addCreature(bill);
                }
                //this is where other types of creatures go
            }
            WAIT_FOR_CREATURES = false;
            GameRunner.getBoard().setShouldPlayer(true);
            GameRunner.updateBoard();
        } else if (theCommand.equals(CommandHolder.THE_OBSTACLES)) {
            WAIT_FOR_OBSTACLES = false;
            System.out.println("ServerDataHandler: Recieved the obstacles.");
            int numberOfObstacles = messageScanner.nextInt();
            for (int currentObject = 0; currentObject < numberOfObstacles; currentObject++) {
                messageScanner.next();
                String label = messageScanner.next();
                int newY = messageScanner.nextInt();
                int newX = 1;
                newX = messageScanner.nextInt();
                boolean passable = messageScanner.nextBoolean();
                double health = messageScanner.nextDouble();
                char sprite = messageScanner.next().charAt(0);
                String type = messageScanner.next();
                if (type.equals(TypeHolder.OB_CHEST)) {
                    Chest chest = new Chest(GameRunner.getBoard(), newY, newX);
                    GameRunner.getBoard().addObstacle(chest);
                } else if (type.equals(TypeHolder.OB_DOOR)) {
                    Door door = new Door(GameRunner.getBoard(), passable, newY, newX);
                    GameRunner.getBoard().addObstacle(door);
                } else if (type.equals(TypeHolder.OB_WALL)) {
                    Wall wall = new Wall(GameRunner.getBoard(), newY, newX);
                    GameRunner.getBoard().addObstacle(wall);
                }else if (type.equals(TypeHolder.OB_START)){
                    StartTile startTile = new StartTile(newY,newX,sprite);
                    GameRunner.getBoard().addObstacle(startTile);
                    GameRunner.GAME_BOARD.getStartTiles().add(startTile);
                }
            }
            GameRunner.updateBoard();
//            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_CREATURES);
        } else if (theCommand.equals(CommandHolder.THE_FLOORS)) {
        } else if (theCommand.equals(CommandHolder.CREATE_CREATURE)) {
            messageScanner.next();
            String name = messageScanner.next();
            int locY = messageScanner.nextInt();
            int locX = messageScanner.nextInt();
            double health = messageScanner.nextDouble();
            String type = messageScanner.next();
            char sprite = messageScanner.next().charAt(0);
            if (!GameRunner.getBoard().hasCreature(name)) {
                if (type.equals(TypeHolder.PLAYER)) {
                    Player player = new Player(sprite, GameRunner.getBoard(), locY, locX, name);
                    GameRunner.GAME_BOARD.placePlayer(player);
                } else{
                    //Where other types of creatures would be created
                }
            }
            GameRunner.updateBoard();
        } else if (theCommand.equals(CommandHolder.BOARD_SIZE)) {
            int boardx = messageScanner.nextInt();
            int boardy = messageScanner.nextInt();
            GameRunner.setBoard(new Board(boardy,boardx));
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
            System.out.println("ServerDataHandler: Connection with the server lost");
        }
    }

    public void sendMove(int newY, int newX,Creature theCreature) {
        String toSend = CommandHolder.ASK_TO_MOVE + " " + newY + " " + newX + " " + theCreature.getName();
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
            System.out.println("ServerDataHandler: Board parameters have been initialized.");
//            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_FLOORS);
//            while (WAIT_FOR_FLOORS) {
//            }
            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_OBSTACLES);
            while (WAIT_FOR_OBSTACLES) {
            }
            System.out.println("ServerDataHandler: Obstacles have been initialized");
            STREAM_OUT.writeUTF(CommandHolder.INITIALIZE_CREATURES);
            while (WAIT_FOR_CREATURES) {
            }
            System.out.println("ServerDataHandler: The creatures have been initialized");
            sendCreatures();
        } catch (IOException ex) {
            System.out.println("ServerDataHandler to communicate with the server");
        }
    }
    
    public void sendCreatures(){
        for(int currentCreature = 0;currentCreature < GameRunner.GAME_GUI.getCreatures().size();currentCreature++){
            sendCreature(GameRunner.GAME_GUI.getCreatures().get(currentCreature));
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