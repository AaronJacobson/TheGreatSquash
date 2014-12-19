/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auras;

/**
 *
 * @author ros_aljacobson001
 */
public abstract class Aura {

    private int CENTER_LOCATION_Y = 0;
    private int CENTER_LOCATION_X = 0;
    private int SIZE = 1;

    public void setLocation(int newY, int newX) {
        CENTER_LOCATION_Y = newY;
        CENTER_LOCATION_X = newX;
    }

    public int getY() {
        return CENTER_LOCATION_Y;
    }

    public int getX() {
        return CENTER_LOCATION_X;
    }

    public void setSize(int newSize) {
        SIZE = newSize;
    }

    public int getSize() {
        return SIZE;
    }
}
