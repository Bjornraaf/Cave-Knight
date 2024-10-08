package com.beaver.caveknight.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.beaver.caveknight.gamestates.MainMenu;
import com.beaver.caveknight.gamestates.Playing;

public class Game {

    private final SurfaceHolder holder;
    private MainMenu mainMenu;
    private Playing playing;
    private final GameLoop gameLoop;
    private GameState currentGameState = GameState.MENU;

    public Game(SurfaceHolder holder) {
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();
    }

    public void update(double delta) {

        switch (currentGameState) {
            case MENU -> mainMenu.update(delta);
            case PLAYING -> playing.update(delta);
        }
    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        //Draw the game
        switch (currentGameState) {
            case MENU -> mainMenu.render(c);
            case PLAYING -> playing.render(c);
        }

        holder.unlockCanvasAndPost(c);
    }

    private void initGameStates() {
        mainMenu = new MainMenu(this);
        playing = new Playing(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case MENU -> mainMenu.touchEvents(event);
            case PLAYING -> playing.touchEvents(event);
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState {
        MENU, PLAYING
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;

    }

}
