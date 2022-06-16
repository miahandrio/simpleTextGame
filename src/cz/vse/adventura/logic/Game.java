package cz.vse.adventura.logic;

/**
 *  Class Game represents the logic of the program.
 * 
 *  It is the main class for the logic of the program.
 *  This class creates the instance of a GamePlan, witch initialises the spaces of the game,
 *  creates a lis of valid commands and the instances of individual commands.
 *  Prints greeting and the epilogue messages.
 *  Processes commands from the user.
 *
 *@author     Mykhailo Bubnov
 */

public class Game implements IGame {
    private CommandSet validCommands;
    private GamePlan gamePlan;
    private Inventory inventory;
    private boolean gameEnd = false;
    private boolean win = false;

    /**
     *  Creates the game, initialises spaces and valid Commands.
     **/
    public Game() {
        inventory = new Inventory();
        gamePlan = new GamePlan(inventory);
        validCommands = new CommandSet();
        validCommands.addCommand(new CommandHelp(validCommands));
        validCommands.addCommand(new CommandGo(gamePlan, inventory, this));
        validCommands.addCommand(new CommandSpeak(gamePlan, inventory));
        validCommands.addCommand(new CommandRespond(gamePlan));
        validCommands.addCommand(new CommandQuit(this));
        validCommands.addCommand(new CommandCollect(gamePlan, inventory));
        validCommands.addCommand(new CommandInventory(inventory));
        validCommands.addCommand(new CommandUse(inventory, gamePlan));
    }

    /**
     *  Returns greeting message for the player.
     */
    public String returnGreeting() {
        return "---------------------------------------------------------\n" +
               "---------------------------------------------------------\n" +
               "---------------------------------------------------------\n" +
               "Hello!\n" +
               "You are an employee of a Pet store\n" +
               "Your lovely frog, charming cat and fluffy parrot had gotten out and ran away\n" +
               "Now you need to find and return them to the store safely.\n\n" +
               "Write \"help\" if you don't know what to do.\n" +
               gamePlan.getCurrentRoom().longDescription();
    }
    
    /**
     *  Returns epilogue message for the player.
     *  if boolean win is true -> you have won.
     *  If it is false -> you have quit.
     */
    public String returnEpilogue() {
        if (win){
            return """
                \nCongratulations! You have collected all the animals and returned them.
                You're gonna continue living with your favourite animals side to side!
                At least, until you're fired.
                Happy end!""";
        }
        return "\nThank you for your time. Goodbye.";
    }
    
    /** 
     * Returns true if the game is ended.
     */
     public boolean gameEnd() {
        return gameEnd;
    }

    /**
     * Returns true if the game was won.
     */
    public boolean isWin() {
        return win;
    }

    /**
     *  Method processes input, separates the command and the parameters.
     *  Then tests, if the command is a keyword. For example go.
     *  If yes, processes of the command.
     *
     *@param  line  - a text that user entered.
     *@return      returns String which is going to be printed.
     */
     public String processCommand(String line) {
        String [] words = line.split("[ \t]+");
        String commandWord = words[0].toLowerCase();
        String [] parameters = new String[words.length-1];
        for(int i=0 ;i<parameters.length;i++){
           	parameters[i]= words[i+1].toLowerCase();
        }
        String returnedText;
        if (validCommands.isValidCommand(commandWord)) {
            ICommand command = validCommands.returnCommand(commandWord);
            returnedText = command.executeCommand(parameters);
        }
        else {
            returnedText="This is not a valid command";
        }
        return returnedText;
    }
    
    
     /**
     *  Setter that ends the game. Is used by CommandQuit.
     *  
     *  @param  gameEnd  true = game over, false = game continues.
     */
    void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     *  Method returns a link to a game plan, primary used in unit tests
     *  for getting the current room.
     *
     *  @return     link to a game plan
     */
     public GamePlan getGamePlan(){
        return gamePlan;
     }

    public Inventory getInventory() {
        return inventory;
    }
}

