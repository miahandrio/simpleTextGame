package cz.vse.adventura.logic;

/**
 *  Class CommandGo implements a command for moving between spaces.
 *  
 *@author     Bubnov Mykhailo
 */
class CommandGo implements ICommand {
    private static final String NAME = "go";
    private static final String DESCRIPTION = " - used for moving between locations";
    private final GamePlan gamePlan;
    private final Inventory inventory;
    private final Game game;
    
    /**
    *  CLass constructor
    *  
    *  @param gamePlan a plan of a game in which there will be a movement.
    */    
    public CommandGo(GamePlan gamePlan, Inventory inventory, Game game) {
        this.gamePlan = gamePlan;
        this.inventory = inventory;
        this.game = game;
    }

    /**
     *  Executes command "go", will try to go to a space written. If a space
     *  exists, it will be entered. If a space doesn't exist, will write out an error.
     *
     *@param parameters - as a parameter accepts a name of a neighbouring room.
     *@return message to show to a player.
     */ 
    @Override
    public String executeCommand(String... parameters) {
        //Check for parameters
        if (parameters.length == 0) {
            // If the second word is missing, then....
            return "Where do you wanna go?";
        }

        // creating a room name
        StringBuilder direction = new StringBuilder();
        for (String word : parameters) {
            direction.append(word).append(" ");
        }
        Room neighbouringRoom = gamePlan.getCurrentRoom().returnNeighbouringRoom(direction.toString().trim());

        //Checking if a room can be accessed
        if (neighbouringRoom == null) {
            return "You can't go there";
        }
        //Changing rooms
        else {
            gamePlan.setCurrentRoom(neighbouringRoom);
            gamePlan.setCurrentSpeaker(null);
            checkForAnimals();
            checkTransportBox();
            return neighbouringRoom.longDescription();
        }
    }

    /**
     * Performs a check when entering a room, if a game can be won.
     * If all animals are present and the room is a pet store, a player wins.
     */
    private void checkForAnimals() {
        if (inventory.contains("fluffy parrot") &&
        inventory.contains("lovely frog") &&
        inventory.contains("charming cat") &&
        gamePlan.getCurrentRoom().getName().equals("pet store")) {
            game.setGameEnd(true);
            game.setWin(true);
        }
    }

    /**
     * When the cat trap is placed, checks that it was placed right
     * and that the player isn't present in a room for 1 move.
     */
    private void checkTransportBox() {
        if (!(gamePlan.itemInAlbert == null) && !(gamePlan.itemInAlbert.getName().equals("transport box"))) {
            for (int i = 0; i<1; i++) {
                if (gamePlan.getCurrentRoom().equals(gamePlan.getAlbert())) {
                    i = 0;
                }
            }
            //Trap was successful
            if (gamePlan.itemInAlbert.getName().equals("transport box with a ham")) {
                gamePlan.getAlbert().deleteItem(gamePlan.itemInAlbert.getName());
                gamePlan.getAlbert().setItem(new Item("charming cat", true, "Congratulations! you've caught that charming cat!."));
            } else {
                //Trap wasn't successful
                gamePlan.getAlbert().deleteItem(gamePlan.itemInAlbert.getName());
                gamePlan.getAlbert().setItem(new Item("transport box", true, "It seems that you've got to use different meat to catch the cat"));
            }
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
