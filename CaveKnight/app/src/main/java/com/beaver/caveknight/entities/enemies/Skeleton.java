package com.beaver.caveknight.entities.enemies;

import static com.beaver.caveknight.main.MainActivity.GAME_HEIGHT;
import static com.beaver.caveknight.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

import com.beaver.caveknight.entities.Character;
import com.beaver.caveknight.entities.GameCharacters;
import com.beaver.caveknight.helpers.GameConstants;

import java.util.Random;

public class Skeleton extends Character {
    private long lastDirChange = System.currentTimeMillis();
    private final Random random = new Random();

    public Skeleton(PointF pos) {
        super(pos, GameCharacters.SKELETON);
    }

    public void update(double delta) {
        updateMove(delta);
        updateAnimation();

    }

    private void updateMove(double delta) {
        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDir = random.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }

        switch (faceDir) {
            case GameConstants.Face_Dir.DOWN:
                hitbox.top += (float) (delta * 300);
                hitbox.bottom += (float) (delta * 300);
                if (hitbox.top >= GAME_HEIGHT)
                    faceDir = GameConstants.Face_Dir.UP;
                break;

            case GameConstants.Face_Dir.UP:
                hitbox.top -= (float) (delta * 300);
                hitbox.bottom -= (float) (delta * 300);
                if (hitbox.top <= 0)
                    faceDir = GameConstants.Face_Dir.DOWN;
                break;

            case GameConstants.Face_Dir.RIGHT:
                hitbox.left += (float) (delta * 300);
                hitbox.right += (float) (delta * 300);
                if (hitbox.left >= GAME_WIDTH)
                    faceDir = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                hitbox.left -= (float) (delta * 300);
                hitbox.right -= (float) (delta * 300);
                if (hitbox.left <= 0)
                    faceDir = GameConstants.Face_Dir.RIGHT;
                break;
        }
    }
}
