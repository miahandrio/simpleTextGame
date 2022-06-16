package cz.vse.adventura.logic;

/**
 *  Class CommandHelp implements a help command.
 *  
 *@author     Mykhailo Bubnov
 *  
 */
class CommandHelp implements ICommand {
    
    private static final String NAME = "help";
    private static final String DESCRIPTION = " - use if you got lost";
    private final CommandSet validCommands;

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
        StringBuilder description = new StringBuilder();
        for (ICommand command : validCommands.getCommandMap().values()) {
            description
                .append(command.getName())
                .append(command.getDescription())
                .append("\n");
        }
        return description.toString();
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
