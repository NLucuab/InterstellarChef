package com.interstellarchef.model;

import com.interstellarchef.controller.GameController;
import com.interstellarchef.util.Recipe;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
  private Location currentLocation;
  private Player player;
  private final Location[] gameLocations;
  private final Recipe[] gameRecipes;
  private List<Recipe> completedRecipes = new LinkedList<>();

  public Game(Location currentLocation, Player player, Location[] locations, Recipe[] gameRecipes) {
    this.currentLocation = currentLocation;
    this.player = player;
    this.gameLocations = locations;
    this.gameRecipes = gameRecipes;
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

    if(noun.equalsIgnoreCase(player.getInventory().getName())) {
      System.out.println(player.getInventory().getDescription());
      if (player.getInventory().getItems().size() == 0) {
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

    for(Item item: player.getInventory().getItems()){
      if(noun.equalsIgnoreCase(item.getName())){
        System.out.println(item.getDescription());
        return item.getDescription();
      }
    }

    if(currentLocation.getMonster() != null && noun.equalsIgnoreCase(currentLocation.getMonster().getName())){
      System.out.println(currentLocation.getMonster().getDescription());
      return currentLocation.getMonster().getName();
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

  public String fly(String destinationName, GameController gameController) {
    String result = "";
    if(!destinationName.toLowerCase().contains("planet") && !destinationName.toLowerCase().contains("home") ){
      return result;
    }
    if (destinationName.toLowerCase().contains("home")){
      currentLocation = gameLocations[18]; //space pod
      System.out.println("You safely return home.");
      gameController.getMusicPlayer().stop();
      gameController.getMusicPlayer().setCurrentSong(gameController.getMusicPlayer().getHappySong());
      gameController.getMusicPlayer().play();
      return gameLocations[18].getName();
    }
    for(Location location : gameLocations){
      if (location.getName().equalsIgnoreCase(destinationName)){
        currentLocation = location;
        System.out.println("You masterfully navigate the path to " + location.getName() +", marveling at the beauty of space.");
        System.out.println("You place the Discovery Pod in Discovery Mode. Now the 'fly Discovery Pod' command can be used from any location on a planet.");
        gameController.getMusicPlayer().stop();
        gameController.getMusicPlayer().setCurrentSong(gameController.getMusicPlayer().getScarySong());
        gameController.getMusicPlayer().play();
        result = location.getName();
        break;
      }
    }
    return result;
  }

  public String readyForGameCheck(String startDecision){
    String ready = "";
    if(startDecision.equalsIgnoreCase("yes")){
      ready = "yes";
    } else {
      ready = "no";
    }
    return ready;
  }

  public String chooseAHandGame(String choice){
    Random rand = new Random();
    int upperbound = 2;
    int int_random = rand.nextInt(upperbound);
    String[] hands = new String[] {"left", "right"};
    String correctHand = hands[int_random];
    if (choice.equalsIgnoreCase(correctHand)){
      return "winner";
    } else {
      return "Nice try, but not quite! Try again~\n";
    }
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

  public Recipe[] getGameRecipes() {
    return gameRecipes;
  }

  public Location[] getGameLocations() {
    return gameLocations;
  }

  public List<Recipe> getCompletedRecipes() {
    return completedRecipes;
  }

  public void setCompletedRecipes(List<Recipe> completedRecipes) {
    this.completedRecipes = completedRecipes;
  }
  public void addCompletedRecipe(Recipe recipe){
    this.completedRecipes.add(recipe);
  }
}
