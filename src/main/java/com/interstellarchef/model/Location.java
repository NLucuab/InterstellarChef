package com.interstellarchef.model;

import java.util.HashMap;
import java.util.List;

public class Location {

    private String name;
    private String description;
    private HashMap<String, String> exits; //direction, exit to
    private List<Item> items;
    private Character[] characters;

    private Monster monster;

    public Location(String name, String description, HashMap<String, String> exits,
        List<Item> items, Character[] characters) {
        this.name = name;
        this.description = description;
        this.exits = exits;
        this.items = items;
        this.characters = characters;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getExits() {
        return exits;
    }

    public void setExits(HashMap<String, String> exits) {
        this.exits = exits;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public String printItems() {
        StringBuilder itemsString = new StringBuilder();
        if (items.size() == 0) {
            return "none";
        }
        for (int i = 0; i < items.size(); i++) {
            itemsString.append(items.get(i).getName());
            if (items.size() > 1 && i != items.size() - 1) {
                itemsString.append(", ");
            }
        }
        return itemsString.toString();
    }

    public String printCharacters() {
        StringBuilder characterString = new StringBuilder();
        if (characters.length == 0) {
            return "none";
        }
        for (int i = 0; i < characters.length; i++) {
            characterString.append(characters[i].getName());
            if (characters.length > 1 && i != characters.length - 1) {
                characterString.append(", ");
            }
        }
        return characterString.toString();
    }

    public String printExits() {
        int counter = 0;
        StringBuilder exitString = new StringBuilder();
        if (exits.size() == 0) {
            return "none";
        }
        for (String key : exits.keySet()) {
            exitString.append(key);
            exitString.append(": ");
            exitString.append(exits.get(key));
            if (exits.size() > 1 && counter != exits.size() - 1) {
                exitString.append(", ");
            }
            counter++;
        }
        return exitString.toString();
    }
}
