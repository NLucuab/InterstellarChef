package com.interstellarchef.model;

import com.interstellarchef.controller.GameController;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    Player player;
    Character character;
    Location inventory;
    List<Item> items;
    HashMap<String, String> actionResponse = new HashMap<>(){{
        put("get","You put the object in your inventory.");
    }};
    Item item = new Item("object", "", actionResponse);
    Item desiredItem = new Item("desired object", "", actionResponse);
    List<String> dialog = new LinkedList<>(Arrays.asList("Hello","How's it going?","dialog filler"));

    @BeforeEach
    public void initEach(){
        items = new LinkedList<>();
        items.add(item);
        character = new Character("Sam","",new HashMap<>(),items,dialog, null);
        inventory = new Location("inventory","", new HashMap<>(), new LinkedList<>(), new Character[0]);
        player = new Player(inventory);
    }

    //character gives item if they don't have a desired item for trade
    @Test
    public void trade_NoDesiredItem(){
        List<Item> playerInitialItems = new LinkedList<>(inventory.getItems());
        character.trade(player);
        assertFalse(Arrays.equals(playerInitialItems.toArray(), player.getInventory().getItems().toArray()));
    }

    //character does not give item if they want to trade but player doesn't have item in inventory
    @Test
    public void trade_DesiredItemNotInInventory(){
        List<Item> playerInitialItems = new LinkedList<>(inventory.getItems());
        character.setDesiredItem(desiredItem.getName());
        character.trade(player);
        assertArrayEquals(playerInitialItems.toArray(), player.getInventory().getItems().toArray());
    }

    //character does give item if they want to trade and player has item in inventory
    @Test
    public void trade_DesiredItemInInventory(){
        List<Item> characterInitialItems = new LinkedList<>(character.getItems());
        character.setDesiredItem(desiredItem.getName());
        player.getInventory().addItem(desiredItem);
        character.trade(player);
        assertArrayEquals(characterInitialItems.toArray(), player.getInventory().getItems().toArray());
    }

}