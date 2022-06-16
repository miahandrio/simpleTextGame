package cz.vse.adventura.logic;

import java.util.*;

/**
 * Class Room - describes individual areas of the game
 *
 * "Room" represents one place (room, area, ...) in game's scenery.
 * Room can have neighbouring rooms connected with exits.
 * Every exit is a link to the next room.
 * Can contain characters and items.
 *
 * @author Mykhailo Bubnov
 */
public class Room {

    private String name;
    private String description;
    private Set<Room> exits;   // contains neighbouring rooms
    private Map<String, Item> items = new HashMap<>();
    private Map<String, ICharacter> characters = new HashMap<>();

    /**
     * Creates room with assigned name and description
     *
     * @param name room's identifier, can have multiple words
     * @param description room's description.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashSet<>();
    }

    /**
     * Defines room's exits (neighbouring rooms). Given the fact the Set structure
     * is used for saving exits, there can only be one exit to neighbouring room.
     * If this command is used second time for the same room, previous entrance
     * will be deleted without error message.
     * An exit can be created to the same room it's exiting.
     *
     * @param nextRoom - neighbouring room
     *
     */
    public void setExit(Room nextRoom) {
        exits.add(nextRoom);
    }

    /**
     * Links an item to a room.
     * Used in GamePlan for creating of the game environment
     * and in use and go command, to manipulate with an environment in the process of a game.
     *
     * @param item - item to be linked to a room
     */
    public void setItem(Item item) {
        items.put(item.getName(), item);
    }

    /**
     * Used once in a Collect command to get an instance of an item in a room.
     * @param itemName - trimmed string with a name of an item.
     * @return returns an instance of an item with a given name, can be null.
     */
    public Item returnItem(String itemName) {
        return items.get(itemName);
    }

    /**
     * Used to check if a room has a specified item.
     * @param itemName - trimmed string with a name of an item.
     * @return returns a boolean value, true if item is present, false if not.
     */
    public boolean containsItem(String itemName) {
        return items.containsKey(itemName);
    }

    /**
     * Used to remove a specified item from a room.
     * @param itemName - trimmed string with a name of an item.
     */
    public void deleteItem(String itemName) {
        items.remove(itemName);
    }

    /**
     * Links a specified character to this room
     * @param character an instance of a character.
     */
    public void addCharacter(ICharacter character) {
        characters.put(character.getName(), character);
    }




    /**
     * Returns a "long" description of an area, which can look like this:
     * You are in the room pet Store, your workplace. Looks quite empty,
     * after all the animals had gotten out.
     * Exits: main corridor
     * Items: transport box   scoop net
     *
     * @return room description with mentioning of name, description,
     * exits, items and (if present) characters.
     */
    public String longDescription() {
        if (!(this.characters.size() == 0)) {
            return "You are in the " + name + description + ".\n"
                + exitsDescription() + "\n"
                + itemsDescription() + "\n"
                + charactersDescription();
        }
        return  "You are in the " + name + description + ".\n"
            + exitsDescription() + "\n"
            + itemsDescription();
    }

    /**
     * Private method.
     * Returns text that contains the exits.
     * If no exit is present, will return "Exits here: ", but
     * in the design of this game, this situation can't occur.
     *
     * @return printable list of exits.
     */
    private String exitsDescription() {
        String returnText = "Exits here: ";
        for (Room nextRoom : exits) {
            returnText += nextRoom.getName() + "   ";
        }
        return returnText;
    }

    /**
     * Private method.
     * Returns text that contains the items.
     * Accounts for the situation that there may be no items.
     *
     * @return printable list of items.
     */
    private String itemsDescription() {
        String returnText = "Items here: ";

        if (items.keySet().size() == 0) {
            returnText += "none";
        } else {
            for (String itemName : items.keySet()) {
                returnText += itemName + "   ";
            }
        }
        return returnText;
    }


    /**
     * Private method.
     * Returns text that contains the unique description of a character.
     * If no character is present, returns an empty string
     * Can be used for multiple characters in a room.
     *
     * @return printable description of characters.
     */
    private String charactersDescription() {
        if (characters.keySet().size() == 0) {
            return "";
        } else {
            String charactersDescription = "";
            for (String characterName : characters.keySet()) {
                charactersDescription += characters.get(characterName).getDescription() + "\n";
            }
            return charactersDescription.trim();
        }
    }

    /**
     * Returns a room as an object, which name is entered as a parameter.
     * If a room with a given name isn't a neighbour, returns null.
     *
     * @param neighbourName a name of a neighbouring room.
     * @return neighbouring room as an object of class "Room", or
     * null, if the room with this name isn't a neighbour.
     */
    public Room returnNeighbouringRoom(String neighbourName) {
        List<Room>searchedRooms =
            exits.stream()
                .filter(neighbour -> neighbour.getName().equals(neighbourName))
                .toList();
        if(searchedRooms.isEmpty()){
            return null;
        }
        else {
            return searchedRooms.get(0);
        }
    }

    /**
     * Getter for a room's name.
     * Getter for items in the room.
     * Getter for a character from the room.
     * Setter for changing description.
     */
    public String getName() {
        return name;
    }
    public Map<String, Item> getItems() {
        return items;
    }
    public ICharacter getCharacter(String name) {
        return characters.get(name);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Equals method for comparing two rooms. Overlaps method equals from
     * class Object. Two rooms are the same if they have the same name.
     * This method is essential for the Set structure to work.
     *
     * @param o object, which is compared to this object
     * @return true, if either objects have the same name, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Room)) {
            return false;
        }
        Room second = (Room) o;

        //Equals method from the java.utils.Objects class compares names of rooms.
        //Returns true for equal names even if both names are null, otherwise returns false.

        return (java.util.Objects.equals(this.name, second.name));
    }

    /**
     * Method HashCode return a number identifier of an instance,
     * which is used for optimisation of saving in dynamic data structures.
     * If the method equals is overlapped, the HashCode method is also
     * needs to be overlapped.
     */
    @Override
    public int hashCode() {
        int result = 3;
        int nameHash = java.util.Objects.hashCode(this.name);
        result = 37 * result + nameHash;
        return result;
    }
}
