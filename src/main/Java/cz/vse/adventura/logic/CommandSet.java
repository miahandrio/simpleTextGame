package cz.vse.adventura.logic;

import java.util.HashMap;
import java.util.Map;

/**
 *  Class CommandSet contains valid commands.
 *  Is used for command identification and
 *  linking a command to its keyword.
 *  Each new command  must be added to list with a method addCommand.
 *
 *@author   Mykhailo Bubnov
 */
class CommandSet {
    // map for containing commands
    private final Map<String, ICommand> commandMap;
    
   
    
    /**
     * Constructor
     */
    public CommandSet() {
        commandMap = new HashMap<>();
    }
    
    
    /**
     * Adds new command
     *
     *@param  command  Instance of a class implementing ICommand
     */
    public void addCommand(ICommand command) {
        commandMap.put(command.getName(),command);
    }
    
    /**
     * Returns an instance of a command implementing ICommand,
     * which executes a command in a parameter.
     *
     *@param  keyword  String of a command that player wants to execute.
     *@return          instance of a command that will be executed.
     */
    public ICommand returnCommand(String keyword) {
        return commandMap.getOrDefault(keyword, null);
    }

    /**
     * Controls, if entered keyword has a valid command attached
     *
     *@param  keyword  String that will be tested if it's valid
     *@return          returns true if a keyword is valid
     */
    public boolean isValidCommand(String keyword) {
        return commandMap.containsKey(keyword);
    }

    /**
     *  Returns a list of valid keywords.
     *  
     *  @return     String that contains a list of valid keywords.
     */
    public String returnCommandName() {
        StringBuilder list = new StringBuilder();
        for (String keyword : commandMap.keySet()){
            list.append(keyword).append(" ");
        }
        return list.toString();
    }

    /**
     * getter for a map of commands
     */
    public Map<String, ICommand> getCommandMap() {
        return commandMap;
    }
    
}

