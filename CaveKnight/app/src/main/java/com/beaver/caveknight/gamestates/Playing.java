package com.beaver.caveknight.gamestates;

import static com.beaver.caveknight.helpers.GameConstants.Sprite.SCALE_MULTIPLIER;
import static com.beaver.caveknight.helpers.GameConstants.Sprite.X_OFFSET;
import static com.beaver.caveknight.helpers.GameConstants.Sprite.Y_OFFSET;
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
import com.beaver.caveknight.ui.CustomButton;
import com.beaver.caveknight.ui.PlayingUI;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;

    private final MapManager mapManager;

    private final Player player;
    private final Paint redPaint;
    private RectF weaponHitBox = null;

    private boolean isAttacking;
    private boolean isAttackChecked;
    private long attackStartTime;
    private static final long ATTACK_DURATION = 350;

    private final PlayingUI playingUI;

    private boolean doorwayJustPassed;

    private Entity[] listOfDrawables;
    private Boolean listOfEntitiesMade = false;

    public Playing(Game game) {
        super(game);

        mapManager = new MapManager(this);
        calculateStartCamera();

        player = new Player();

        playingUI = new PlayingUI(this);

        redPaint = new Paint();
        redPaint.setStrokeWidth(2);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);

        updateWeaponHitbox();
    }

    private void calculateStartCamera() {
        cameraX = (float) GAME_WIDTH / 2 - (float) mapManager.getMaxWidthCurrentMap() / 2;
        cameraY = (float) GAME_HEIGHT / 2 - (float) mapManager.getMaxHeightCurrentMap() / 2;
    }


    @Override
    public void update(double delta) {
        buildEntityList();
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForDoorway();

        updateWeaponHitbox();

        if (isAttacking) {
            if (!isAttackChecked) {
                checkAttack();
            }
            if (System.currentTimeMillis() - attackStartTime >= ATTACK_DURATION) {
                setAttacking(false);
                playingUI.getAttackButton().setPushed(false);
            }
        }

        for (Skeleton skeleton : mapManager.getCurrentMap().getSkeletonArrayList()) {
            if (skeleton.isActive()) {
                skeleton.update(delta, mapManager.getCurrentMap());
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
            if (!doorwayJustPassed)
                mapManager.changeMap(doorwayPlayerIsOn.getDoorwayConnectedTo());
        } else
            doorwayJustPassed = false;
    }

    private void checkAttack() {
        RectF attackBoxWithoutCamera = new RectF(weaponHitBox);
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        for (Skeleton s : mapManager.getCurrentMap().getSkeletonArrayList())
            if (attackBoxWithoutCamera.intersects(s.getHitbox().left, s.getHitbox().top, s.getHitbox().right, s.getHitbox().bottom))
                s.setActive(false);

        isAttackChecked = true;
    }

    private void updateWeaponHitbox() {
        PointF pos = getWeaponPosition();
        float width = getWeaponWidth();
        float height = getWeaponHeight();

        weaponHitBox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);

    }

    private float getWeaponWidth() {
        return switch (player.getFaceDir()){

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidth();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getHeight();

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    private float getWeaponHeight() {
        return switch (player.getFaceDir()){

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    private PointF getWeaponPosition() {
        return switch (player.getFaceDir()) {

            case GameConstants.Face_Dir.UP ->
                    new PointF(player.getHitbox().left - 0.5f * GameConstants.Sprite.SCALE_MULTIPLIER,
                            player.getHitbox().top - Weapons.BIG_SWORD.getHeight() - Y_OFFSET);

            case GameConstants.Face_Dir.DOWN ->
                    new PointF(player.getHitbox().left + 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER,
                            player.getHitbox().bottom);

            case GameConstants.Face_Dir.LEFT ->
                    new PointF(player.getHitbox().left - Weapons.BIG_SWORD.getHeight() - X_OFFSET,
                            player.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(player.getHitbox().right + X_OFFSET,
                            player.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    @Override
    public void render(Canvas c) {
        mapManager.drawTiles(c);

        if (listOfEntitiesMade != null && listOfEntitiesMade) {
            drawSortedEntities(c);
        }

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
        c.drawBitmap(Weapons.SHADOW.getWeaponImg(),
                player.getHitbox().left,
                player.getHitbox().bottom - 5 * SCALE_MULTIPLIER,
                null);

        c.drawBitmap(
                player.getGameCharType().getSprite(getAnimIndex(), player.getFaceDir()),
                player.getHitbox().left - X_OFFSET,
                player.getHitbox().top - Y_OFFSET,
                null);

        c.drawRect(player.getHitbox(), redPaint);

        if (isAttacking){
            drawWeapons(c);
        }

    }

    private int getAnimIndex(){
        if(isAttacking) return 4;
        return player.getAnimIndex();
    }

    private void drawWeapons(Canvas c) {
        c.rotate(getWeaponRotation(), weaponHitBox.left, weaponHitBox.top);

        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(),
                weaponHitBox.left + weaponRotationLeft(),
                weaponHitBox.top + weaponRotationTop(),
                null);

        c.rotate(getWeaponRotation() * -1, weaponHitBox.left, weaponHitBox.top);

        c.drawRect(weaponHitBox, redPaint);
    }

    private float weaponRotationTop() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.UP ->
                    - Weapons.BIG_SWORD.getHeight();
            default -> 0;
        };
    }

    private float weaponRotationLeft() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.RIGHT ->
                    - Weapons.BIG_SWORD.getWidth();
            default -> 0;
        };
    }

    private float getWeaponRotation() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;

            default -> 0;
        };
    }

    public void drawCharacter(Canvas canvas, Character c) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(),
                c.getHitbox().left + cameraX,
                c.getHitbox().bottom - 5 * SCALE_MULTIPLIER + cameraY,
                null);

        canvas.drawBitmap(
                c.getGameCharType().getSprite(c.getAnimIndex(), c.getFaceDir()),
                c.getHitbox().left + cameraX - X_OFFSET,
                c.getHitbox().top + cameraY - Y_OFFSET,
                null);

        canvas.drawRect(c.getHitbox().left + cameraX,
                c.getHitbox().top + cameraY,
                c.getHitbox().right + cameraX,
                c.getHitbox().bottom + cameraY,
                redPaint);
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer) return;

        float baseSpeed = (float) (delta * 300);
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0) player.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else player.setFaceDir(GameConstants.Face_Dir.LEFT);
        } else {
            if (lastTouchDiff.y > 0) player.setFaceDir(GameConstants.Face_Dir.DOWN);
            else player.setFaceDir(GameConstants.Face_Dir.UP);
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
        }
        else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, cameraX * -1, mapManager.getCurrentMap())) {
                cameraY += deltaY;
            }

            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, cameraY * -1, mapManager.getCurrentMap())) {
                cameraX += deltaX;
            }
        }
    }

    public void setGameStateToMainMenu(){
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

    private boolean isButtonTouched(MotionEvent motionEvent, CustomButton button){
        return button.getHitbox().contains(motionEvent.getX(), motionEvent.getY());
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
        if (isAttacking) {
            attackStartTime = System.currentTimeMillis(); // Record when the attack starts
        } else {
            isAttackChecked = false;
        }
    }


    public void setDoorwayJustPassed(boolean doorwayJustPassed) {
        this.doorwayJustPassed = doorwayJustPassed;

    }
}
