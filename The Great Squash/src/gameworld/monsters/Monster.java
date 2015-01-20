package gameworld.monsters;

import gameworld.Board;
import gameworld.Creature;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import tools.CreateFromDocument;
import tools.ObjectCounter;

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
        super.setName(ObjectCounter.getCreatureCount(type));
        super.SPRITE = (scanFile.nextLine().charAt(0));
        super.setSpeed(CreateFromDocument.generateStat(scanFile.nextLine()));
        super.setEndurance(CreateFromDocument.generateStat(scanFile.nextLine()));
        super.setMaxHealth(ENDURANCE * 5 + 5);
        CURRENT_HEALTH = MAX_HEALTH;
        super.setStrength(CreateFromDocument.generateStat(scanFile.nextLine()));
        super.setIntelligence(CreateFromDocument.generateStat(scanFile.nextLine()));
        super.setDexterity(CreateFromDocument.generateStat(scanFile.nextLine()));
    }

    private File getFile(String name) {
        name = name.toLowerCase();
        String filePath = "";
        if (!name.endsWith(".monster")) {
            filePath = "src/gameworld/monsters/" + name + ".monster";
        } else {
            filePath = "src/gameworld/monsters/" + name;
        }
        File file = new File(filePath);
        return file;
    }
}
