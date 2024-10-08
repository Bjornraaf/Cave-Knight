package com.beaver.caveknight.gamestates;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.beaver.caveknight.helpers.interfaces.GameStateInterface;
import com.beaver.caveknight.main.Game;
import com.beaver.caveknight.main.MainActivity;
import com.beaver.caveknight.ui.ButtonImages;
import com.beaver.caveknight.ui.CustomButton;
import com.beaver.caveknight.ui.GameImages;

public class MainMenu extends BaseState implements GameStateInterface {

    private final CustomButton startButton;
    private final CustomButton optionsButton;
    private final CustomButton exitButton;

    int buttonBgWidth = GameImages.MAINMENU_BUTTONBG.getImage().getWidth();
    int buttonBgHeight = GameImages.MAINMENU_BUTTONBG.getImage().getHeight();

    int buttonBgX = (MainActivity.GAME_WIDTH / 2) - (buttonBgWidth / 2);
    int buttonBgY = (MainActivity.GAME_HEIGHT / 2) - (buttonBgHeight / 2);


    public MainMenu(Game game) {
        super(game);

        int startButtonY = buttonBgY + 100;
        int startButtonX = buttonBgX + GameImages.MAINMENU_BUTTONBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
        startButton = new CustomButton(startButtonX, startButtonY, ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_START.getHeight());

        int optionsButtonX = (MainActivity.GAME_WIDTH / 2) - (ButtonImages.MENU_OPTIONS.getWidth() / 2);
        int optionsButtonY = startButtonY + ButtonImages.MENU_START.getHeight() + 25;
        optionsButton = new CustomButton(optionsButtonX, optionsButtonY, ButtonImages.MENU_OPTIONS.getWidth(), ButtonImages.MENU_OPTIONS.getHeight());

        int exitButtonX = (MainActivity.GAME_WIDTH / 2) - (ButtonImages.MENU_EXIT.getWidth() / 2);
        int exitButtonY = optionsButtonY + ButtonImages.MENU_OPTIONS.getHeight() + 25;
        exitButton = new CustomButton(exitButtonX, exitButtonY, ButtonImages.MENU_EXIT.getWidth(), ButtonImages.MENU_EXIT.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {

        Rect destRect = new Rect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
        c.drawBitmap(
                GameImages.MAINMENU_BG.getImage(),
                null,
                destRect,
                null);

        c.drawBitmap(GameImages.MAINMENU_BUTTONBG.getImage(),
                buttonBgX,
                buttonBgY,
                null);

        c.drawBitmap(ButtonImages.MENU_START.getButtonImg(startButton.isPushed()),
                startButton.getHitbox().left,
                startButton.getHitbox().top,
                null);

        c.drawBitmap(ButtonImages.MENU_OPTIONS.getButtonImg(optionsButton.isPushed()),
                optionsButton.getHitbox().left,
                optionsButton.getHitbox().top,
                null);

        c.drawBitmap(ButtonImages.MENU_EXIT.getButtonImg(exitButton.isPushed()),
                exitButton.getHitbox().left,
                exitButton.getHitbox().top,
                null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isButtonTouched(event, startButton)) {
                startButton.setPushed(true);
            } else if (isButtonTouched(event, optionsButton)) {
                optionsButton.setPushed(true);
            } else if (isButtonTouched(event, exitButton)) {
                exitButton.setPushed(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isButtonTouched(event, startButton) && startButton.isPushed()) {
                game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isButtonTouched(event, optionsButton) && optionsButton.isPushed()) {
                System.out.println("Options Button is Clicked!");
            } else if (isButtonTouched(event, exitButton) && exitButton.isPushed()) {
                ((MainActivity) MainActivity.getGameContext()).finish(); // Close the app
            }

            startButton.setPushed(false);
            optionsButton.setPushed(false);
            exitButton.setPushed(false);
        }
    }

    private boolean isButtonTouched(MotionEvent motionEvent, CustomButton button){
        return button.getHitbox().contains(motionEvent.getX(), motionEvent.getY());
    }
}
