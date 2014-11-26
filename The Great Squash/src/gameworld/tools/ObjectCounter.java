/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameworld.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ros_dmlamarca
 */
public class ObjectCounter {
    private static final File COUNT_FILE = new File("src\\gameworld\\tools\\countlog.tgs");

    public static String getCount(String type) {
        String countLog = getFileText(COUNT_FILE);
        String finalLog = "";
        Scanner scanLog = new Scanner(countLog);
        boolean containsType = false;
        int typeCount = 0;

        while (scanLog.hasNextLine()) {
            String current = scanLog.nextLine();
            if (current.startsWith(type + " ")) {
                int spacePlace = current.indexOf(" ");
                typeCount = Integer.decode((String) (current.subSequence(spacePlace + 1, current.length()))) + 1;
                containsType = true;

                current = current.substring(0, spacePlace) + " " + typeCount;
            }
            finalLog += current + "\n";
        }

        if (!containsType) {
            finalLog += type + " " + 0;
        }

        setFileText(COUNT_FILE, finalLog);

        return generateName(type, typeCount);
    }

    public static void clearCounter() {
        setFileText(COUNT_FILE, "");
    }

    private static String generateName(String type, int count) {
        String name = type + "_";
        if (count / 100 > 0) {
            name += count;
        } else if (count / 10 > 0) {
            name += "0" + count;
        } else {
            name += "00" + count;
        }
        return name;
    }

    private static void setFileText(File file, String text) {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException ex) {
        } catch (UnsupportedEncodingException ex) {
        }

        fileWriter.println(text);
        fileWriter.close();
    }

    private static String getFileText(File file) {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);

        } catch (FileNotFoundException ex) {
        }
        String countLog = "";
        while (fileScanner.hasNextLine()) {
            String current = fileScanner.nextLine();
            if (!current.equals("")) {
                countLog += current + "\n";
            }
        }
        return countLog;
    }
}
