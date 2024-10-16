package com.beaver.caveknight.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.beaver.caveknight.entities.Player;
import com.beaver.caveknight.gamestates.Playing;
import com.beaver.caveknight.main.Game;

public class PlayingUI {

    private final Playing playing;

    //UI
    private final PointF joystickPosition = new PointF(250, 800);
    private final PointF attackButtonPosition = new PointF(1700, 800);
    private final float radius = 150;
    private final Paint circlePaint;

    private final Paint scorePaint;

    //Buttons
    private final CustomButton homeButton;
    private final CustomButton attackButton;
    private final CustomButton abilityButton;

    //Multitouch
    private int joystickPointerId = -1;
    private int attackButtonPointerID = -1;
    private int abilityButtonPointerID = -1;
    private boolean touchDown;



    public PlayingUI(Playing playing) {
        this.playing = playing;

        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);

        homeButton = new CustomButton(5,5, ButtonImages.HOME_BUTTON.getWidth(), ButtonImages.HOME_BUTTON.getHeight());

        attackButton = new CustomButton(attackButtonPosition.x, attackButtonPosition.y, ButtonImages.ATTACK_BUTTON.getWidth(), ButtonImages.ATTACK_BUTTON.getHeight());

        PointF abilityPosition = new PointF(attackButtonPosition.x + 200, attackButtonPosition.y);
        abilityButton = new CustomButton(abilityPosition.x + 25, abilityPosition.y, ButtonImages.ABILITY_BUTTON.getWidth(), ButtonImages.ABILITY_BUTTON.getHeight());
    }

    public void draw(Canvas c) {
        drawUI(c);
    }

    private void drawUI(Canvas c) {
        c.drawCircle(joystickPosition.x, joystickPosition.y, radius, circlePaint);

        c.drawBitmap(ButtonImages.ATTACK_BUTTON.getButtonImg(attackButton.isPushed(attackButton.getPointerId())),
                attackButton.getHitbox().left + 25,
                attackButton.getHitbox().top + 25,
                null);

        c.drawBitmap(ButtonImages.HOME_BUTTON.getButtonImg(homeButton.isPushed(homeButton.getPointerId())),
                homeButton.getHitbox().left + 25,
                homeButton.getHitbox().top + 25,
                null);

        c.drawBitmap(ButtonImages.ABILITY_BUTTON.getButtonImg(abilityButton.isPushed(abilityButton.getPointerId())),
                abilityButton.getHitbox().left + 25,
                abilityButton.getHitbox().top + 25,
                null);

        drawHealth(c);
    }

    private void drawHealth(Canvas c) {
        Player player = playing.getPlayer();

        for (int i = 0; i < player.getMaxHealth() / 100; i++) {

            int healthIconX = 175;
            int healthIconY = 25;

            int x = healthIconX + 100 * i;
            int heartValue = player.getCurrentHealth() - 100 * i;

            if (heartValue < 100) {
                if (heartValue <= 0)
                    c.drawBitmap(HealthIcons.HEART_EMPTY.getHealthIcon(), x, healthIconY, null);
                else if (heartValue == 25)
                    c.drawBitmap(HealthIcons.HEART_1Q.getHealthIcon(), x, healthIconY, null);
                else if (heartValue == 50)
                    c.drawBitmap(HealthIcons.HEART_HALF.getHealthIcon(), x, healthIconY, null);
                else
                    c.drawBitmap(HealthIcons.HEART_3Q.getHealthIcon(), x, healthIconY, null);
            } else
                c.drawBitmap(HealthIcons.HEART_FULL.getHealthIcon(), x, healthIconY, null);
        }
    }

    public void drawScore(Canvas c) {
        String scoreText = "Score: " + playing.getScoreManager().getScore();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(75);
        c.drawText(scoreText, c.getWidth() - 400, 100, scorePaint);
    }

    private boolean isInsideRadius(PointF eventPos, PointF circle) {
        float a = Math.abs(eventPos.x - circle.x);
        float b = Math.abs(eventPos.y - circle.y);
        float c = (float) Math.hypot(a, b);

        return c <= radius;
    }

    private boolean checkInsideAttackBtn(PointF eventPos) {
        return isInsideRadius(eventPos, attackButtonPosition);
    }

    private boolean checkInsideJoyStick(PointF eventPos, int pointerId){

        if (isInsideRadius(eventPos, joystickPosition)) {
            touchDown = true;
            joystickPointerId = pointerId;
            return true;
        }

        return false;
    }

    public void touchEvents(MotionEvent event) {
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));

        switch (action) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (checkInsideJoyStick(eventPos, pointerId)) {
                    touchDown = true;
                } else if (isIn(eventPos, attackButton)) {
                    if (attackButtonPointerID < 0) {
                        playing.getPlayer().setAttacking(true);
                        attackButton.setPushed(true, pointerId);
                        attackButtonPointerID = pointerId;
                    }
                } else if (isIn(eventPos, abilityButton)) {
                    if (abilityButtonPointerID < 0) {
//                        playing.setSliceAttacking(true);
                        abilityButton.setPushed(true, pointerId);
                        abilityButtonPointerID = pointerId;
                    }
                } else {
                    if (isIn(eventPos, homeButton)) {
                        homeButton.setPushed(true, pointerId);
                    }
                }
            }

            case MotionEvent.ACTION_MOVE -> {
                if (touchDown)
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == joystickPointerId) {
                            float xDiff = event.getX(i) - joystickPosition.x;
                            float yDiff = event.getY(i) - joystickPosition.y;
                            playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                        }
                    }
            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (pointerId == joystickPointerId) {
                    resetJoystick();
                } else {
                    if (isIn(eventPos, homeButton) && homeButton.isPushed(pointerId)) {
                        resetJoystick();
                        playing.setGameStateToMainMenu();
                    }
                    homeButton.unPush(pointerId);

                    if (pointerId == attackButtonPointerID) {
                        playing.getPlayer().setAttacking(false);
                        attackButton.unPush(pointerId);
                        attackButtonPointerID = -1;
                    }

                    if (pointerId == abilityButtonPointerID) {
                        abilityButton.unPush(pointerId);
                        abilityButtonPointerID = -1;
                    }
                }
            }
        }
    }

    private void resetJoystick() {
        touchDown = false;
        playing.setPlayerMoveFalse();
        joystickPointerId = -1;
    }

    private boolean isIn(PointF eventPos, CustomButton b) {
        return b.getHitbox().contains(eventPos.x, eventPos.y);
    }

    public CustomButton getAttackButton() {
        return attackButton;
    }

    public CustomButton getAbilityButton() {
        return abilityButton;
    }

}
