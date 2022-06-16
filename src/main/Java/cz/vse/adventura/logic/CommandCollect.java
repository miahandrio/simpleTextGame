package cz.vse.adventura.logic;

/**
 * Class of the command "collect".
 * Used to collect the item form a room and to put it into an inventory.
 *
 * @author  Mykhailo Bubnov
 */
public class CommandCollect implements ICommand {

    private static final String NAME = "collect";
    private static final String DESCRIPTION = " - used for collecting items";
    private final GamePlan gamePlan;
    private final Inventory inventory;

    public CommandCollect(GamePlan gamePlan, Inventory inventory) {
        this.gamePlan = gamePlan;
        this.inventory = inventory;
    }

    /**
     * Command execution.
     * Checks for a valid parameters and performs a  collection for all items except pencils.
     * @param parameters parameters for a certain command.
     * @return description of item.
     */
    @Override
    public String executeCommand(String... parameters) {
        //Check phase
        if (parameters.length == 0) {
            // If the second word is missing, then....
            return "Collect what?";
        }

        StringBuilder itemName = new StringBuilder();
        for (String word : parameters) {
            itemName.append(word).append(" ");
        }
        String itemNameStr = itemName.toString().trim();
        Item item = gamePlan.getCurrentRoom().returnItem(itemNameStr);

        if (item == null) {
            return "Seems that such item is nowhere to be found.";
        } else if (!item.getMovable()) {
            return "Can't collect that item.";
        }

        //collect phase
        gamePlan.getCurrentRoom().deleteItem(itemNameStr);
        if (itemNameStr.equals("pencils")) {
            return collectPencils();
        }
        inventory.insert(item);
        if (item.getDescription().equals("")) {
            return "Item " + itemNameStr + " was collected.";
        }
        return "Item " + itemNameStr + " was collected." + "\n" + item.getDescription();
    }

    /**
     * Collection for an item "pencils"
     * Collects one item pencils and inserts 5 pencils into an inventory.
     * @return notification that the pencils were collected
     */
    private String collectPencils() {
        inventory.insert(new Item("blue pencil", true, ""));
        inventory.insert(new Item("yellow pencil", true, ""));
        inventory.insert(new Item("red pencil", true, ""));
        inventory.insert(new Item("green pencil", true, ""));
        inventory.insert(new Item("purple pencil", true, ""));
        return "Blue, yellow, red, green and purple pencils were collected";
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
