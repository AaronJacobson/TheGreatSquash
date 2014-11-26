package gameworld.monsters;

import gameworld.Board;
import gameworld.Creature;
import gameworld.tools.CreatureCounter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ros_Dmlamarca
 */
public class Monster extends Creature {

    public Monster(String type) {
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(getFile(type));
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry Bro, no Monster File Scanner");
        }
        makeFromFile(scanFile);
    }
    
     public Monster(String type, Board board, int y, int x) {
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(getFile(type));
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry Bro, no Monster File Scanner");
        }
        makeFromFile(scanFile);
        super.setBoard(board);
        super.setY(y);
        super.setX(x);
        board.addCreature(this);

    }
    

    private void makeFromFile(Scanner scanFile) {
        String type = scanFile.nextLine();
        super.setType(type);
        super.setName(CreatureCounter.getCount(type));
        super.setSprite(scanFile.nextLine().charAt(0));
        super.setSpeedMod(generateStat(scanFile.nextLine()));
        super.setEnduranceMod(generateStat(scanFile.nextLine()));
        super.setStrengthMod(generateStat(scanFile.nextLine()));
        super.setIntelligenceMod(generateStat(scanFile.nextLine()));
        super.setDexterityMod(generateStat(scanFile.nextLine()));
    }
    
    private int generateStat(String line) {
        Scanner readLine = new Scanner(line);
        int lowEdge = readLine.nextInt();
        int highEdge = readLine.nextInt();
        int randomStat = (int)(Math.random() * (highEdge - lowEdge + 1) + lowEdge);
        return randomStat;
    }
    
    private File getFile(String name) {
        name = name.toLowerCase();
        String filePath = "";
        if(!name.endsWith(".monster")) {
            filePath = "src/gameworld/monsters/" + name + ".monster";
        } else {
            filePath = "src/gameworld/monsters/" + name;
        }
        //filePath = "src/gameworld/maps/";
        File file = new File(filePath);
        return file;
    }
}
