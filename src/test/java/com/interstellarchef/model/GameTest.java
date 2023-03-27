package com.interstellarchef.model;

import com.interstellarchef.controller.GameController;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import com.interstellarchef.model.Game;
import com.interstellarchef.model.Location;
import com.interstellarchef.model.Item;
import com.interstellarchef.model.Player;
import com.interstellarchef.util.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Location location;
    private Location location1;
    private Location location2;
    private Location location3;
    private Player player;


    @BeforeEach
    public void setUp() {
        // Create some items
        Item pepper = new Item("Pepper", "Wonder how old this is…", new HashMap<String, String>());
        Item spaceRock = new Item("Space Rock", "shiny space rock.", new HashMap<String, String>());
        Item meatballs = new Item("Martian Meatballs", "You're too afraid to ask what these are made of.", new HashMap<String, String>());
        Item key = new Item("Key", "This doesn't appear to be a door key", new HashMap<String, String>());
        Item gps = new Item("GPS", "A device that can be used to track your location and navigate.", new HashMap<String, String>());
        Item spacesuit = new Item("Spacesuit", "You must equip this before going to the floating garden.", new HashMap<String, String>());

        // Add the items to the current location
        location = new Location("Discovery Pod",
            "The doorway to discovery!",
            new HashMap<>(), new ArrayList<>(), new Character[1]);
        location.addItem(pepper);
        location.addItem(spaceRock);

        //set up for fly method
        location1 = new Location("West Swamp Planet", "Nothing here except an interesting fossil of a frog",
            new HashMap<>(), new ArrayList<>(), new Character[1]);
        location2 = new Location("North Volcano Planet", "This quadrant is home a shiny space rock",
            new HashMap<>(), new ArrayList<>(), new Character[1]);
        location3 = new Location("South Desert Planet", "Might have glass",
            new HashMap<>(), new ArrayList<>(), new Character[1]);

        // Create the player and add an item to their inventory
        Location inventory = new Location("Inventory", "This is test inventory.",  new HashMap<>(), new ArrayList<>(), new Character[10]);
        inventory.addItem(meatballs);
        player = new Player(inventory);
        player.getInventory().addItem(key);
        player.getInventory().addItem(gps);
        player.equipItem("spacesuit");

        // Create the game with the current location and player
        Location[] locations = {location, location1, location2, location3};
        Recipe[] recipes = {};
        game = new Game(location, player, locations, recipes);

    }

    @Test
    public void testGet() {
        // Call the get method with an item that is in the current location
        String itemName = game.get("Pepper");

        // Check that the item is removed from the current location and added to the player's inventory
        assertEquals(location.getItems().size(), 1);
        assertEquals(player.getInventory().getItems().size(), 4);
        assertEquals(itemName, "Pepper");
    }

    @Test
    public void testDrop() {
        // Call the drop method with an item that is in the player's inventory
        String itemName = game.drop("Martian Meatballs");

        // Check that the item is removed from the player's inventory and added to the current location
        assertEquals(location.getItems().size(), 3);
        assertEquals(player.getInventory().getItems().size(), 2);
        assertEquals(itemName, "Martian Meatballs");
    }

}
