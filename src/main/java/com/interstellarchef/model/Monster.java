package com.interstellarchef.model;

import com.interstellarchef.controller.GameController;

import java.util.HashMap;
import java.util.Random;

public class Monster extends Character {

    private HashMap<String, String> riddles;

    public String playRiddleGame(GameController gc) {
        System.out.printf("%1$s: Hello Spacewalker, if it is this item you seek, a riddle you must solve." +
                "\n%1$s: If your answer is correct, you can have the item you've come in search of!" +
                "\n%1$s: What do you say? Will you give it a go? (yes/no)\n",this.getName());
        String readyToPlay = gc.getGame().readyForGameCheck(gc.getUserInput());
        if (readyToPlay.equalsIgnoreCase("yes")) {
            String riddleAnswer = selectRiddle();
            String userInput = gc.getUserInput();
            if (userInput.equalsIgnoreCase(riddleAnswer)) {
                if (gc.getGame().getCurrentLocation().getMonster().getItems().size() == 0) {
                    System.out.printf("%s: You fell into my trap! I don't actually have anything to give you~\n",this.getName());
                    riddles = new HashMap<>();
                } else {
                    gc.getGame().getCurrentLocation().getMonster().giveItem(gc.getGame().getPlayer());
                    riddles = new HashMap<>();
                }
            } else {
                System.out.printf("%s: Nice try, but not quite! Maybe you'll solve the next riddle!\n",this.getName());
            }
        } else {
            System.out.printf("%s: Come back when you're ready\n",this.getName());
        }
        return gc.getGame().getCurrentLocation().getMonster().getName();

    }

    public String selectRiddle() {
        Random rand = new Random();
        int upperbound = 3;
        int int_random = rand.nextInt(upperbound);
        String[] riddlesKeyArray = riddles.keySet().toArray(new String[0]);
        System.out.println(riddlesKeyArray[int_random]);
        String riddleSolution = riddles.get(riddlesKeyArray[int_random]);
        return riddleSolution;
    }

    public HashMap<String, String> getRiddles() {
        return riddles;
    }

    public void setRiddles(HashMap<String, String> riddles) {
        this.riddles = riddles;
    }

}
