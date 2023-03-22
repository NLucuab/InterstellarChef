package com.interstellarchef.controller;

import com.interstellarchef.model.Character;
import com.interstellarchef.model.Game;
import com.interstellarchef.model.GameText;
import com.interstellarchef.model.Item;

public class TextParser {

  //todo: when JSON files created, take values and printed strings below from the JSON file
  private final GameController gameController;
  private final Game game;
  private final GameText gameText;

  TextParser(GameController gameController){
    this.gameController = gameController;
    this.game = gameController.getGame();
    this.gameText = gameController.getGameText();
  }


  public void promptUserAction(){
    boolean valid = false;
    String currentAction;
    String currentNoun;
    String playerStatusDivider = gameText.getDividerBar();
    String playerCurrentLocation = String.format(gameText.getCurrentLocationWidget(), game.getCurrentLocation().getName());
    String playerAvailableExits = String.format(gameText.getAvailableExitWidget(), game.getCurrentLocation().printExits());
    String currentRoomItems = String.format(gameText.getRoomItemsWidget(), game.getCurrentLocation().printItems());
    String currentRoomCharacters = String.format(gameText.getRoomCharactersWidget(), game.getCurrentLocation().printCharacters());
    String currentEquippedItem = String.format(gameText.getEquippedItemWidget(), game.getPlayer().printEquippedItem());
    while(!valid){
      System.out.println(playerStatusDivider);
      System.out.println(playerCurrentLocation);
      System.out.println(playerAvailableExits);
      System.out.println(currentRoomItems);
      System.out.println(currentRoomCharacters);
      System.out.println(currentEquippedItem);
      System.out.println(playerStatusDivider);
      System.out.println(gameText.getPromptActionMessage());
      String input = gameController.getUserInput();
      String[] inputArray = input.split(" ", 2);
      currentAction = inputArray[0];

      //check if input greater than 1 word, else continue
      if(inputArray.length == 1) {
          if (inputArray[0].equalsIgnoreCase(gameText.getHelpKeyWord())) {
              String helpInfo = gameText.getHelpMessage();
              System.out.println(helpInfo);
          } else if(currentAction.equalsIgnoreCase("quit")){
            continue;
          } else {
              System.out.println(gameText.getMissingWordMessage());
          }
          continue;
      }

      //check noun and action
      currentNoun = checkInput(currentAction, inputArray[1]);

      //if noun not valid, continue
      if(currentNoun.equals("")) {
        System.out.println(gameText.getInvalidActionMessage());
      } else {
        valid = true;
        System.out.println(gameText.getPressEnterMessage());
      }
    }

  }

  private String checkInput(String action, String noun){
    String result = "";

    //check directions
    if (action.equalsIgnoreCase("go")){ //todo: include synonyms and load from json
      for (String direction : game.getCurrentLocation().getExits().keySet()){
        if(noun.equalsIgnoreCase(direction)){
          //change current location
          game.changeCurrentLocation(direction);
          return direction;
        }
      }
    }

    //check characters
    if (action.equalsIgnoreCase("talk")) { //todo: include synonyms and load from json
      for(Character character: game.getCurrentLocation().getCharacters()){
        if(noun.equalsIgnoreCase(character.getName())){
          character.talk();
          return character.getName();
        }
      }
    }


    //look item, location, character
    if(action.equalsIgnoreCase("look")) {
      return game.look(noun);
    }

    //get item
    if(action.equalsIgnoreCase("get")) {
      return game.get(noun);
    }

    //drop item
    if(action.equalsIgnoreCase("drop")) {
      return game.drop(noun);
    }

    if (action.equalsIgnoreCase("equip")) {
      return game.getPlayer().equipItem(noun);
    }

    //check items in current room
    if (action.equalsIgnoreCase("grab")) {//todo: include synonyms and load from json
      for(Item item: game.getCurrentLocation().getItems()){
        if(noun.equalsIgnoreCase(item.getName())){
          for (String allowedAction : item.getActionResponse().keySet()) {
            //checks if action can be performed on item
            if (action.equalsIgnoreCase(allowedAction)){
              return item.getName();
            }
          }
        }
      }
    }

    //check items in inventory
    for(Item item: game.getPlayer().getInventory().getItems()){
      if(noun.equalsIgnoreCase(item.getName())){
        for (String allowedAction : item.getActionResponse().keySet()) {
          //checks if action can be performed on item
          if (action.equalsIgnoreCase(allowedAction)){
            result = item.getName();
            break;
          }
          break;
        }
      }
    }

    return result;
  }

}
