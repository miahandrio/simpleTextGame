package cz.vse.adventura.logic;


import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Test class Commands test is used to test the opportunity to end and
 * all the commands from this game, except help and inventory.
 *
 * @author    Bubnov Mykhailo
 */
public class CommandsTest {
    private final Game game = new Game();

    @Test
    public void testGameEnd() {
        Room room1 = new Room("pet store", "");
        Room room2 = new Room("room", "");
        room1.setExit(room2);
        room2.setExit(room1);
        game.getGamePlan().setCurrentRoom(room2);
        game.getInventory().insert(new Item("lovely frog", true, ""));
        game.processCommand("go pet store");
        assertFalse(game.gameEnd());
        game.getInventory().insert(new Item("charming cat", true, ""));
        game.getGamePlan().setCurrentRoom(room2);
        game.processCommand("go pet store");
        assertFalse(game.gameEnd());
        game.getInventory().insert(new Item("fluffy parrot", true, ""));
        game.getGamePlan().setCurrentRoom(room2);
        assertFalse(game.gameEnd());
        game.getGamePlan().setCurrentRoom(room1);
        assertFalse(game.gameEnd());
        assertFalse(game.isWin());
        game.getGamePlan().setCurrentRoom(room2);
        game.processCommand("go pet store");
        assertTrue(game.gameEnd());
        assertTrue(game.isWin());
        game.getGamePlan().setCurrentRoom(room2);
        game.setGameEnd(false);
        game.setWin(false);
        game.processCommand("go pet store");
        assertTrue(game.gameEnd());
        assertTrue(game.isWin());
    }

    @Test
    public void testCommandGo() {
        assertEquals("pet store", game.getGamePlan().getCurrentRoom().getName());

        game.processCommand("go main corridor");
        assertFalse(game.gameEnd());
        assertEquals("main corridor", game.getGamePlan().getCurrentRoom().getName());

        game.processCommand("go post office");
        assertFalse(game.gameEnd());
        assertEquals("post office", game.getGamePlan().getCurrentRoom().getName());

        game.processCommand("go main corridor");
        game.processCommand("go paper store");
        assertFalse(game.gameEnd());
        assertEquals("paper store", game.getGamePlan().getCurrentRoom().getName());
    }

    @Test
    public void testCommandCollect() {
        Item item1 = new Item("movable 1", true, "i can move");
        Item item2 = new Item("immovable", false, "i can't move");
        Item pencils = new Item("pencils", true, "");
        game.getGamePlan().getCurrentRoom().setItem(item1);
        game.getGamePlan().getCurrentRoom().setItem(item2);
        game.getGamePlan().getCurrentRoom().setItem(pencils);
        assertEquals("Item movable 1 was collected.\ni can move", game.processCommand("collect movable 1"));
        assertEquals("You have these items: movable 1   ", game.processCommand("inventory"));
        assertEquals("Seems that such item is nowhere to be found.", game.processCommand("collect movable 2"));
        assertEquals("Seems that such item is nowhere to be found.", game.processCommand("collect item 3"));
        assertEquals("Can't collect that item.", game.processCommand("collect immovable"));
        assertEquals("Collect what?", game.processCommand("collect"));
        assertEquals("Blue, yellow, red, green and purple pencils were collected", game.processCommand("collect pencils"));
    }

    @Test
    public void testCommandQuit() {
        assertFalse(game.gameEnd());
        game.processCommand("quit c");
        assertFalse(game.gameEnd());
        game.processCommand("quit");
        assertTrue(game.gameEnd());
    }

    @Test
    public void textCommandSpeak() {
        CharacterPostwoman postwoman = new CharacterPostwoman(game.getInventory(), game.getGamePlan());
        CharacterCashier cashier = new CharacterCashier(game.getInventory(), game.getGamePlan());
        game.getGamePlan().getCurrentRoom().addCharacter(postwoman);
        game.getGamePlan().getCurrentRoom().addCharacter(cashier);
        assertEquals("""
            Linda: We are on a brake. You'd rather not bother me right now.
            *You knew that they had their break 2 hours ago*
            a. Attach her with a parrot!(not available)
            b. "I need your help, haven't you seen a frog here?"
            c. Present her with a plushie!(not available)
            """, game.processCommand("speak linda"));
        assertEquals("try a \"respond\" command", game.processCommand("speak cashier"));
        game.processCommand("respond b");
        assertEquals("There isn't any character named parrot here.", game.processCommand("speak parrot"));
    }

    @Test
    public void testCommandRespond() {
        assertEquals("You aren't speaking right now.", game.processCommand("respond"));
        CharacterPostwoman postwoman = new CharacterPostwoman(game.getInventory(), game.getGamePlan());
        CharacterCashier cashier = new CharacterCashier(game.getInventory(), game.getGamePlan());
        game.getGamePlan().getCurrentRoom().addCharacter(postwoman);
        game.getGamePlan().getCurrentRoom().addCharacter(cashier);
        game.processCommand("speak cashier");
        assertEquals("Here is your ham.\nThe dialogue was ended.", game.processCommand("respond b"));
        assertEquals("You have these items: ham   ", game.processCommand("inventory"));
    }

    @Test
    public void textCommandUse() {
        game.getInventory().insert(new Item("cat manual", true, ""));
        assertEquals("""
            To easily catch a cat you'll need a transport box and a ham.
            Put a piece of ham gently into the transport box,
            walk away and wait till your prey is caught.""", game.processCommand("use cat manual"));
        game.getInventory().insert(new Item("transport box", true, ""));
        game.getInventory().insert(new Item("ham", true, ""));
        assertEquals("It doesn't has much use here.", game.processCommand("use transport box"));
        assertEquals("Maybe you should use it in a different place.", game.processCommand("use ham"));
        Room albert = new Room("albert", "");
        game.getGamePlan().setCurrentRoom(albert);
        assertEquals("Item transport box was removed from inventory.\n" +
            "A good beginning for a cat-trap, but how do you lure him inside this box?", game.processCommand("use transport box"));
        assertEquals("You have set up a trap for a cat! Now you'll need to walk away and wait.", game.processCommand("use ham"));
    }
}
