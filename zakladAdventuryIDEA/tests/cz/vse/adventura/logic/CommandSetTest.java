package cz.vse.adventura.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Test class CommandSetTest is used to test the work of a CommandSet
 * 
 * @author    Mykhailo Bubnov
 */
public class CommandSetTest
{
    private Game game = new Game();
    private GamePlan gamePlan = game.getGamePlan();
    private Inventory inventory = game.getInventory();;
    private CommandCollect commandCollect;
    private CommandGo commandGo;
    private CommandInventory commandInventory;
    private CommandQuit commandQuit;
    private CommandRespond commandRespond;
    private CommandSpeak commandSpeak;
    private CommandUse commandUse;

    
    @Before
    public void setUp() {
        commandGo = new CommandGo(gamePlan, inventory, game);
        commandSpeak = new CommandSpeak(gamePlan, inventory);
        commandRespond = new CommandRespond(gamePlan);
        commandQuit = new CommandQuit(game);
        commandCollect = new CommandCollect(gamePlan, inventory);
        commandInventory = new CommandInventory(inventory);
        commandUse = new CommandUse(inventory, gamePlan);
    }

    @Test
    public void testAddCommands() {
        CommandSet commandSet = new CommandSet();
        commandSet.addCommand(commandGo);
        commandSet.addCommand(commandSpeak);
        commandSet.addCommand(commandRespond);
        commandSet.addCommand(commandQuit);
        commandSet.addCommand(commandCollect);
        commandSet.addCommand(commandInventory);
        commandSet.addCommand(commandUse);

        assertEquals(commandGo, commandSet.returnCommand("go"));
        assertEquals(commandSpeak, commandSet.returnCommand("speak"));
        assertEquals(commandRespond, commandSet.returnCommand("respond"));
        assertEquals(commandQuit, commandSet.returnCommand("quit"));
        assertEquals(commandCollect, commandSet.returnCommand("collect"));
        assertEquals(commandInventory, commandSet.returnCommand("inventory"));

    }
    @Test
    public void testIsValidCommand() {
        CommandSet commandSet = new CommandSet();
        commandSet.addCommand(commandQuit);
        commandSet.addCommand(commandGo);
        assertEquals(true, commandSet.isValidCommand("quit"));
        assertEquals(true, commandSet.isValidCommand("go"));
        assertEquals(false, commandSet.isValidCommand("help"));
        assertEquals(false, commandSet.isValidCommand("Go"));
    }
    
    @Test
    public void testCommandNames() {
        CommandSet commandSet = new CommandSet();
        commandSet.addCommand(commandQuit);
        commandSet.addCommand(commandGo);
        String names = commandSet.returnCommandName();
        assertEquals(true, names.contains("quit"));
        assertEquals(true, names.contains("go"));
        assertEquals(false, names.contains("help"));
        assertEquals(false, names.contains("Quit"));
    }
    
}
