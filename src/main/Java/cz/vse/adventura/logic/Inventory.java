package cz.vse.adventura.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class Inventory used for a creation of an inventory,
 * where items could be inserted
 */

public class Inventory {
    private final Map<String, Item> itemMap = new HashMap<>();


    /**
     * Adds the item to an inventory.
     * @param item - instance of an item to be added.
     */
    public void insert(Item item) {
        if (controlInventory()) {
            itemMap.put(item.getName(), item);
        }
    }

    /**
     * Checks if an inventory has the item in it.
     * @param itemName - string containing item name.
     * @return true if item is present, false if not.
     */
    public boolean contains(String itemName) {
        return itemMap.containsKey(itemName);
    }

    /**
     * Used for creating a set of names of items.
     * @return set of names of items
     */
    public Set<String> show() {
        return itemMap.keySet();
    }

    /**
     * Used to get the instance of the item specified.
     * @param itemName - String name of item to get an instance of.
     * @return instance of an item.
     */
    public Item get(String itemName) {
        return itemMap.get(itemName);
    }

    /**
     * Removes item from inventory.
     * @param itemName - String with a name of an item.
     */
    public void remove(String itemName) {
        itemMap.remove(itemName);
    }

    private boolean controlInventory() {
        return itemMap.size() <= 4;
    }
}
