package cz.vse.adventura.logic;


/**
 *  Class GamePlan - class representing game map and game state.
 * 
 *  This class initialises all elements of the game:
 *  creates all the spaces,
 *  connects them with exits
 *  and memorises room, where the player currently is situated.
 *
 *@author     Mykhailo Bubnov
 */
public class GamePlan {
    
    private Room currentRoom;
    private Room albertRoom;
    private Room paperStoreRoom;
    public Item itemInAlbert;
    private ICharacter currentSpeaker;
    private final Inventory inventory;
    
     /**
     *  Constructor that calls createGameRooms method that creates game spaces and connects them with exits.
     */
    public GamePlan(Inventory inventory) {
        this.inventory = inventory;
        createGameRooms();
    }
    /**
     *  Creates items and spaces.
     *  Connect spaces with exits.
     *  Sets petStore as a default room.
     */
    private void createGameRooms() {
        // creation of rooms
        Room petStore = new Room("pet store",", \nYour lovely workplace looks quite empty, after all the animals had gotten out");
        Room mainCorridor = new Room("main corridor",". \nFrom here, you can go to any store");
        Room postOffice = new Room("post office",". \nThere's not much people here in this time of day");
        Room albert = new Room("albert",". \nA big hypermarket, a paradise for predators like humans or ... cats");
        Room flyingTiger = new Room("flying tiger", """
            .\s
            A store full of different presents that can melt even the coldest hearts
            It seems like today they have a promotion action, you could take a little frog plushie completely for free""");
        Room paperStore = new Room("paper store", """
            .\s
            A store full of various pens, pencils, notebooks and other things that can interest a curious bird.
            You notice a wryly-drawn head of a parrot. The artist surely wasn't happy with his self-portrait.
            And here's the artist, our magnificent purple and green parrot, seems like he needs your help.
            Maybe try communicating with him""");

        // Creation of characters
        CharacterPostwoman postwoman = new CharacterPostwoman(inventory, this);
        CharacterCashier cashier = new CharacterCashier(inventory, this);

        // Creation of items
        Item catManual = new Item("cat manual", true, "A short manual with a few pages about how to catch a wild cat.");
        Item transportBox = new Item("transport box", true, "A box for transporting cats, fitting just right for the lost one.");
        Item plushie = new Item("plushie", true, "A little frog plushie, indeed very similar to the frog that ran away.");
        Item pencils = new Item("pencils", true, "");

        // Assigning of exits
        petStore.setExit(mainCorridor);
        mainCorridor.setExit(postOffice);
        mainCorridor.setExit(albert);
        mainCorridor.setExit(flyingTiger);
        mainCorridor.setExit(petStore);
        mainCorridor.setExit(paperStore);
        postOffice.setExit(mainCorridor);
        albert.setExit(mainCorridor);
        flyingTiger.setExit(mainCorridor);
        paperStore.setExit(mainCorridor);

        // Assigning of items
        petStore.setItem(transportBox);
        mainCorridor.setItem(catManual);
        flyingTiger.setItem(plushie);
        paperStore.setItem(pencils);

        //Assigning of characters
        postOffice.addCharacter(postwoman);
        albert.addCharacter(cashier);


        currentRoom = petStore;  // Default room setting
        albertRoom = albert; // Needed to check a state of items in that room, when the player isn't present
        paperStoreRoom = paperStore; // Needed to change the description of room when exited
    }
    
    /**
     *  Getters for the room that a player is currently in,
     *  character that a player is speaking to
     *  and an instance of albert.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public ICharacter getCurrentSpeaker() {
        return currentSpeaker;
    }
    public Room getAlbert() {
        return albertRoom; //used to check if transport box has caught the cat. see: commandGo.checkTransportBox()
    }
    public Room getPaperStore() {
        return paperStoreRoom;
    }

    /**
     *  Setters for a new current room and new current speaker.
     */
    public void setCurrentRoom(Room room) {
       currentRoom = room;
    }
    public void setCurrentSpeaker(ICharacter currentSpeaker) {
        this.currentSpeaker = currentSpeaker;
    }


}
