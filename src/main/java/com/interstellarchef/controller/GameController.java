package com.interstellarchef.controller;

import static com.interstellarchef.view.View.clearScreen;
import com.google.gson.Gson;
import com.interstellarchef.model.*;
import com.interstellarchef.model.Character;
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
   // ToDo: make set up game conditional based on user input
   setUpGame();
   runGameIntro();
   startGame();
 }

  public void startGame(){
    //clearScreen();
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
    TextParser parser = new TextParser(this);
    while(true){
        clearScreen();
        parser.promptUserAction();
        String input = getUserInput();
        while(!input.equals("")){
          input = getUserInput();
        }
    }
  }

  public void setUpGameText(){
    Gson gson = new Gson();

      //noinspection ConstantConditions
      try (Reader reader = new InputStreamReader(getClass()
                    .getClassLoader()
                    .getResourceAsStream("gametext.json"))) {

      this.gameText = gson.fromJson(reader,GameText.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setUpGame(){
     Gson gson = new Gson();
     Location[] locations = new Location[40];

      //noinspection ConstantConditions
      try (Reader reader = new InputStreamReader(getClass()
              .getClassLoader()
              .getResourceAsStream("locations.json"))) {
          
          locations = gson.fromJson(reader,Location[].class);

      } catch (IOException e) {
          e.printStackTrace();
      }
      Player player = new Player(locations[1]);
      this.game = new Game(locations[0], player, locations);
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
