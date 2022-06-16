package cz.vse.adventura.logic;

/**
 * Class Item - used for creation of items that can be
 * distributed through rooms, characters.
 *
 */
public class Item {
    private final String name;
    private boolean movable;
    private final String description;

    /**
     * Constructor for the instance of item
     * @param name - name of an item, it will be the key in the map of an items.
     * @param movable - if true, it can be added to an inventory, false - cannot.
     * @param description - the message that will appear when the item is collected.
     */
    public Item(String name, boolean movable, String description) {
        this.name = name;
        this.movable = movable;
        this.description = description;
    }

    /**
     * Getters for item's name, description and move-ability
     */
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean getMovable() {
        return movable;
    }

    /**
     * Setter for changing the move-ability of an item.
     */
    public void setMovable(boolean movable) {
        this.movable = movable;
    }


}
