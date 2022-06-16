package cz.vse.adventura.logic;

/**
 * Class for a command "speak".
 * Used to interact with characters and a parrot.
 *
 * @author  Mykhailo Bubnov
 */
public class CommandSpeak implements ICommand {

    private static final String NAME = "speak";
    private static final String DESCRIPTION = " - used to enter a dialogue with characters";
    private GamePlan gamePlan;
    private Inventory inventory;

    public CommandSpeak(GamePlan gamePlan, Inventory inventory) {
        this.gamePlan = gamePlan;
        this.inventory = inventory;
    }


    /**
     * Execution of a command, gets the parameters, checks them for presence,
     * checks if another character isn't speaking now and is there are
     * valid characters to speak to.
     * @param parameters parameters number can differ from a command to command, can be zero.
     * @return character's response and player's response variants.
     */
    @Override
    public String executeCommand(String... parameters) {
        if (!(gamePlan.getCurrentSpeaker() == null)) {
            return "try a \"respond\" command";
        }
        if (parameters.length != 1) {
            return "Speak to who?";
        }

        String characterName = parameters[0];

        if (gamePlan.getCurrentRoom().getName().equals("paper store")
            && !inventory.contains("parrot drawing")
            && characterName.equals("parrot")) {
            return """
                Looks like he tries to draw himself, but he doesn't have
                fitting pencils to represent the beauty of his plumage.
                Look around to see if you can find those, suiting him.""";
        }

        ICharacter character = gamePlan.getCurrentRoom().getCharacter(characterName);

        if (character == null) {
            return "There isn't any character named " + characterName + " here.";
        }

        gamePlan.setCurrentSpeaker(character);
        return character.returnGreeting();
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
