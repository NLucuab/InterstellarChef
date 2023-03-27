package com.interstellarchef.controller;

import com.interstellarchef.model.Character;
import com.interstellarchef.model.Game;
import com.interstellarchef.util.GameText;

public class TextParser {
    private final GameController gameController;
    private final Game game;
    private final GameText gameText;

    TextParser(GameController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGame();
        this.gameText = gameController.getGameText();
    }


    public void promptUserAction() {
        boolean valid = false;
        String currentAction;
        String currentNoun;
        String playerStatusDivider = gameText.getDividerBar();
        String playerCurrentLocation = String.format(gameText.getCurrentLocationWidget(), game.getCurrentLocation().getName());
        String playerAvailableExits = String.format(gameText.getAvailableExitWidget(), game.getCurrentLocation().printExits());
        String currentRoomItems = String.format(gameText.getRoomItemsWidget(), game.getCurrentLocation().printItems());
        String currentRoomCharacters = String.format(gameText.getRoomCharactersWidget(), game.getCurrentLocation().printCharacters());
        String currentEquippedItem = String.format(gameText.getEquippedItemWidget(), game.getPlayer().printEquippedItem());
        String currentRoomMonster = String.format(gameText.getRoomMonsterWidget(), (game.getCurrentLocation().getMonster() == null ? "none" : game.getCurrentLocation().getMonster().getName()));
        while (!valid) {
            System.out.println(playerStatusDivider);
            System.out.println(playerCurrentLocation);
            System.out.println(playerAvailableExits);
            System.out.println(currentRoomItems);
            System.out.println(currentRoomCharacters);
            System.out.println(currentRoomMonster);
            System.out.println(currentEquippedItem);
            System.out.println(playerStatusDivider);
            System.out.println(gameText.getPromptActionMessage());
            String input = gameController.getUserInput();
            String[] inputArray = input.split(" ", 2);
            currentAction = inputArray[0];

            //check if input greater than 1 word, else continue
            if (inputArray.length == 1) {
                if (inputArray[0].equalsIgnoreCase(gameText.getHelpKeyWord())) {
                    String helpInfo = gameText.getHelpMessage();
                    System.out.println(helpInfo);
                    System.out.println(gameText.getDividerBar());
                } else if (currentAction.equalsIgnoreCase("quit")) {
                    continue;
                } else {
                    System.out.println(gameText.getMissingWordMessage());
                }
                continue;
            }

            //check noun and action
            currentNoun = checkInput(currentAction, inputArray[1]);

            //if noun not valid, continue
            if (currentNoun.equals("")) {
                System.out.println(gameText.getInvalidActionMessage());
            } else {
                valid = true;
                System.out.println(gameText.getPressEnterMessage());
            }
        }

    }

    private String checkInput(String action, String noun) {
        String result = "";

        //check directions
        if (action.equalsIgnoreCase("go")) { //todo: include synonyms
            for (String direction : game.getCurrentLocation().getExits().keySet()) {
                if (noun.equalsIgnoreCase(direction)) {
                    //change current location
                    if (game.getCurrentLocation().getExits().get(direction).equalsIgnoreCase("Floating Garden") && (game.getPlayer().getEquippedItem() == null || !game.getPlayer().getEquippedItem().getName().equalsIgnoreCase("spacesuit"))) {
                        System.out.println("You have to wear a Spacesuit to go out to the Floating Garden!");
                    } else {
                        game.changeCurrentLocation(direction);
                    }
                    return direction;
                }
            }
        }


        //check characters
        if (action.equalsIgnoreCase("talk")) {
            for (Character character : game.getCurrentLocation().getCharacters()) {
                if (noun.equalsIgnoreCase(character.getName())) {
                    if(character.getName().equalsIgnoreCase("Head Chef")){
                        character.talkToChef(gameController);
                    } else {
                        character.talk();
                        character.trade(game.getPlayer());
                    }
                    return character.getName();
                }
            }
            if (game.getCurrentLocation().getMonster() != null && game.getCurrentLocation().getMonster().getName().equalsIgnoreCase(noun)) {
                if (game.getCurrentLocation().getMonster().getRiddles().size() > 0) {
                    game.getCurrentLocation().getMonster().playRiddleGame(gameController);
                } else if (game.getCurrentLocation().getMonster().getItems().size() != 0) {
                    System.out.printf("%1$s: Hello Spacewalker. If it's this item you want, a game you must play. \n%1$s: What do you say? (yes/no)\n",game.getCurrentLocation().getMonster().getName());
                    String output = game.readyForGameCheck(gameController.getUserInput());
                    if (output.equalsIgnoreCase("yes")) {
                        System.out.printf("%1$s: I have something hidden in one of my hands. " +
                                "\n%1$s: If you choose the correct hand (left/right) you can have the item you've come in search of!" +
                                "\n%1$s: Go ahead... choose a hand!\n",game.getCurrentLocation().getMonster().getName());
                        String choice = gameController.getUserInput();
                        String chooseAHandGameResult = game.chooseAHandGame(choice);
                        if (chooseAHandGameResult.equalsIgnoreCase("winner")) {
                            game.getCurrentLocation().getMonster().giveItem(game.getPlayer());
                        } else {
                            System.out.printf("%1$s: " + chooseAHandGameResult,game.getCurrentLocation().getMonster().getName());
                        }
                    } else {
                        // user types in anything other than yes @ ready check
                        System.out.printf("%1$s: Come back when you're ready\n",game.getCurrentLocation().getMonster().getName());
                    }
                } else {
                    System.out.printf("%1$s: Hello again Spacewalker, alas...you have already beat me at my game. \n%1$s: Enjoy your prize, and safe travels!\n",game.getCurrentLocation().getMonster().getName());
                }
                return game.getCurrentLocation().getMonster().getName();
            }
        }


        //look item, location, character
        if (action.equalsIgnoreCase("look")) {
            return game.look(noun);
        }

        //get item
        if (action.equalsIgnoreCase("get")) {
            return game.get(noun);
        }

        //drop item
        if (action.equalsIgnoreCase("drop")) {
            return game.drop(noun);
        }

        if (action.equalsIgnoreCase("equip")) {
            return game.getPlayer().equipItem(noun);
        }

        //fly discovery pod
        if (action.equalsIgnoreCase("fly") && noun.trim().equalsIgnoreCase("discovery pod")) {
            String output = game.prepareToFly();
            if (!output.equalsIgnoreCase("") && !output.equalsIgnoreCase(" ")) {
                output = game.fly(gameController.getUserInput(), gameController);
            }
            return output;
        }
        if(action.equalsIgnoreCase("toggle")){
            gameController.getMusicPlayer().toggleMusic();
            return gameController.getMusicPlayer().getCurrentSong();
        }
        if(action.equalsIgnoreCase("volume")){
            if(noun.trim().equalsIgnoreCase("up")){
                gameController.getMusicPlayer().volumeUp();
            }else{
                gameController.getMusicPlayer().volumeDown();
            }
            return gameController.getMusicPlayer().getCurrentSong();
        }
        return result;
    }

}
