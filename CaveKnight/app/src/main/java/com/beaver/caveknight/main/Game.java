package com.beaver.caveknight.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.beaver.caveknight.gamestates.DeathScreen;
import com.beaver.caveknight.gamestates.MainMenu;
import com.beaver.caveknight.gamestates.Playing;

public class Game {

    private final SurfaceHolder holder;
    private MainMenu mainMenu;
    private Playing playing;
    private DeathScreen deathScreen;
    private final GameLoop gameLoop;
    private GameState currentGameState = GameState.MAINMENU;

    public Game(SurfaceHolder holder) {
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();
    }

    public void update(double delta) {

        switch (currentGameState) {
            case MAINMENU -> mainMenu.update(delta);
            case PLAYING -> playing.update(delta);
            case DEATH_SCREEN -> deathScreen.update(delta);
        }
    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        //Draw the game
        switch (currentGameState) {
            case MAINMENU -> mainMenu.render(c);
            case PLAYING -> playing.render(c);
            case DEATH_SCREEN -> deathScreen.render(c);
        }

        holder.unlockCanvasAndPost(c);
    }

    private void initGameStates() {
        mainMenu = new MainMenu(this);
        playing = new Playing(this);
        deathScreen = new DeathScreen(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case MAINMENU -> mainMenu.touchEvents(event);
            case PLAYING -> playing.touchEvents(event);
            case DEATH_SCREEN -> deathScreen.touchEvents(event);
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState {
        MAINMENU, PLAYING, DEATH_SCREEN
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;

    }

    public DeathScreen getDeathScreen() {
        return deathScreen;
    }

    public Playing getPlaying() {
        return playing;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
