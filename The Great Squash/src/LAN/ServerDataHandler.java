package LAN;

import Main.GameRunner;
import gameworld.Board;
import gameworld.Creature;
import gameworld.Displayable;
import gameworld.Interactive;
import gameworld.Obstacle;
import gameworld.Player;
import gameworld.obstacles.Chest;
import gameworld.obstacles.Door;
import gameworld.obstacles.StartTile;
import gameworld.obstacles.Wall;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import tools.DamageCalculations;
import tools.TypeHolder;

public class ServerDataHandler implements Runnable {

    private DataInputStream STREAM_IN;
    private DataOutputStream STREAM_OUT;
    private boolean WAIT_FOR_PARAMETERS = true;
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
        if (theCommand.equals(Server.MOVE_CREATURE)) {
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
            GameRunner.GAME_GUI.updateCreatures();
            System.out.println("ServerDataHandler: " + newY + " " + newX);
            System.out.println("ServerDataHandler: " + GameRunner.GAME_GUI.getCreature().getY() + " " + GameRunner.GAME_GUI.getCreature().getX());
            GameRunner.GAME_GUI.updateCreatures();
        } else if (theCommand.equals(Server.THE_CREATURES)) {
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
                int speed = messageScanner.nextInt();
                if (type.equals(TypeHolder.PLAYER)) {
                    Player john = new Player(sprite, GameRunner.getBoard(), newY, newX, label);
                    john.setSpeed(speed);
                    john.setMovementPoints(speed);
                    GameRunner.getBoard().addCreature(john);
                } else {
                    Creature bill = new Creature(sprite, GameRunner.getBoard(), newY, newX, label, type);
                    GameRunner.getBoard().addCreature(bill);
                }
                //this is where other types of creatures go
            }
            WAIT_FOR_CREATURES = false;
            GameRunner.getBoard().setShouldPlayer(true);
            GameRunner.updateBoard();
        } else if (theCommand.equals(Server.THE_OBSTACLES)) {
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
                    chest.setLabel(label);
                    GameRunner.getBoard().addObstacle(chest);
                } else if (type.equals(TypeHolder.OB_DOOR)) {
                    Door door = new Door(GameRunner.getBoard(), passable, newY, newX);
                    door.setLabel(label);
                    door.setSprite(sprite);
                    GameRunner.getBoard().addObstacle(door);
                } else if (type.equals(TypeHolder.OB_WALL)) {
                    Wall wall = new Wall(GameRunner.getBoard(), newY, newX);
                    wall.setLabel(label);
                    GameRunner.getBoard().addObstacle(wall);
                } else if (type.equals(TypeHolder.OB_START)) {
                    StartTile startTile = new StartTile(GameRunner.getBoard(), newY, newX);
                    startTile.setLabel(label);
                    GameRunner.getBoard().addObstacle(startTile);
                    GameRunner.GAME_BOARD.getStartTiles().add(startTile);
                }
            }
            GameRunner.updateBoard();
        } else if (theCommand.equals(Server.CREATE_CREATURE)) {
            messageScanner.next();
            String name = messageScanner.next();
            int locY = messageScanner.nextInt();
            int locX = messageScanner.nextInt();
            double currentHealth = messageScanner.nextDouble();
            double maxHealth = messageScanner.nextInt();
            String type = messageScanner.next();
            char sprite = messageScanner.next().charAt(0);
            int speed = messageScanner.nextInt();
            int endurance = messageScanner.nextInt();
            int strength = messageScanner.nextInt();
            int intelligence = messageScanner.nextInt();
            int dexterity = messageScanner.nextInt();
            if (!GameRunner.getBoard().hasCreature(name)) {
                if (type.equals(TypeHolder.PLAYER)) {
                    Player player = new Player(sprite, GameRunner.getBoard(), locY, locX, name);
                    GameRunner.GAME_BOARD.placePlayer(player);
                } else {
                    Creature creature = new Creature(sprite,GameRunner.getBoard(),locY,locX,name,type,currentHealth,maxHealth,speed,endurance,strength,intelligence,dexterity);
                }
            }
            GameRunner.updateBoard();
        } else if (theCommand.equals(Server.BOARD_SIZE)) {
            int boardx = messageScanner.nextInt();
            int boardy = messageScanner.nextInt();
            GameRunner.setBoard(new Board(boardy, boardx));
            WAIT_FOR_PARAMETERS = false;
        } else if (theCommand.equals(Server.INTERACT_WITH)) {
            String creatureName = messageScanner.next();
            String obstacleName = messageScanner.next();
            if (GameRunner.GAME_BOARD.getObstacle(obstacleName) instanceof Interactive) {
                Interactive toInteract = (Interactive) GameRunner.GAME_BOARD.getObstacle(obstacleName);
                System.out.println("ServerDataHandler: " + GameRunner.GAME_BOARD.getObstacle(obstacleName).toServerData());
                toInteract.interact(GameRunner.GAME_BOARD.getCreature(creatureName));
                System.out.println("ServerDataHandler: " + GameRunner.GAME_BOARD.getObstacle(obstacleName).toServerData());
            
            GameRunner.updateBoard();
            } 
        }else if (theCommand.equals(Server.ATTACK)) {
            Creature attacker = GameRunner.GAME_BOARD.getCreature(messageScanner.next());
            Creature defender = GameRunner.GAME_BOARD.getCreature(messageScanner.next());
            System.out.println(defender.getName() + ": " + defender.getCurrentHealth() + "/" + defender.getMaxHealth());
            DamageCalculations.attackMelee(attacker, attacker.getStrength(), defender);
            System.out.println(defender.getName() + ": " + defender.getCurrentHealth() + "/" + defender.getMaxHealth());
            GameRunner.GAME_GUI.updateCreateStats();
            GameRunner.updateBoard();
        } else if (theCommand.equals(Server.BEGIN_TURN)) {
            
            GameRunner.GAME_GUI.getCreature().setMovementPoints(GameRunner.GAME_GUI.getCreature().getSpeed());
        }
    }

    public void sendCreature(Creature toSend) {
        String commandToSend = Server.CREATE_CREATURE + toSend.toServerData();
        sendCommand(commandToSend);
    }

    public void sendCommand(String toSend) {
        try {
            STREAM_OUT.writeUTF(toSend);
        } catch (IOException ex) {
            System.out.println("ServerDataHandler: Connection with the server lost");
        }
    }

    public void sendInteract(String creatureName, String obstacleName) {
        sendCommand(Server.INTERACT_WITH + " " + creatureName + " " + obstacleName);
    }
    
    public void sendAttack(String attackerName, String defenderName) {
        sendCommand(Server.ATTACK + " " + attackerName + " " + defenderName);
    }

    public void sendMove(int newY, int newX, Creature theCreature) {
        sendCommand(Server.ASK_TO_MOVE + " " + newY + " " + newX + " " + theCreature.getName());
    }

    public void sendLocation(Displayable displayable) {
        String toSend = "";
        if (displayable instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) (displayable);
            toSend = Server.MOVE_CREATURE + " " + obstacle.getY() + " " + obstacle.getX() + " " + obstacle.getLabel() + obstacle.getPassable();
        }
    }

    public void sendDoneTurn(String playerName) {
        sendCommand(Server.DONE_TURN + " " + playerName);
    }

    public void initEverything() {
        try {
            STREAM_OUT.writeUTF(Server.SEND_THE_BOARD_PARAMETERS);
            while (WAIT_FOR_PARAMETERS) {
            }
            System.out.println("ServerDataHandler: Board parameters have been initialized.");
            STREAM_OUT.writeUTF(Server.INITIALIZE_OBSTACLES);
            while (WAIT_FOR_OBSTACLES) {
            }
            System.out.println("ServerDataHandler: Obstacles have been initialized");
            STREAM_OUT.writeUTF(Server.INITIALIZE_CREATURES);
            System.out.println("ServerDataHandler: I have sent for the creatures.");
            while (WAIT_FOR_CREATURES) {
            }
            System.out.println("ServerDataHandler: The creatures have been initialized");
            sendCreatures();
            if(GameRunner.SERVER != null){
                GameRunner.GAME_GUI.setControlledCreatures(GameRunner.GAME_BOARD.getCreatures());
            }
        } catch (IOException ex) {
            System.out.println("ServerDataHandler to communicate with the server");
        }
    }

    public void sendCreatures() {
        for (int currentCreature = 0; currentCreature < GameRunner.GAME_GUI.getCreatures().size(); currentCreature++) {
            if (GameRunner.GAME_GUI.getCreatures().get(currentCreature) instanceof Player) {
                sendCreature((Player) GameRunner.GAME_GUI.getCreatures().get(currentCreature));
            } else {
                sendCreature(GameRunner.GAME_GUI.getCreatures().get(currentCreature));
            }
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