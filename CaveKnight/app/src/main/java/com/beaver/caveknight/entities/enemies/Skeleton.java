package com.beaver.caveknight.entities.enemies;

import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.entities.Character;
import com.beaver.caveknight.entities.GameCharacters;
import com.beaver.caveknight.entities.Player;
import com.beaver.caveknight.environments.GameMap;
import com.beaver.caveknight.helpers.GameConstants;
import com.beaver.caveknight.helpers.HelpMethods;

import java.util.Random;

public class Skeleton extends Character {
    private long lastDirChange = System.currentTimeMillis();
    private final Random random = new Random();
    private boolean moving = true, preparingAttack;
    private long timerBeforeAttack, timerAttackDuration;


    public Skeleton(PointF pos) {
        super(pos, GameCharacters.SKELETON);
        setStartHealth(100);
    }

    public void update(double delta, GameMap gameMap) {
        if (moving) {
            updateMove(delta, gameMap);
            updateAnimation();
        }
        if (preparingAttack) {
            checkTimeToAttackTimer();
        }
        if (isAttacking) {
            updateAttackTimer();
        }
    }

    public void prepareAttack(Player player, float cameraX, float cameraY) {
        timerBeforeAttack = System.currentTimeMillis();
        preparingAttack = true;
        moving = false;
        turnTowardsPlayer(player, cameraX, cameraY);
    }

    private void turnTowardsPlayer(Player player, float cameraX, float cameraY) {
        float xDelta = hitbox.left - (player.getHitbox().left - cameraX);
        float yDelta = hitbox.top - (player.getHitbox().top - cameraY);

        if (Math.abs(xDelta) > Math.abs(yDelta)) {
            if (hitbox.left < (player.getHitbox().left - cameraX)){
                faceDirection = GameConstants.Face_Dir.RIGHT;
            }
            else {
                faceDirection = GameConstants.Face_Dir.LEFT;
            }
        }
        else {
            if (hitbox.top < (player.getHitbox().top - cameraY)){
                faceDirection = GameConstants.Face_Dir.DOWN;
            }
            else{
                faceDirection = GameConstants.Face_Dir.UP;
            }
        }
    }

    private void updateAttackTimer() {
        long timeForAttackDuration = 250;
        if (timerAttackDuration + timeForAttackDuration < System.currentTimeMillis()) {
            setAttacking(false);
            resetAnimation();
            moving = true;
        }
    }

    private void checkTimeToAttackTimer() {
        long timeToAttack = 500;
        if (timerBeforeAttack + timeToAttack < System.currentTimeMillis()) {
            setAttacking(true);
            preparingAttack = false;
            timerAttackDuration = System.currentTimeMillis();
        }
    }

    private void updateMove(double delta, GameMap gameMap) {
        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDirection = random.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }

        float deltaChange = (float) (delta * 300);

        switch (faceDirection) {
            case GameConstants.Face_Dir.DOWN:
                if (HelpMethods.CanWalkHere(hitbox, 0, deltaChange, gameMap)) {
                    hitbox.top += deltaChange;
                    hitbox.bottom += deltaChange;
                } else
                    faceDirection = GameConstants.Face_Dir.UP;
                break;

            case GameConstants.Face_Dir.UP:
                if (HelpMethods.CanWalkHere(hitbox, 0, -deltaChange, gameMap)) {
                    hitbox.top -= deltaChange;
                    hitbox.bottom -= deltaChange;
                } else
                    faceDirection = GameConstants.Face_Dir.DOWN;
                break;

            case GameConstants.Face_Dir.RIGHT:
                if (HelpMethods.CanWalkHere(hitbox, deltaChange, 0, gameMap)) {
                    hitbox.left += deltaChange;
                    hitbox.right += deltaChange;
                } else
                    faceDirection = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                if (HelpMethods.CanWalkHere(hitbox, -deltaChange, 0, gameMap)) {
                    hitbox.left -= deltaChange;
                    hitbox.right -= deltaChange;
                } else
                    faceDirection = GameConstants.Face_Dir.RIGHT;
                break;
        }
    }

    public boolean isPreparingAttack() {
        return preparingAttack;
    }

    public void setSkeletonInactive() {
        active = false;
        hitbox.set(0, 0, 0, 0);
    }

    public RectF getHitbox() {
        return active ? super.getHitbox() : new RectF(0, 0, 0, 0);
    }
}