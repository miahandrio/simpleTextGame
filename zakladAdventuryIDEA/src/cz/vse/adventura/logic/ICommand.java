package cz.vse.adventura.logic;

/**
 *  A class implementing this interface will process an individual command.
 *  
 *@author     Mykhailo Bubnov
 *  
 */
interface ICommand {
	
	/**
     *  Method for executing commands.
     *  
     *  @param parameters parameters for a certain command.
     *                    Their number can differ from a command to command, can be zero.
     *  
     */
    String executeCommand(String... parameters);

    /**
     * getters for a command's name and description.
     */
	String getName();

    String getDescription();
	
}
