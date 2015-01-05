package gameworld;

import items.Item;

/**
 *
 * @author ros_dmlamarca
 */
public interface HasInventory {

    Inventory INVENTORY = null;

    public Inventory getInventory();
}
