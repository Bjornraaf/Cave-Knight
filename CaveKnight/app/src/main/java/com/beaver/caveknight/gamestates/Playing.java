package com.beaver.caveknight.gamestates;

import static com.beaver.caveknight.helpers.GameConstants.Attack.ATTACK_DURATION;
import static com.beaver.caveknight.helpers.GameConstants.Sprite.X_OFFSET;
import static com.beaver.caveknight.main.MainActivity.GAME_HEIGHT;
import static com.beaver.caveknight.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.beaver.caveknight.entities.Character;
import com.beaver.caveknight.entities.Entity;
import com.beaver.caveknight.entities.Player;
import com.beaver.caveknight.entities.Weapons;
import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.enemies.Skeleton;
import com.beaver.caveknight.entities.objects.GameObject;
import com.beaver.caveknight.environments.Doorway;
import com.beaver.caveknight.environments.MapManager;
import com.beaver.caveknight.helpers.GameConstants;
import com.beaver.caveknight.helpers.HelpMethods;
import com.beaver.caveknight.helpers.interfaces.GameStateInterface;
import com.beaver.caveknight.main.Game;
import com.beaver.caveknight.ui.PlayingUI;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;

    private final MapManager mapManager;
    private final Player player;
    private final PlayingUI playingUI;

    private final Paint redPaint;

    private boolean doorwayJustPassed;
    private Entity[] listOfDrawables;
    private boolean listOfEntitiesMade;

    public Playing(Game game) {
        super(game);

        mapManager = new MapManager(this);
        calcStartCameraValues();

        player = new Player();

        playingUI = new PlayingUI(this);

        redPaint = new Paint();
        redPaint.setStrokeWidth(2);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);
    }

    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f - mapManager.getMaxWidthCurrentMap() / 2f;
        cameraY = GAME_HEIGHT / 2f - mapManager.getMaxHeightCurrentMap() / 2f;
    }


    @Override
    public void update(double delta) {
        buildEntityList();
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForDoorway();

        if (player.isAttacking()) {
            if (!player.isAttackChecked()) {
                checkPlayerAttack();

            }
            if (System.currentTimeMillis() - player.getAttackStartTime() >= ATTACK_DURATION) {
                player.setAttacking(false);
                playingUI.getAttackButton().setPushed(false);
            }
        }


        for (Skeleton skeleton : mapManager.getCurrentMap().getSkeletonArrayList())
            if (skeleton.isActive()) {
                skeleton.update(delta, mapManager.getCurrentMap());
                if (skeleton.isAttacking()) {
                    if (!skeleton.isAttackChecked()) {
                        checkEnemyAttack(skeleton);
                    }
                } else if (!skeleton.isPreparingAttack()) {
                    if (HelpMethods.IsPlayerCloseForAttack(skeleton, player, cameraY, cameraX)) {
                        skeleton.prepareAttack(player, cameraX, cameraY);
                    }
                }
            }


        sortArray();

    }


    private void buildEntityList() {
        listOfDrawables = mapManager.getCurrentMap().getDrawableList();
        listOfDrawables[listOfDrawables.length - 1] = player;
        listOfEntitiesMade = true;
    }

    private void sortArray() {
        player.setLastCameraValueY(cameraY);
        Arrays.sort(listOfDrawables);
    }

    public void setCameraValues(PointF cameraPos) {
        this.cameraX = cameraPos.x;
        this.cameraY = cameraPos.y;
    }

    private void checkForDoorway() {
        Doorway doorwayPlayerIsOn = mapManager.isPlayerOnDoorway(player.getHitbox());

        if (doorwayPlayerIsOn != null) {
            if (!doorwayJustPassed) mapManager.changeMap(doorwayPlayerIsOn.getDoorwayConnectedTo());
        } else doorwayJustPassed = false;

    }

    public void setDoorwayJustPassed(boolean doorwayJustPassed) {
        this.doorwayJustPassed = doorwayJustPassed;
    }


    private void checkEnemyAttack(Character character) {
        character.updateWepHitbox();
        RectF playerHitbox = new RectF(player.getHitbox());
        playerHitbox.left -= cameraX;
        playerHitbox.top -= cameraY;
        playerHitbox.right -= cameraX;
        playerHitbox.bottom -= cameraY;
        if (RectF.intersects(character.getAttackBox(), playerHitbox)) {
            System.out.println("Enemy Hit Player!");
        } else {
            System.out.println("Enemy Missed Player!");
        }
        character.setAttackChecked(true);
    }

    private void checkPlayerAttack() {

        RectF attackBoxWithoutCamera = new RectF(player.getAttackBox());
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        for (Skeleton s : mapManager.getCurrentMap().getSkeletonArrayList())
            if (attackBoxWithoutCamera.intersects(s.getHitbox().left, s.getHitbox().top, s.getHitbox().right, s.getHitbox().bottom))
                s.setActive(false);

        player.setAttackChecked(true);
    }


    @Override
    public void render(Canvas c) {
        mapManager.drawTiles(c);
        if (listOfEntitiesMade)
            drawSortedEntities(c);

        playingUI.draw(c);
    }

    private void drawSortedEntities(Canvas c) {
        for (Entity e : listOfDrawables) {
            if (e instanceof Skeleton skeleton) {
                if (skeleton.isActive()) drawCharacter(c, skeleton);
            } else if (e instanceof GameObject gameObject) {
                mapManager.drawObject(c, gameObject);
            } else if (e instanceof Building building) {
                mapManager.drawBuilding(c, building);
            } else if (e instanceof Player) {
                drawPlayer(c);
            }
        }
    }


    private void drawPlayer(Canvas c) {
        c.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left, player.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        c.drawBitmap(player.getGameCharType().getSprite(player.getAnimIndex(), player.getFaceDirection()), player.getHitbox().left - X_OFFSET, player.getHitbox().top - GameConstants.Sprite.Y_OFFSET, null);
        c.drawRect(player.getHitbox(), redPaint);
        if (player.isAttacking()) drawWeapon(c, player);
    }


    private void drawWeapon(Canvas c, Character character) {
        c.rotate(character.getWeaponRotation(), character.getAttackBox().left, character.getAttackBox().top);
        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + character.weaponRotationAdjustLeft(), character.getAttackBox().top + character.weaponRotationAdjustTop(), null);
        c.rotate(character.getWeaponRotation() * -1, character.getAttackBox().left, character.getAttackBox().top);
    }

    private void drawEnemyWeapon(Canvas c, Character character) {
        c.rotate(character.getWeaponRotation(), character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + cameraX + character.weaponRotationAdjustLeft(), character.getAttackBox().top + cameraY + character.weaponRotationAdjustTop(), null);
        c.rotate(character.getWeaponRotation() * -1, character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
    }


    public void drawCharacter(Canvas canvas, Character c) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), c.getHitbox().left + cameraX, c.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(c.getGameCharType().getSprite(c.getAnimIndex(), c.getFaceDirection()), c.getHitbox().left + cameraX - X_OFFSET, c.getHitbox().top + cameraY - GameConstants.Sprite.Y_OFFSET, null);
        canvas.drawRect(c.getHitbox().left + cameraX, c.getHitbox().top + cameraY, c.getHitbox().right + cameraX, c.getHitbox().bottom + cameraY, redPaint);

        if (c.isAttacking())
            drawEnemyWeapon(canvas, c);
    }

    private void updatePlayerMove(double delta) {

        if (!movePlayer || player.isAttacking()) return;

        float baseSpeed = (float) (delta * 300);
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0) player.setFaceDirection(GameConstants.Face_Dir.RIGHT);
            else player.setFaceDirection(GameConstants.Face_Dir.LEFT);
        } else {
            if (lastTouchDiff.y > 0) player.setFaceDirection(GameConstants.Face_Dir.DOWN);
            else player.setFaceDirection(GameConstants.Face_Dir.UP);
        }

        if (lastTouchDiff.x < 0) xSpeed *= -1;
        if (lastTouchDiff.y < 0) ySpeed *= -1;

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float deltaCameraX = cameraX * -1 + deltaX * -1;
        float deltaCameraY = cameraY * -1 + deltaY * -1;

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, mapManager.getCurrentMap())) {
            cameraX += deltaX;
            cameraY += deltaY;
        } else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, cameraX * -1, mapManager.getCurrentMap()))
                cameraY += deltaY;

            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, cameraY * -1, mapManager.getCurrentMap()))
                cameraX += deltaX;
        }
    }

    public void setGameStateToMainMenu() {
        game.setCurrentGameState(Game.GameState.MENU);
    }

    public void setPlayerMoveTrue(PointF lastTouchDiff) {
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse() {
        movePlayer = false;
        player.resetAnimation();
    }

    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    public Player getPlayer() {
        return player;
    }
}
