package cz.vse.adventura.logic;

import java.util.Map;

/**
 *  Class CommandHelp implements a help command.
 *  
 *@author     Mykhailo Bubnov
 *  
 */
class CommandHelp implements ICommand {
    
    private static final String NAME = "help";
    private static final String DESCRIPTION = " - use if you got lost";
    private CommandSet validCommands;

     /**
    *  Class constructor
    *  
    *  @param validCommands a list of commands that are valid.
    */    
    public CommandHelp(CommandSet validCommands) {
        this.validCommands = validCommands;
    }
    
    /**
     *  Method for returning valid commands list and information about the game.
     *  
     *  @return help for the game
     */
    @Override
    public String executeCommand(String... parameters) {
        return "\n"
            + "Your objective is to find all the missing animals"
            + "\n check all the areas and pay attention to details!"
            + "\n"
            + "You can enter these commands:\n"
            + generateDescription();
    }


    /**
     * Generates a table with commands and their description.
     */
    public String generateDescription() {
        String description = "";
        for (Map.Entry<String, ICommand> entry : validCommands.getCommandMap().entrySet()) {
            description += entry.getKey() + entry.getValue().getDescription() + "\n";
        }
        return description;
    }



    /**
     * getters for a command's name and description.
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
