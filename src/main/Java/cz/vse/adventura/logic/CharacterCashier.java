package cz.vse.adventura.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for a character cashier in albert.
 * Allows speaking and buying food from him.
 *
 * @author Mykhailo Bubnov
 */
public class CharacterCashier implements ICharacter{
    private final Inventory inventory;
    private final GamePlan gamePlan;
    private static final String NAME = "cashier";
    private final boolean dialogueAble = true;
    private final Map<String, String> responseVariants = new HashMap<>();

    public CharacterCashier(Inventory inventory, GamePlan gamePlan) {
        this.inventory = inventory;
        this.gamePlan = gamePlan;
        addResponseVariants();
    }


    /**
     * For a player's input, sells him a certain good and then vanishes it.
     * Ends a dialogue after a valid command.
     * @param playerLine player input, for example "a".
     * @return cashier's respond.
     */
    @Override
    public String getRespond(String playerLine) {
        String respond;
        switch (playerLine) {
            case ("a"): {
                inventory.insert(new Item("sausage", true, ""));
                respond = "Here is your sausage.";
                responseVariants.remove("a");
                break;
            }case ("b"): {
                inventory.insert(new Item("ham", true, ""));
                respond = "Here is your ham.";
                responseVariants.remove("b");
                break;
            }case ("c"): {
                inventory.insert(new Item("cutlet", true, ""));
                respond = "Here is your cutlet.";
                responseVariants.remove("c");
                break;
            }
            default: {
                respond = "try typing a b or c.";
                break;
            }
        }
        gamePlan.setCurrentSpeaker(null);
        return respond + "\nThe dialogue was ended.";
    }

    /**
     * Adds a valid responses to a response map.
     */
    public void addResponseVariants() {
        responseVariants.put("a", "Buy sausage");
        responseVariants.put("b", "Buy ham");
        responseVariants.put("c", "Buy cutlet");
    }

    /**
     * Arranges the variants so they could be printed.
     * @return printable variants.
     */
    public String printResponseVariants() {
        String text = "";
        for (String variantKey : responseVariants.keySet()) {
            text += variantKey + ". " + responseVariants.get(variantKey) + "\n";
        }
        return text;
    }

    /**
     * When all the items are bought, makes the character unspeakable.
     * @return a text that a cashier outputs when is spoken to.
     */
    @Override
    public String returnGreeting() {
        if (!(responseVariants.size() == 0)) {
            return "Cashier: Hello, what do you want?" + "\n" + printResponseVariants();
        }
        gamePlan.setCurrentSpeaker(null);
        return "Cashier: Sorry, we're out of stock" + "\nThe dialogue was ended.";
    }


    /**
     * getters for the character's name, description, a map of responses and if you can speak with them.
     */
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "A single cashier here is present, responsibly standing at the counter.";
    }

    @Override
    public Map<String, String> getResponseMap() {
        return responseVariants;
    }

    @Override
    public boolean getDialogueAble() {
        return dialogueAble;
    }
}
