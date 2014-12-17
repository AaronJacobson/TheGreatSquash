/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import gameworld.Board;
import gameworld.Displayable;
import gameworld.obstacles.Door;
import gameworld.obstacles.StartTile;
import gameworld.obstacles.Wall;
import gameworld.monsters.Monster;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author ros_dmlamarca
 */
public class CreateFromDocument {

    public static Board createFromMaps(String mapName) {
        String filePath = "";
        if(!mapName.endsWith(".map")) {
            filePath = "src/gameworld/maps/" + mapName + ".map";
        } else {
            filePath = "src/gameworld/maps/" + mapName;
        }
        //filePath = "src/gameworld/maps/";
        Board board = createBoard(filePath);
        return board;
    }
    
    public static Board createBoard(String filePath) {
//        System.out.println(filePath);
        Board board = null;
        try {
            File textFile = new File(filePath);
            Scanner readFile = new Scanner(textFile);

            String objectCode = getNextFileElement(readFile);
            String stringBoard = getNextFileElement(readFile);
            String creatureCode = getNextFileElement(readFile);

            Hashtable<String, Displayable> creatorTable = getCreatorTable(objectCode);

            board = makeBoard(creatorTable, stringBoard);

            makeMonsters(creatureCode, board);
            
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry bub, but we couldn't make your file (well File Scanner). It just wasn't in the numbers.");
        }

//        System.out.println(board);
        return board;
    }

    private static Hashtable<String, Displayable> getCreatorTable(String input) {
        Hashtable<String, Displayable> hashTable = new Hashtable<String, Displayable>();
        Scanner inputScanner = new Scanner(input);

        while (inputScanner.hasNextLine()) {
            Scanner lineScanner = new Scanner(inputScanner.nextLine());

            String key = lineScanner.next();
            String objectName = lineScanner.next();

            Displayable object = createDisplayable(objectName);

            hashTable.put(key, object);
        }

        return hashTable;
    }

    private static Displayable createDisplayable(String objectName) {
        Displayable object = null;

        if (objectName.equals("wall")) {
            object = new Wall();
        } else if (objectName.equals("closeddoor")) {
            object = new Door(false);
        } else if (objectName.equals("opendoor")) {
            object = new Door(true);
        } else if (objectName.equals("starttile")){
            object = new StartTile();
        }

        return object;
    }

    private static String getNextFileElement(Scanner readFile) {
        String string = "";
        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            if (!line.equals("")) {
                if (string.equals("")) {
                    string += line;
                } else {
                    string += "\n" + line;
                }
            } else {
                break;
            }
        }
        return string;
    }
    
    public static String getLineElement(String line) {
        String output = "";
        Scanner lineScanner = new Scanner(line);
        boolean first = true;
        while(lineScanner.hasNext()) {
            String current = lineScanner.next();
            //System.out.println(current);
            if(!current.contains("*") && first) {
                output += current;
                first = false;
            } else if(!current.contains("*")) {
                output += " " + current;
            }
        }
        return output;
    }

    private static Board makeBoard(Hashtable<String, Displayable> creatorTable, String stringBoard) {
        Scanner getSize = new Scanner(stringBoard);
        int sizeX = 0;
        int sizeY = 0;
        while (getSize.hasNextLine()) {
            String line = getSize.nextLine();
            int lineX = line.length();
            if (lineX > sizeX) {
                sizeX = lineX;
            }
            sizeY++;
        }
        Board board = new Board(sizeY, sizeX);
        board.setBoardTilesNull();
        Scanner getBoard = new Scanner(stringBoard);
        int y = 0;
        while (getBoard.hasNextLine()) {
            String line = getBoard.nextLine();
            for (int x = 0; x < line.length(); x++) {
                String currentChar = line.charAt(x) + "";
                Displayable displayable = creatorTable.get(currentChar);
                if (displayable != null) {
                    if (displayable instanceof Wall) {
                        Wall wall = ((Wall) (displayable)).clone(board);
                        //System.out.println(wall);
                        board.setTileObstacle(x, y, wall);
                    } else if (displayable instanceof Door) {
                        Door door = ((Door) (displayable)).clone(board);
                        //System.out.println(door);
                        board.setTileObstacle(x, y, door);
                    } else if (displayable instanceof StartTile){
                        StartTile startTile = ((StartTile) displayable).clone(board);
                        board.setTileObstacle(x,y,startTile);
                    }
                }
            }
            //System.out.println();
            y++;
        }
        return board;
    }

    private static void makeMonsters(String creatureCode, Board board) {
        Scanner scanCreatures = new Scanner(creatureCode);
        while(scanCreatures.hasNextLine()) {
            String line = scanCreatures.nextLine();
            Scanner readLine = new Scanner(line);
            String type = readLine.next();
            int x = readLine.nextInt();
            int y = readLine.nextInt();
            Monster monster = new Monster(type, board, y, x);
        }
    }
}
