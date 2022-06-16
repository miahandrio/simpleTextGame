package cz.vse.adventura.logic;


/**
 * Class for a response command.
 * Used to choose an answer in a dialogue.
 *
 * @author  Mykhailo Bubnov
 */
public class CommandRespond implements ICommand {

    private static final String NAME = "respond";
    private static final String DESCRIPTION = " - used in dialogue to answer";
    private GamePlan plan;

    public CommandRespond(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Checks is there is an open dialogue and if a character can be spoken to.
     *
     * @param parameters parameters for a certain command.
     *                   Their number can differ from a command to command, can be zero.
     * @return String response of a character or changes in environment.
     */
    @Override
    public String executeCommand(String... parameters) {
        ICharacter speaker = plan.getCurrentSpeaker();
        if (speaker == null) {
            return "You aren't speaking right now.";
        }

        if (!speaker.getDialogueAble()) {
            return "you already spoke to that character.";
        }
        if (parameters.length == 0) {
            return "respond what?";
        }
        if (!speaker.getResponseMap().containsKey(parameters[0])) {
            return "enter a valid answer.";
        } else {
            return speaker.getRespond(parameters[0]);
        }
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
