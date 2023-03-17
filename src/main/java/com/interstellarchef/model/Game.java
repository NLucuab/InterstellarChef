package com.interstellarchef.model;

import java.util.List;

public class Game {
  Location currentLocation;
  Location inventory;
  Item currentRecipe;
  List<String> completedRecipes;
  Location[] gameLocations;

  public Game(Location currentLocation, Location inventory, Location[] locations) {
    this.currentLocation = currentLocation;
    this.inventory = inventory;
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

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public Location getInventory() {
    return inventory;
  }

  public void setInventory(Location inventory) {
    this.inventory = inventory;
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
}
