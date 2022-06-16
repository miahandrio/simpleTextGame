package cz.vse.adventura.logic;

/**
 *  Class CommandEnd creates a command to end a game
 *  
 *@author     Mykhailo Bubnov
 *  
 */

class CommandQuit implements ICommand {

    private static final String NAME = "quit";
    private static final String DESCRIPTION = " - used for quitting the game";
    private final Game game;

    /**
     *  Class constructor
     *  
     *  @param game takes a game that will be ended.
     */    
    public CommandQuit(Game game) {
        this.game = game;
    }

    /**
     * If command has only one word "quit", the game will end(method setGameEnd(true))
     * otherwise will continue, entering "quit a" for example.
     * 
     * @return message, which is shown to the player.
     */

    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length > 0) {
            return "Quit what? You don't need a second word";
        }
        else {
            game.setGameEnd(true);
            return "The game was quit";
        }
    }

    /**
     *  Getters for the commands name (word that a player is printing for calling a command)
     *  and a command description.
     *
     */
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
