package com.beaver.caveknight.entities;

import static com.beaver.caveknight.helpers.GameConstants.Sprite.HITBOX_SIZE;
import static com.beaver.caveknight.helpers.GameConstants.Sprite.X_OFFSET;
import static com.beaver.caveknight.helpers.GameConstants.Sprite.Y_OFFSET;

import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.helpers.GameConstants;

public abstract class Character extends Entity {
    protected int animTick;
    protected int animIndex;
    protected int faceDirection = GameConstants.Face_Dir.DOWN;

    protected final GameCharacters gameCharType;

    protected boolean isAttacking;
    protected boolean  isAttackChecked;
    private long attackStartTime;
    private RectF attackBox = null;
    private final int attackDamage;

    private int maxHealth;
    private int currentHealth;

    public Character(PointF pos, GameCharacters gameCharType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.gameCharType = gameCharType;
        attackDamage = setAttackDamage();

        updateWepHitbox();
    }

    protected void setStartHealth(int health) {
        maxHealth = health;
        currentHealth = maxHealth;
    }

    public void resetCharacterHealth() {
        currentHealth = maxHealth;
    }

    public void damageCharacter(int damage) {
        this.currentHealth -= damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    private int setAttackDamage() {
        return switch (gameCharType) {
            case PLAYER -> 50;
            case SKELETON -> 25;
        };
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
        if (isAttacking) return 4;
        return animIndex;
    }

    public int getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(int faceDirection) {
        this.faceDirection = faceDirection;
    }

    public long getAttackStartTime() {
        return attackStartTime;
    }

    public GameCharacters getGameCharType() {
        return gameCharType;
    }

    public void updateWepHitbox() {
        PointF pos = getWeaponPosition();
        float w = getWeaponWidth();
        float h = getWeaponHeight();

        attackBox = new RectF(pos.x, pos.y, pos.x + w, pos.y + h);
    }

    public float getWeaponWidth() {
        return switch (getFaceDirection()) {

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDirection());
        };
    }

    public float getWeaponHeight() {
        //Must keep in mind, there is a rotation active
        return switch (getFaceDirection()) {

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDirection());
        };
    }

    public PointF getWeaponPosition() {
        return switch (getFaceDirection()) {

            case GameConstants.Face_Dir.UP ->
                    new PointF(getHitbox().left - 0.5f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().top - Weapons.BIG_SWORD.getHeight() - Y_OFFSET);

            case GameConstants.Face_Dir.DOWN ->
                    new PointF(getHitbox().left + 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().bottom);

            case GameConstants.Face_Dir.LEFT ->
                    new PointF(getHitbox().left - Weapons.BIG_SWORD.getHeight() - X_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(getHitbox().right + X_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDirection());
        };

    }

    public float weaponRotationAdjustTop() {
        return switch (getFaceDirection()) {
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.UP ->
                    -Weapons.BIG_SWORD.getHeight();
            default -> 0;
        };
    }

    public float weaponRotationAdjustLeft() {
        return switch (getFaceDirection()) {
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.RIGHT ->
                    -Weapons.BIG_SWORD.getWidth();
            default -> 0;
        };
    }

    public float getWeaponRotation() {
        return switch (getFaceDirection()) {
            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;
            default -> 0;
        };

    }

    public RectF getAttackBox() {
        return attackBox;
    }

    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
        if (isAttacking) {
            attackStartTime = System.currentTimeMillis(); // Record when the attack starts
        } else {
            isAttackChecked = false;
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isAttackChecked() {
        return isAttackChecked;
    }

    public void setAttackChecked(boolean attackChecked) {
        this.isAttackChecked = attackChecked;
    }

    public int getDamage() {

        return attackDamage;
    }


}
