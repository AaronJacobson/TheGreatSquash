/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.tests;

/**
 *
 * @author ros_dmlamarca
 */
public class ArgumentSettle {

    public static void main(String[] args) {
        int random = (int) (Math.random() * 257);
        System.out.print(random + "\n");
        if (random % 2 == 0) {
            System.out.print("DYLAN WINS!!!");
        } else {
            System.out.print("AARON WINS!!!");
        }
    }
}
