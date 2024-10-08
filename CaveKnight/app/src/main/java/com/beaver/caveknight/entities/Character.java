package com.beaver.caveknight.entities;

import static com.beaver.caveknight.helpers.GameConstants.Sprite.HITBOX_SIZE;

import android.graphics.PointF;

import com.beaver.caveknight.helpers.GameConstants;

public abstract class Character extends Entity {
    protected int animTick, animIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;
    protected final GameCharacters gameCharType;

    public Character(PointF pos, GameCharacters gameCharType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.gameCharType = gameCharType;
    }

    protected void updateAnimation() {
        animTick++;
        if (animTick >= GameConstants.Animation.SPEED) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GameConstants.Animation.AMOUNT)
                animIndex = 0;
        }
    }

    public void resetAnimation() {
        animTick = 0;
        animIndex = 0;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public int getFaceDir() {
        return faceDir;
    }

    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }

    public GameCharacters getGameCharType() {
        return gameCharType;
    }
}
