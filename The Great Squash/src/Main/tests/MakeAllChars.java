package Main.tests;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MakeAllChars {

    public static void main(String[] args) {
        for (int i = 12449; i < 12539; i++) {
//            String current = i + "";
//            System.out.println("\\u" + current);
            System.out.println(Character.toChars(0xB0));
        }
    }
}
