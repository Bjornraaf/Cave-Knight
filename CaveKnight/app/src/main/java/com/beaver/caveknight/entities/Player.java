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

    // Update player state each frame
    public void update(double delta, boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }

    // Reset method to restore the player to the initial state
    public void reset() {
        // Reset player's health
        resetCharacterHealth();

        // Reset player's position
        setPosition(startingPosition.x, startingPosition.y);

        // Reset attack-related flags
        setAttacking(false);
        setAttackChecked(false);

        // Reset player's animation (if applicable)
        resetAnimation();
    }

    public void setPosition(float x, float y) {
        this.hitbox.left = x;
        this.hitbox.top = y;
    }
}