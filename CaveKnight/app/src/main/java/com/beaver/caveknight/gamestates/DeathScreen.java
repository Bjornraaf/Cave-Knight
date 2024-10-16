package com.beaver.caveknight.gamestates;

import static com.beaver.caveknight.environments.WaveManager.getWavesSurvived;
import static com.beaver.caveknight.helpers.ScoreManager.getFinalScore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.beaver.caveknight.helpers.interfaces.GameStateInterface;
import com.beaver.caveknight.main.Game;
import com.beaver.caveknight.main.MainActivity;
import com.beaver.caveknight.ui.ButtonImages;
import com.beaver.caveknight.ui.CustomButton;
import com.beaver.caveknight.ui.GameImages;

public class DeathScreen extends BaseState implements GameStateInterface {

    private final CustomButton btnReplay;
    private final CustomButton btnMainMenu;

    private final int menuX = MainActivity.GAME_WIDTH / 2 - GameImages.DEATHSCREEN_MENUBG.getImage().getWidth() / 2;
    private final int menuY = 200;

    private final Paint textPaint;

    public DeathScreen(Game game) {
        super(game);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        int buttonsX = menuX + GameImages.DEATHSCREEN_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
        int btnReplayY = menuY + 250;
        btnReplay = new CustomButton(buttonsX, btnReplayY, ButtonImages.MENU_REPLAY.getWidth(), ButtonImages.MENU_REPLAY.getHeight());

        int btnMainMenuY = btnReplayY + 150;
        btnMainMenu = new CustomButton(buttonsX, btnMainMenuY, ButtonImages.MENU_MENU.getWidth(), ButtonImages.MENU_MENU.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        drawBackground(c);
        drawButtons(c);
        drawScore(c);
        drawWavesSurvived(c);
    }

    private void drawButtons(Canvas c) {
        c.drawBitmap(ButtonImages.MENU_REPLAY.getButtonImg(btnReplay.isPushed()),
                btnReplay.getHitbox().left,
                btnReplay.getHitbox().top,
                null);

        c.drawBitmap(ButtonImages.MENU_MENU.getButtonImg(btnMainMenu.isPushed()),
                btnMainMenu.getHitbox().left,
                btnMainMenu.getHitbox().top,
                null);
    }

    private void drawBackground(Canvas c) {
        c.drawBitmap(GameImages.DEATHSCREEN_MENUBG.getImage(),
                menuX, menuY, null);
    }

    private void drawScore(Canvas c) {
        String scoreText = "Final Score: " + getFinalScore(); // Create the score text

        float textWidth = textPaint.measureText(scoreText);

        float scoreX = menuX + (GameImages.DEATHSCREEN_MENUBG.getImage().getWidth() - textWidth) / 2;

        float scoreY = btnReplay.getHitbox().top - 65;

        c.drawText(scoreText, scoreX, scoreY, textPaint);
    }

    private void drawWavesSurvived(Canvas c) {
        int wavesSurvived = getWavesSurvived();
        String wavesText = "Waves Survived: " + wavesSurvived;

        textPaint.setTextSize(40);
        float textWidth = textPaint.measureText(wavesText);
        float wavesX = menuX + (GameImages.DEATHSCREEN_MENUBG.getImage().getWidth() - textWidth) / 2;
        float wavesY = btnReplay.getHitbox().top - 17;

        // Draw the waves survived on the canvas
        c.drawText(wavesText, wavesX, wavesY, textPaint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isButtonTouched(event, btnReplay))
                btnReplay.setPushed(true);
            else if (isButtonTouched(event, btnMainMenu))
                btnMainMenu.setPushed(true);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isButtonTouched(event, btnReplay)) {
                if (btnReplay.isPushed()){
                    game.setCurrentGameState(Game.GameState.PLAYING);
                }
            } else if (isButtonTouched(event, btnMainMenu)) {
                if (btnMainMenu.isPushed()) {
                    game.setCurrentGameState(Game.GameState.MAINMENU);
                }
            }

            btnReplay.setPushed(false);
            btnMainMenu.setPushed(false);
        }

    }
}
