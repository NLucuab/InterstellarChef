package com.interstellarchef.view;

import com.interstellarchef.model.GameText;

public class View {

    GameText gameText;

    public View(GameText gameText){
        this.gameText = gameText;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void printStartBanner() {
        clearScreen();
        System.out.println(gameText.getGameBanner());
        System.out.println(gameText.getGreeting());
        System.out.println(gameText.getPressEnterMessageCentered());
    }

    public void printGameIntro(){
        clearScreen();
        System.out.println(gameText.getGameIntro());
    }

}

