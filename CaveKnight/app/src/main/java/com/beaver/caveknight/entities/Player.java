package com.beaver.caveknight.entities;

import static com.beaver.caveknight.main.MainActivity.GAME_HEIGHT;
import static com.beaver.caveknight.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

public class Player extends Character {

    private final PointF startingPosition;
    private final int startingHealth;

    public Player() {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.PLAYER);

        // Save the starting position and health
        this.startingPosition = new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2);
        this.startingHealth = 300;

        // Set initial health
        setStartHealth(startingHealth);
    }

    public void update(double delta, boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }

    public void reset() {
        resetCharacterHealth();

        setPosition(startingPosition.x, startingPosition.y);

        setAttacking(false);
        setAttackChecked(false);

        resetAnimation();
    }

    public void setPosition(float x, float y) {
        this.hitbox.left = x;
        this.hitbox.top = y;
    }
}