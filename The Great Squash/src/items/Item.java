/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.util.Scanner;

/**
 *
 * @author ros_dmlamarca
 */
public interface Item {
    public String toString();
    
    public void loadFromFile(String fileElement);
}
