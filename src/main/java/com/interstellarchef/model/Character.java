package com.interstellarchef.model;

import com.interstellarchef.controller.GameController;
import com.interstellarchef.util.Recipe;
import java.util.HashMap;
import java.util.List;

public class Character {
  private String name;
  private String description;
  private HashMap<String,String> actionResponse; //action, text printed in response to action
  private List<Item> items;
  private List<String> dialog;
  private String desiredItem;

  public Character(){

  }
  public Character(String name, String description,
      HashMap<String, String> actionResponse, List<Item> items,
      List<String> dialog, String desiredItem) {
    this.name = name;
    this.description = description;
    this.actionResponse = actionResponse;
    this.items = items;
    this.dialog = dialog;
    this.desiredItem = desiredItem;
  }

  public void talk(){
    String result = "";
    System.out.println(getActionResponse().get("talk"));
    if(("Random Alien".equalsIgnoreCase(this.getName())
        || "Random Creature".equalsIgnoreCase(this.getName())
        || "Spacewalker Felix".equalsIgnoreCase(this.getName())
        || "Desert Alien".equalsIgnoreCase(this.getName())) ){
      System.out.printf((dialog.get((int) (Math.random() * 10))) + "%n", name);
    }else{
      System.out.printf((dialog.get(0)) + "%n", name);
    }

  }

  public void giveItem(Player player){
    if(items.size() >= 1){
      System.out.println(name + ": Take this.");
      System.out.println("You received " + items.get(0).getName());
      player.getInventory().addItem(items.get(0));
      System.out.println(items.get(0).getActionResponse().get("get"));
      items.remove(0);
    }
  }

  public void trade(Player player){
    Item matchedItem = null;
    if(items.size() == 0){
      return;
    } else if (desiredItem == null) {
      giveItem(player);
      dialog.remove(0);
      return;
    }
    for (Item item : player.getInventory().getItems()){
      if(item.getName().equalsIgnoreCase(desiredItem)){
        System.out.println("You give the " + desiredItem + " to "+ name);
        System.out.println(name + ": Thanks!");
        giveItem(player);
        matchedItem = item;
        break;
      }
    }
    if(matchedItem != null){
      player.getInventory().removeItem(matchedItem);
      dialog.remove(0);
    }
  }

  public void talkToChef(GameController gameController){
    System.out.printf((dialog.get(0)) + "%n",name);
    Recipe currentRecipe = null;
    for (Item item : gameController.getGame().getPlayer().getInventory().getItems()){
      if (item.getName().contains("Recipe")){
        for(Recipe recipe: gameController.getGame().getGameRecipes()){
          if (item.getName().equalsIgnoreCase(recipe.getName())){
            currentRecipe = recipe;
            break;
          }
        }
      }
    }

    if(currentRecipe != null){
      boolean recipeCompleted = checkItems(false, currentRecipe, gameController);

      if (recipeCompleted){
        System.out.printf("%s: Congratulations! You've completed %s.\n",name,currentRecipe.getName());
        checkItems(true, currentRecipe, gameController);
        gameController.getGame().addCompletedRecipe(currentRecipe);
        if (gameController.getGame().getCompletedRecipes().size() < 5){
          System.out.printf("%s: Would you like to complete another recipe? (yes/no)\n", name);
          if(gameController.getUserInput().equalsIgnoreCase("yes")){
            giveItem(gameController.getGame().getPlayer());
            System.out.printf("%s: Gather all of the ingredients and bring them to me!\n", name);
          } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
          }

        } else {
          System.out.printf("%s: You've completed all of the recipes!%n",name);
          System.out.printf("%s: Congratulations! I'm promoting you to Head Chef!%n",name);
          System.out.printf("%s: ...It's time for me to retire, anyway.%n",name);
          System.out.println("You completed the entire game!");
          System.exit(0);
        }
      } else {
        System.out.printf("%s: Looks like you have more ingredients to find! Come back when you're done.%n", name);
      }
    } else {
      giveItem(gameController.getGame().getPlayer());
      System.out.printf("%s: Gather all of the ingredients and bring them to me!\n", name);
    }
  }

  private boolean checkItems(boolean removeItems, Recipe currentRecipe, GameController gameController){
    boolean recipeCompleted = true;
    Item itemToRemove = null;
    for(Item item: currentRecipe.getIngredients()) {
      boolean validItem = false;
      for (Item inventoryItem : gameController.getGame().getPlayer().getInventory().getItems()) {
        if (item.getName().equalsIgnoreCase(inventoryItem.getName())) {
          validItem = true;
          if(removeItems){
            itemToRemove = inventoryItem;
          }
          break;
        }
      }
      if (!validItem) {
        recipeCompleted = false;
        break;
      }
      if(itemToRemove != null){
        gameController.getGame().getPlayer().getInventory().removeItem(itemToRemove);
        itemToRemove = null;
      }
    }
    if (recipeCompleted && removeItems){
      for (Item inventoryItem : gameController.getGame().getPlayer().getInventory().getItems()){
        if (inventoryItem.getName().equalsIgnoreCase(currentRecipe.getName())){
          itemToRemove = inventoryItem;
          break;
        }
      }
      if(itemToRemove != null){
        gameController.getGame().getPlayer().getInventory().removeItem(itemToRemove);
        itemToRemove = null;
      }
    }
    return recipeCompleted;
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

  public HashMap<String, String> getActionResponse() {
    return actionResponse;
  }

  public void setActionResponse(HashMap<String, String> actionResponse) {
    this.actionResponse = actionResponse;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public String getDesiredItem() {
    return desiredItem;
  }

  public void setDesiredItem(String desiredItem) {
    this.desiredItem = desiredItem;
  }
}
