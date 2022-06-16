package cz.vse.adventura.logic;


/**
 *  Interface that a game must implement, establishes user interface.
 *
 *@author     Mykhailo Bubnov
 */
public interface IGame
{
    /**
     *  Returns greeting message for the player.
     *  
     *  @return  returns String which is going to be printed.
     */
    String returnGreeting();

    /**
     *  Returns epilogue message for the player.
     *
     *  @return  returns String which is going to be printed.
     */
    String returnEpilogue();
    
    /** 
     * Returns information if the game is ended or still running.
     * @return   returns true, if the game is ended
     */
     boolean gameEnd();
     
      /**
     *  Method processes input, separates the command and the parameters.
     *  Then tests, if the command is a keyword. For example go.
     *  If yes, processes of the command.
     *
     *@param  line  - a text that user entered.
     *@return      returns String which is going to be printed.
     */
     String processCommand(String line);
   
    
     /**
     *  Method returns a link to a game plan, primary used in unit tests
     *  for getting the current room.
     *  
     *  @return     link to a game plan
     */
     GamePlan getGamePlan();
}
