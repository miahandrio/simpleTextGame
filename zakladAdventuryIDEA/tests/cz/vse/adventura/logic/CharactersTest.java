package cz.vse.adventura.logic;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharactersTest {
    private Game game = new Game();
    private GamePlan gamePlan = new GamePlan(game.getInventory());

    @Before
    public void setUp() {
        CharacterPostwoman postwoman = new CharacterPostwoman(game.getInventory(), gamePlan);
        CharacterCashier cashier = new CharacterCashier(game.getInventory(), gamePlan);
        Room room = new Room("room", "");
        game.getGamePlan().setCurrentRoom(room);
        room.addCharacter(postwoman);
        room.addCharacter(cashier);
    }

    @Test
    public void characterPostwomanSpeakTest() {
        assertEquals("Linda: We are on a brake. You'd rather not bother me right now.\n" +
            "*You knew that they had their break 2 hours ago*\n" +
            "a. Attach her with a parrot!(not available)\n" +
            "b. \"I need your help, haven't you seen a frog here?\"\n" +
            "c. Present her with a plushie!(not available)\n", game.processCommand("speak linda"));
        assertEquals("try a \"respond\" command", game.processCommand("speak linda"));
        game.getInventory().insert(new Item("fluffy parrot", true, ""));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Linda: I said we're on a brake, go away!\n" +
            "a. Attack her with a parrot!\n" +
            "b. \"I need your help, haven't you seen a frog here?\"\n" +
            "c. Present her with a plushie!(not available)\n", game.processCommand("speak linda"));
        game.getInventory().insert(new Item("plushie", true, ""));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Linda: I said we're on a brake, go away!\n" +
            "a. Attack her with a parrot!\n" +
            "b. \"I need your help, haven't you seen a frog here?\"\n" +
            "c. Present her with a plushie!\n", game.processCommand("speak linda"));

    }

    @Test
    public void characterPostwomanRespondTestA() {
        game.processCommand("speak linda");
        assertEquals("If only you had your parrot with you...\n" +
            "The dialogue was ended.", game.processCommand("respond a"));
        game.getInventory().insert(new Item("fluffy parrot", true, ""));
        game.getInventory().insert(new Item("plushie", true, ""));
        assertEquals("The parrot proceeds to attack her by your command.\n" +
            "He bites her and blows her hair.\n" +
            "Linda runs away, loudly screaming in the process.\n" +
            "She drops the frog and you pick it up.\n" +
            "A lovely frog was added to your inventory.\n" +
            "\n" +
            "The dialogue was ended.", game.processCommand("respond a"));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Linda isn't present.\n" +
            "The dialogue was ended.", game.processCommand("speak linda"));
    }

    @Test
    public void characterPostwomanRespondTestB() {
        game.processCommand("speak linda");
        assertEquals("Linda: Have you heard me right? We're on a break, and I hadn't seen any stupid frogs here!\n" +
            "The dialogue was ended.", game.processCommand("respond b"));
        game.getInventory().insert(new Item("fluffy parrot", true, ""));
        game.getInventory().insert(new Item("plushie", true, ""));
        assertEquals("Linda: Have you heard me right? We're on a break, and I hadn't seen any stupid frogs here!\n" +
            "The dialogue was ended.", game.processCommand("respond b"));
    }

    @Test
    public void characterPostwomanRespondTestC() {
        game.processCommand("speak linda");
        assertEquals("You don't have any gifts with you. \n" +
            "Maybe you should check out the flying tiger...\n" +
            "The dialogue was ended.", game.processCommand("respond c"));
        game.getInventory().insert(new Item("fluffy parrot", true, ""));
        game.getInventory().insert(new Item("plushie", true, ""));
        assertEquals("She blushed and mumbled with a smile on her face:\n" +
            "Linda: you're such a sweet young man, you wanted that frog? Take it.\n" +
            "A lovely frog was added to your inventory.\n" +
            "The dialogue was ended.", game.processCommand("respond c"));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Linda: I always loved frogs, thank you!\n" +
            "The dialogue was ended.", game.processCommand("speak linda"));
    }

    @Test
    public void characterCashierTest() {
        assertEquals("Cashier: Hello, what do you want?\n" +
            "a. Buy sausage\n" +
            "b. Buy ham\n" +
            "c. Buy cutlet\n", game.processCommand("speak cashier"));
        assertEquals("Here is your sausage.\n" +
            "The dialogue was ended.", game.processCommand("respond a"));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Cashier: Hello, what do you want?\n" +
            "b. Buy ham\n" +
            "c. Buy cutlet\n", game.processCommand("speak cashier"));
        assertEquals("Here is your ham.\n" +
            "The dialogue was ended.", game.processCommand("respond b"));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Cashier: Hello, what do you want?\n" +
            "c. Buy cutlet\n", game.processCommand("speak cashier"));
        assertEquals("Here is your cutlet.\n" +
            "The dialogue was ended.", game.processCommand("respond c"));
        game.getGamePlan().setCurrentSpeaker(null);
        assertEquals("Cashier: Sorry, we're out of stock\n" +
            "The dialogue was ended.", game.processCommand("speak cashier"));
    }
}
