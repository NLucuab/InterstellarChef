package com.interstellarchef.controller;

import static com.interstellarchef.view.View.clearScreen;
import com.google.gson.Gson;
import com.interstellarchef.model.Character;
import com.interstellarchef.model.Game;
import com.interstellarchef.model.GameText;
import com.interstellarchef.model.Item;
import com.interstellarchef.model.Location;
import com.interstellarchef.view.View;

import java.io.InputStreamReader;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class GameController {

  Game game;
  GameText gameText;

 public GameController(){
   setUpGameText();
   runGameIntro();
   startGame();
 }

  public void startGame(){
    clearScreen();
    System.out.println(gameText.getNewGameMessage());
    String input;
    Scanner scan = new Scanner(System.in);
    input = scan.nextLine().toLowerCase();
    if(input.equals(gameText.getAffirmative())){
      playGame();
    }else{
      quitGame(false);
    }

  }

  public void quitGame(boolean ask) {
    String quitGame;
    if (ask) {
      System.out.println(gameText.getConfirmQuitMessage());
      quitGame = getUserInput();
    } else {
      quitGame = "yes";
    }
      if (quitGame.equals(gameText.getAffirmative())) {
        System.out.println(gameText.getGoodbyeMessage());
        System.exit(0);
      } else {
        System.out.println("It's our honor you choose to stay with us");
      }
  }

  public String getUserInput(){
    String input;
    Scanner scan = new Scanner(System.in);
    input = scan.nextLine().toLowerCase();
    if(input.equals(gameText.getQuitKeyWord())){
      quitGame(true);
    }
    return input;
  }

  public void runGameIntro(){

    View viewInstance = new View(gameText);
    viewInstance.printStartBanner();

    String input = getUserInput();
    while(!input.equals("")){
      viewInstance.printStartBanner();
      input = getUserInput();
    }

    viewInstance.printGameIntro();
  }

  public void playGame(){

    //todo: The following is for testing TextParser. Replace after json functionality is completed.
    HashMap<String, String> actionResponse = new HashMap<>();
    HashMap<String, String> chefActionResponse = new HashMap<>();
    chefActionResponse.put("look","Pizza");
    Item testChefItem = new Item("knife","Meat Chop", chefActionResponse);
    List<Item> itemChefList = new LinkedList<>();
    itemChefList.add(testChefItem);
    Character characters = new Character("chef", "Cooking food", chefActionResponse, itemChefList);
    Character[] testCharacters = new Character[]{characters};
    actionResponse.put("look","a cool rock");
    Item testItem = new Item("rock","a cool rock", actionResponse);
    List<Item> itemList = new LinkedList<>();
    itemList.add(testItem);
    HashMap<String, String> exitsA = new HashMap<>();
    exitsA.put("north","kitchen");
    Location testLocationA = new Location("room", "nice room", exitsA, new LinkedList<>(), testCharacters);
    HashMap<String, String> exitsB = new HashMap<>();
    exitsB.put("south","room");
    Location testLocationB = new Location("kitchen", "nice room", exitsB, itemList, new Character[0]);
    Location inventory = new Location(new String(" "), new String(" "), new HashMap<String, String>(), new LinkedList<>(), new Character[0]);
    this.game = new Game(testLocationA, inventory, new Location[]{testLocationA, testLocationB});
    TextParser parser = new TextParser(this);
    while(true){
        clearScreen();
        parser.promptUserAction();
    }


  }

  public void setUpGameText(){
    Gson gson = new Gson();

    try (Reader reader = new InputStreamReader(getClass()
                    .getClassLoader()
                    .getResourceAsStream("gametext.json"))) {

      this.gameText = gson.fromJson(reader,GameText.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public GameText getGameText() {
    return gameText;
  }

  public void setGameText(GameText gameText) {
    this.gameText = gameText;
  }
}
