package cz.vse.adventura.logic;

import cz.vse.adventura.uiText.TextInterface;
import java.util.Objects;

/**
 * class of the command "Use"
 * Utilized for using an item from the inventory.
 *
 * @author  Mykhailo Bubnov
 */
public class CommandUse implements ICommand {
    private static final String NAME = "use";
    private static final String DESCRIPTION = "- used for every sort of manipulation with an item.";
    private final Inventory inventory;
    private final GamePlan gamePlan;
    private int rightPencilCount = 0;

    public CommandUse(Inventory inventory, GamePlan gamePlan) {
        this.inventory = inventory;
        this.gamePlan = gamePlan;
    }

    /**
     * Execution of a command, gets the parameters, checks them for presence,
     * existence of items in an inventory and routes the program to individual commands
     * for different parameters
     * @param parameters parameters number can differ from a command to command, can be zero.
     * @return String with text response to parameters following the manipulation with the game environment.
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "check your inventory for the items you have.";
        }

        StringBuilder itemName = new StringBuilder();
        for (String word : parameters) {
            itemName.append(word).append(" ");
        }

        itemName = new StringBuilder(itemName.toString().trim());
        Item item = inventory.get(itemName.toString());
        if (item == null) {
            return "you don't have that item.";
        }

        return identifyItem(itemName.toString());
    }

    private String identifyItem(String itemName) {
        switch (itemName) {
            case ("cat manual"): return """
                    To easily catch a cat you'll need a transport box and a ham.
                    Put a piece of ham gently into the transport box,
                    walk away and wait till your prey is caught.""";
            case ("transport box"): return setTransportBox();
            case ("ham"):
            case ("sausage"):
            case ("cutlet"): return createTrap(itemName);
            case ("parrot drawing"): return drawParrot();
            default:
                if (itemName.matches(".* pencil")) {
                    return usePencil(itemName);
                }
        }
        return "You can't use that item.";
    }
    /**
     * Used, if a command is executed with the items "**** pencil".
     * Looks for a validity of use and performs a count of right uses.
     * If it is higher than 1, proceeds to next commands.
     * @param pencilName - String name of a pencil in format "yellow pencil"ю
     * @return String with text response to parameters following the manipulation with the game environment.
     */
    private String usePencil(String pencilName) {
        inventory.remove(pencilName);
        if (pencilName.equals("purple pencil") || pencilName.equals("green pencil")) {
            rightPencilCount++;
            if (rightPencilCount > 1) {
                inventory.insert(new Item("fluffy parrot", true, ""));
                inventory.insert(new Item("parrot drawing", true, ""));
                removeUnusedPencils();
                gamePlan.getPaperStore().setDescription(
                    ".\nA store full of various pens, pencils, notebooks and other things that can interest a curious bird");
                return "You gave a " + pencilName + " to the parrot.\n" + """
                    Parrot gladly clapped with his wings and finished the drawing
                    It looks just like the artist, you should have a look!
                    Now parrot is with you.
                    New item: parrot drawing""";
            }
            return "You gave a " + pencilName + " to the parrot."+
                "\nParrot proceeds to draw half of the missing space, but he still wants another one.";
        }
        return "You gave a " + pencilName + " to the parrot."+
        "\nParrot isn't interested in that color, try another.";
    }

    /**
     * Clears pencils that wasn't used from inventory after the events with parrot(look usePencil).
     */
    private void removeUnusedPencils() {
        inventory.remove("red pencil");
        inventory.remove("yellow pencil");
        inventory.remove("blue pencil");
    }

    /**
     * Used to set the Item transport box in a specific room albert,
     * with a following conversion to a cat trap.
     *
     * @return String after setting a transport box in a right room/not setting.
     */
    public String setTransportBox() {
        if (!(Objects.equals(gamePlan.getCurrentRoom().getName(), "albert"))) {
            return  "It doesn't has much use here.";
        }
        Item transportBox = inventory.get("transport box");
        transportBox.setMovable(false);
        gamePlan.getCurrentRoom().setItem(transportBox);
        inventory.remove("transport box");
        return  "Item transport box was removed from inventory.\n" +
            "A good beginning for a cat-trap, but how do you lure him inside this box?";
    }

    /**
     * Creates an item consisting of transport box placed previously(see setTransportBox)
     * and a meat(ham, sausage, cutlet).
     * Then the method CommandGo.checkTransportBox check if it was placed right
     * and when it should trigger.
     * @param itemName consumes the name of an item to put into a trap
     * @return result of construction.
     */
    public String createTrap(String itemName) {
        if (!(Objects.equals(gamePlan.getCurrentRoom().getName(), "albert"))) {
            return "Maybe you should use it in a different place.";
        } else if (!gamePlan.getCurrentRoom().containsItem("transport box")) {
            return  "Maybe you should place something in advance to insert " + itemName.trim() + " into it.";
        } else {
            gamePlan.getCurrentRoom().deleteItem("transport box");
            inventory.remove(itemName);
            gamePlan.itemInAlbert = new Item("transport box with a " + itemName, false, "");
            gamePlan.getCurrentRoom().setItem(gamePlan.itemInAlbert);
            return  "You have set up a trap for a cat! Now you'll need to walk away and wait.";
        }

    }

    /**
     * A method for drawing a parrot ASCII-art.
     * @return ASCII-art of a parrot.
     */
    public String drawParrot() {
        TextInterface.drawParrot();
        return "\n\nA work of art indeed.";
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
