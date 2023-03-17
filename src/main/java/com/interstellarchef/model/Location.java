package com.interstellarchef.model;

import java.util.HashMap;
import java.util.List;

public class Location {

  private String name;
  private String description;
  private HashMap<String, String> exits; //direction, exit to
  private List<Item> items;
  private Character[] characters;

  public Location(String name, String description, HashMap<String,String> exits, List<Item> items, Character[] characters) {
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

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public Character[] getCharacters() {
    return characters;
  }

  public void setCharacters(Character[] characters) {
    this.characters = characters;
  }

  public String printItems() {
    StringBuilder itemsString = new StringBuilder();
    for (Item item : items) {
      itemsString.append(item.getName());
      if (items.size() > 1) {
        itemsString.append(", ");
      } else if (items.size() == 0) {
        return "none";
      }
    }
    return itemsString.toString();
  }

  public String printExits(){
    StringBuilder exitString = new StringBuilder();
    for (String key : exits.keySet()) {
      exitString.append(key);
      exitString.append(": ");
      exitString.append(exits.get(key));
      if (exits.size() > 1) {
        exitString.append(", ");
      } else if (exits.size() == 0) {
        return "none";
      }
    }
    return exitString.toString();
  }
}
