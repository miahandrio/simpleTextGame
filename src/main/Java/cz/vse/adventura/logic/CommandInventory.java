package cz.vse.adventura.logic;

/**
 * Class CommandInventory
 *
 * Used for creation of a command "inventory",
 * which is used to look what the player has with himself
 *
 * @author Mykhailo Bubnov
 */
public class CommandInventory implements ICommand {

    private static final String NAME = "inventory";
    private static final String DESCRIPTION = " - used for viewing what's in your inventory";
    private Inventory inventory;


    public CommandInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Checks for the items in inventory, if none, returns that there are none,
     * if present, prints out the items.
     * @param parameters parameters for a certain command.
     * @return contents of an inventory.
     */
    @Override
    public String executeCommand(String... parameters) {

        if (parameters.length > 0 ) {
            return "You can't do anything with inventory\n" +
                "other than looking into it";

        } else {

            if (inventory.show().size() == 0) {
                return "You have no items currently";
            } else {
                String returnString = "You have these items: ";

                for (String currentItem : inventory.show()) {
                    returnString += currentItem + "   ";
                }
                return returnString;

            }
        }
    }


    /**
     * Getters for a command's name and description.
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
