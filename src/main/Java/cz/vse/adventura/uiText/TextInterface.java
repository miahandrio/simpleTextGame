package cz.vse.adventura.uiText;


import java.io.*;
import java.util.Locale;
import java.util.Scanner;

import cz.vse.adventura.logic.IGame;

/**
 *  Class TextInterface
 * 
 *  This is user's interface of the application
 *  This class creates instances of the class Game, witch contains the logic of the program.
 *  Reads users input sends it to the logic and prints responds.
 *  
 *  
 *
 *@author     Mykhailo Bubnov
 */

public class TextInterface {
    private final IGame game;
    private static int writeSpeed = 20;

    /**
     *  Creates a game class
     */
    public TextInterface(IGame game) {
        this.game = game;
    }

    /**
     *  Main method of the game. Prints greeting text and then repeats reading and processing of
     *  users input until the game is finished (while method gameEnd isn't returning true).
     *  In the end prints epilogue.
     */
    public void playToSave(String saveFileName) throws IOException {
        PrintWriter savePrint = new PrintWriter(new FileWriter("saves/" + saveFileName + ".txt"));

            typewrite(game.returnGreeting());
            // basic game cycle, repeatedly reads and processes commands.

            while (!game.gameEnd()) {
                String line = readLine();
                savePrint.println(line);
                typewrite(game.processCommand(line));
            }
            savePrint.close();

            typewrite(game.returnEpilogue());
    }

    public void playFromSave(File saveFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(saveFile));
        PrintWriter savePrint = new PrintWriter(new FileWriter(saveFile, true));

        writeSpeed = 0;

        String saveLine = reader.readLine();
        while (saveLine != null) {
            saveLine = saveLine.replace("quit", "");
            typewrite("\n> " + saveLine + "\n");
            typewrite(game.processCommand(saveLine));
            saveLine = reader.readLine();
        }
        writeSpeed = 20;

        while (!game.gameEnd()) {
            String line = readLine();
            savePrint.println(line);
            typewrite(game.processCommand(line));
        }

        if (game.isWin()) {
            saveFile.delete();
        }

        typewrite(game.returnEpilogue());
    }

    /**
     *  Method that reads command from the command line
     *
     *@return    returns read command as a String
     */
    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        typewrite("\n\n> ");
        return scanner.nextLine();
    }



    /**
     * Method that creates a typewriter effect for the text.
     * Responsible for printing the text out.
     *
     * @param text - gets a text from the readLine method
     */
    private static void typewrite(String text) {
        for(int i = 0; i < text.length(); i++){
            System.out.printf("%c", text.charAt(i));
            try{
                Thread.sleep(writeSpeed);//0.02s pause between characters
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }


    public static void drawParrot() {
        writeSpeed = 3;
        typewrite("""
            
                          ██████████
                        ██░░░░░░░░░░████
                      ██░░░░░░░░░░░░░░░░██
                    ██░░░░░░░░░░░░░░░░░░░░██
                  ██░░░░░░██░░░░░░░░░░░░████
                ██░░░░░░░░██░░████████░░██░░██
                ██░░░░░░░░░░░░██░░░░░░██░░░░██
              ████░░░░░░░░░░░░██░░░░░░██░░░░░░██
              ██░░░░░░░░░░░░░░██░░░░░░██░░░░░░░░██
            ██░░░░░░░░░░░░░░░░██░░░░████░░░░░░░░██
            ██░░░░░░░░░░░░░░░░░░██░░██░░░░░░░░░░██
            ██░░░░░░░░░░░░░░░░░░██░░██░░░░░░░░░░██
              ██░░░░░░░░░░░░░░░░░░██░░░░░░░░░░░░██
                ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
                ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░████
                ████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
                ██░░██████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
                ██░░░░░░░░██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
                ████████████████████████████████████████████████""");
        writeSpeed = 20;
    }




}
