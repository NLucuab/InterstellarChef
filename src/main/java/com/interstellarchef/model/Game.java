package com.interstellarchef.model;

import java.util.List;

public class Game {
  Location currentLocation;

  Player player;
  Item currentRecipe;
  List<String> completedRecipes;
  Location[] gameLocations;

  public Game(Location currentLocation, Player player, Location[] locations) {
    this.currentLocation = currentLocation;
    this.player = player;
    this.gameLocations = locations;
  }

  public void changeCurrentLocation(String locationDirection){
    String nextLocationName = currentLocation.getExits().get(locationDirection);
    for(Location location : gameLocations){
      if(location.getName().equalsIgnoreCase(nextLocationName)){
        setCurrentLocation(location);
        System.out.println("You are now in the " + location.getName() + ".");
      }
    }
  }

  public String look(String noun) {

    if(noun.equalsIgnoreCase(player.getInventory().getName())){
      System.out.println(player.getInventory().getDescription());
      if (player.getInventory().getItems().size() == 0){
        System.out.println("Nothing.");
      } else {
        for (Item item: player.getInventory().getItems()){
          System.out.println(item.getName());
        }
      }
      return player.getInventory().getDescription();
    }

    if(noun.equalsIgnoreCase(currentLocation.getName())) {
      System.out.println(currentLocation.getDescription());
      return currentLocation.getDescription();
    }

    for(Character character: currentLocation.getCharacters()) {
      if (noun.equalsIgnoreCase(character.getName())) {
        //print out character description
        System.out.println(character.getDescription());
        return character.getName();
      }
    }

    for(Item item: currentLocation.getItems()){
      if(noun.equalsIgnoreCase(item.getName())){
        System.out.println(item.getDescription());
        return item.getDescription();
      }
    }
    return "";
  }

  public String get(String noun){
    for(Item item: currentLocation.getItems()){
      if(noun.equalsIgnoreCase(item.getName())){
        player.getInventory().addItem(item);
        System.out.println(item.getActionResponse().get("get"));
        currentLocation.removeItem(item);
        return item.getName();
      }
    }
    return "";
  }

  public String drop(String noun){
    for(Item item: player.getInventory().getItems()){
      if(noun.equalsIgnoreCase(item.getName())){
        player.getInventory().removeItem(item);
        System.out.println(item.getActionResponse().get("drop"));
        currentLocation.addItem(item);
        return item.getName();
      }
    }
    return "";
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public Item getCurrentRecipe() {
    return currentRecipe;
  }

  public void setCurrentRecipe(Item currentRecipe) {
    this.currentRecipe = currentRecipe;
  }

  public List<String> getCompletedRecipes() {
    return completedRecipes;
  }

  public void setCompletedRecipes(List<String> completedRecipes) {
    this.completedRecipes = completedRecipes;
  }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
