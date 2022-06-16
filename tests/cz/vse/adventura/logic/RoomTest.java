package cz.vse.adventura.logic;


import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * This test class is used to test a class Room
 *
 * @author    Bubnov Mykhailo
 */
public class RoomTest
{
    Game game = new Game();
    @Test
    public  void testCanBeNavigated() {
        Room room1 = new Room("albert", "a hypermarket");
        Room room2 = new Room("post office", "an office of post");
        Room room3 = new Room("paper store", "a paper store");
        room1.setExit(room2);
        room2.setExit(room1);
        assertEquals(room2, room1.returnNeighbouringRoom("post office"));
        assertNull(room2.returnNeighbouringRoom("paper store"));
        room1.setExit(room3);
        assertEquals(room3, room1.returnNeighbouringRoom("paper store"));
        room1.setItem(new Item("item 1", false, ""));
        room1.addCharacter(new CharacterCashier(game.getInventory(), game.getGamePlan()));
        game.getGamePlan().setCurrentRoom(room2);
        assertEquals("""
            You are in the alberta hypermarket.
            Exits here: post office   paper store  \s
            Items here: item 1  \s
            A single cashier here is present, responsibly standing at the counter.""", game.processCommand("go albert"));
    }

}
