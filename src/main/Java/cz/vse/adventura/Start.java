/* File is saved in UTF-8 encoding. */
package cz.vse.adventura;



import cz.vse.adventura.logic.Game;
import cz.vse.adventura.logic.IGame;
import cz.vse.adventura.uiText.TextInterface;

/*******************************************************************************
 * Class start - main method of the project.
 */
public class Start
{
    /***************************************************************************
     * The app is started with a method below
     *
     * @param args parameters of command prompt
     */
    public static void main(String[] args)
    {
        
        IGame game = new Game();
        TextInterface ui = new TextInterface(game);
        ui.play();
    }
}
