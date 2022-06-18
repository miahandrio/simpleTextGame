/* File is saved in UTF-8 encoding. */
package cz.vse.adventura;



import cz.vse.adventura.logic.Game;
import cz.vse.adventura.logic.IGame;
import cz.vse.adventura.uiText.TextInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

        //An if tree for a game, loaded form another file
        if (args[0].equals("load")) {
            if (args.length != 2) {
                System.out.println("Enter a valid one-word name of your save.");
            } else {
                loadGameCase(args[1], ui);
            }
        }

        else {
            // An if tree for a new game with new save file
            if (args.length != 1) {
                System.out.println("Enter a save name of this session with one word after the launching command.");
            } else {
                try {
                    ui.playToSave(args[0]);
                } catch (IOException e) {
                    System.out.println("File cannot be opened");
                }
            }
        }
    }

    private static void loadGameCase(String loadFileName, TextInterface ui) {

        File saveFile = new File("saves/" + loadFileName+ ".txt");
        if (!saveFile.exists()) {

            File[] saveFilesArray = new File("saves/").listFiles();

            if (saveFilesArray == null) {
                System.out.println("You have no save files currently");
            } else {

                StringBuilder existingFiles = new StringBuilder();

                for (File currentFile : saveFilesArray) {
                    String fileName = currentFile.getName().replace(".txt","");
                    existingFiles.append(fileName).append("\n");
                }


                System.out.println("enter a valid save name.\n" +
                    "Existing file names are:\n"
                    + existingFiles);
            }
        } else {
            try {
                ui.playFromSave(saveFile);
            }  catch (FileNotFoundException e) {
                System.out.println("File cannot be opened");
            } catch (IOException e) {
                System.out.println("Input output error");
            }
        }
    }
}
