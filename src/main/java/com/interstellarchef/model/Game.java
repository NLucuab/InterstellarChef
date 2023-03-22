package com.interstellarchef.model;

import java.util.List;
import java.util.Locale;

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

  public String prepareToFly(){
    String result = "";
    if(player.getEquippedItem() == null || (!currentLocation.getName().toLowerCase().contains("planet") && !currentLocation.getName().toLowerCase().contains("pod"))){
      if (!currentLocation.getName().toLowerCase().contains("planet") && !currentLocation.getName().toLowerCase().contains("pod")){
        System.out.println("You must be in a valid location to fly the Discovery Pod.");
      } else {
        System.out.println("You must equip a Spacesuit to use the Discovery Pod.");
      }
      return " ";
    }
    boolean hasKey = false;
    boolean hasGPS = false;
    boolean hasSpacesuitEquipped = player.getEquippedItem().getName().equalsIgnoreCase("Spacesuit");
    for(Item item : player.getInventory().getItems()){
      if(item.getName().equalsIgnoreCase("Key")){
        hasKey = true;
      } else if (item.getName().equalsIgnoreCase("GPS")){
        hasGPS = true;
      }
    }
    if(hasKey && hasGPS && hasSpacesuitEquipped){
      if (currentLocation.getName().toLowerCase().contains("planet")){
        System.out.println("You board the Discovery Pod and make preparations to go underway.");
      } else {
        System.out.println("You start the Discovery Pod and begin setting up the GPS.");
      }
      System.out.println("Which planet will you travel to? Please include a quadrant.");
      System.out.println("Example: North Volcano Planet, West Rainforest Planet, East Swamp Planet, North Desert Planet, South Snow Planet");
      System.out.println("If you are ready to return to the space station, enter 'home'.");
      result = "ready";
    } else {
      System.out.println("You must have a key and gps in your inventory to fly the Discovery Pod.");
      result= " ";
    }
    return result;
  }

  public String fly(String destinationName) {
    String result = "";
    if(!destinationName.toLowerCase().contains("planet") && !destinationName.toLowerCase().contains("home") ){
      return result;
    }
    if (destinationName.toLowerCase().contains("home")){
      currentLocation = gameLocations[18]; //space pod
      System.out.println("You safely return home.");
      return gameLocations[18].getName();
    }
    for(Location location : gameLocations){
      if (location.getName().equalsIgnoreCase(destinationName)){
        currentLocation = location;
        System.out.println("You masterfully navigate the path to " + location.getName() +", marveling at the beauty of space.");
        System.out.println("You place the Discovery Pod in Discovery Mode. Now the 'fly Discovery Pod' command can be used from any location on a planet.");
        result = location.getName();
        break;
      }
    }
    return result;
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
