package cz.vse.adventura.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for a character postwoman in a post office.
 * Implements speaking, changing variants and getting an animal from her.
 *
 * @author Mykhailo Bubnov
 */
public class CharacterPostwoman implements ICharacter {
    private final Inventory inventory;
    private final GamePlan gamePlan;
    private static final String NAME = "linda";
    private boolean dialogueAble = true;
    private String methodOfTaking = "";
    private boolean wasMet = false;
    private final Map<String, String> responseVariants = new HashMap<>();
    Item frog = new Item("lovely frog", true, "");

    public CharacterPostwoman(Inventory inventory, GamePlan gamePlan) {
        this.inventory = inventory;
        this.gamePlan = gamePlan;
        addResponseVariants();
    }

    /**
     * Adds a valid responses to a response map.
     */
    public void addResponseVariants() {
        responseVariants.put("a", "Attach her with a parrot!(not available)");
        responseVariants.put("b", "\"I need your help, haven't you seen a frog here?\"");
        responseVariants.put("c", "Present her with a plushie!(not available)");
    }

    /**
     * Makes the response variants of a player printable,
     * checks if player has certain items and opens dialogue variants.
     * @return printable response variants.
     */
    public String printResponseVariants() {
        if (!(inventory == null) && inventory.show().contains("plushie")) {

             responseVariants.put("c", "Present her with a plushie!");
        } else if (!(inventory == null) && inventory.show().contains("fluffy parrot")) {
            responseVariants.put("a", "Attack her with a parrot!");
        }
        StringBuilder outText = new StringBuilder();
        for (String variantKey : responseVariants.keySet()) {
            outText.append(variantKey).append(". ").append(responseVariants.get(variantKey)).append("\n");
        }
        return outText.toString();
    }

    /**
     * allows player to choose between 3 variants of a response to greeting.
     * @param playerLine arguments a player has entered.
     * @return respond of a character/environment.
     */
    public String getRespond(String playerLine) {
        String respond = "";
        switch (playerLine) {
            case ("a") -> respond = parrotVariant();
            case ("b") -> respond = "Linda: Have you heard me right? We're on a break, and I hadn't seen any stupid frogs here!";
            case ("c") -> respond = plushieVariant();
        }
        gamePlan.setCurrentSpeaker(null);
        return respond + "\nThe dialogue was ended.";
    }

    /**
     * generates a response when a variant "a" is used.
     * Gives player a frog.
     * Makes character unspeakable.
     * @return respond of a character
     */
    private String parrotVariant() {
        if (inventory.contains("fluffy parrot")){
            dialogueAble = false;
            inventory.insert(frog);
            methodOfTaking = "parrot";
            return """
                        The parrot proceeds to attack her by your command.
                        He bites her and blows her hair.
                        Linda runs away, loudly screaming in the process.
                        She drops the frog and you pick it up.
                        A lovely frog was added to your inventory.
                        """;
        }
        return  "If only you had your parrot with you...";
    }

    /**
     * generates a response when a variant "c" is used.
     * Gives player a frog.
     * Makes character unspeakable.
     * @return respond of a character
     */
    private String plushieVariant() {
        if (inventory.contains("plushie")) {
            inventory.remove("plushie");
            dialogueAble = false;
            inventory.insert(frog);
            methodOfTaking = "plushie";
            return  """
                        She blushed and mumbled with a smile on her face:
                        Linda: you're such a sweet young man, you wanted that frog? Take it.
                        A lovely frog was added to your inventory.""";
        }
        return  "You don't have any gifts with you. " +
            "\nMaybe you should check out the flying tiger...";
    }

    /**
     * Used for generating a greeting of a character, when a dialogue is begun.
     * Depends on parameters.
     * @return String greet of a character.
     */
    public String returnGreeting() {
        String greetingBeforeMet = "Linda: We are on a brake. You'd rather not bother me right now." +
            "\n*You knew that they had their break 2 hours ago*";
        String greetingAfterMet = "Linda: I said we're on a brake, go away!";
        String greetingAfterPlushie = "Linda: I always loved frogs, thank you!";
        String greetingAfterParrot = "Linda isn't present.";
        if (!wasMet) {
            wasMet = true;
            return greetingBeforeMet + "\n" + printResponseVariants();
        } else if (dialogueAble) {
                return greetingAfterMet + "\n" + printResponseVariants();
        } else {
            gamePlan.setCurrentSpeaker(null);
            if (methodOfTaking.equals("parrot")) {
                return greetingAfterParrot + "\nThe dialogue was ended.";
            }
            return greetingAfterPlushie + "\nThe dialogue was ended.";
        }
    }

    @Override
    public String getDescription() {
        if (methodOfTaking.equals("plushie")) return "Linda is still here but surprisingly not angry anymore.";
        if (methodOfTaking.equals("parrot")) return "Linda is nowhere to be found";
        if (wasMet) return "Linda is still here and is still angry.";

        return "An overweight, angrily-looking woman stands at the only working window." +
            "\nYou see her badge, it reads \"Linda\"";
    }

    @Override
    public Map<String, String> getResponseMap() {
        return responseVariants;
    }


    @Override
    public String getName() {
        return NAME;
    }


}
