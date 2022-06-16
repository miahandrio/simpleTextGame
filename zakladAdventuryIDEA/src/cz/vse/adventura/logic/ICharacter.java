package cz.vse.adventura.logic;

import java.util.Map;

/**
 * An interface for the character objects
 */
public interface ICharacter {
    /**
     * Used to get name
     */
    String getName();

    /**
     * Used to get a description of a character when going into room.
     */
    String getDescription();

    /**
     * Returns the map of player responses.
     */
    Map<String, String> getResponseMap();

    /**
     * Returns the greeting of a character when is spoken to.
     */
    String returnGreeting();

    /**
     * Returns a response of a character on a specific input of a player.
     */
    String getRespond(String playerLine);

    /**
     * Tells, if a character can be spoken to.
     */
    boolean getDialogueAble();

    /**
     * Adds the variants of player response to the map
     */
    void addResponseVariants();

    /**
     * Creates printable response variants.
     */
    String printResponseVariants();
}
