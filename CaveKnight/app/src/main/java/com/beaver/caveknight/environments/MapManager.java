package com.beaver.caveknight.environments;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.buildings.Buildings;
import com.beaver.caveknight.entities.objects.GameObject;
import com.beaver.caveknight.entities.objects.GameObjects;
import com.beaver.caveknight.gamestates.Playing;
import com.beaver.caveknight.helpers.GameConstants;
import com.beaver.caveknight.helpers.HelpMethods;
import com.beaver.caveknight.main.MainActivity;

import java.util.ArrayList;

public class MapManager {

    private GameMap currentMap, outsideMap, insideMap;
    private float cameraX, cameraY;
    private final Playing playing;

    public MapManager(Playing playing) {
        this.playing = playing;
        initTestMap();
    }

    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canMoveHere(float x, float y) {
        if (x < 0 || y < 0)
            return false;

        return !(x >= getMaxWidthCurrentMap()) && !(y >= getMaxHeightCurrentMap());
    }

    public int getMaxWidthCurrentMap() {
        return currentMap.getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMaxHeightCurrentMap() {
        return currentMap.getArrayHeight() * GameConstants.Sprite.SIZE;
    }

    public void drawTiles(Canvas c) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                c.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }

    public void drawObject(Canvas c, GameObject go) {
        c.drawBitmap(go.getObjectType().getObjectImg(), go.getHitbox().left + cameraX, go.getHitbox().top - go.getObjectType().getHitboxRoof() + cameraY, null);
    }

    public void drawBuilding(Canvas c, Building b) {
        c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y - b.getBuildingType().getHitboxRoof() + cameraY, null);
    }

    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox, cameraX, cameraY))
                return doorway;

        return null;
    }

    public void changeMap(Doorway doorwayTarget) {
        this.currentMap = doorwayTarget.getGameMapLocatedIn();

        float cX = MainActivity.GAME_WIDTH / 2f - doorwayTarget.getPosOfDoorway().x + GameConstants.Sprite.HITBOX_SIZE / 2f;
        float cY = MainActivity.GAME_HEIGHT / 2f - doorwayTarget.getPosOfDoorway().y + GameConstants.Sprite.HITBOX_SIZE / 2f;

        playing.setCameraValues(new PointF(cX, cY));
        cameraX = cX;
        cameraY = cY;

        playing.setDoorwayJustPassed(true);
    }

    private void initTestMap() {

        int[][] outsideArray = {
                {454, 276, 275, 275, 275, 275, 275, 279, 275, 275, 275, 275, 275, 275, 297, 110, 0, 1, 1, 1, 1, 1, 2, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 279, 275, 275, 275, 275, 275, 275, 275, 279, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 279, 279, 275, 278, 275, 275, 275, 275, 279, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 275, 275, 279, 275, 276, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 278, 275, 275, 275, 276, 275, 275, 275, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 279, 275, 279, 275, 279, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 276, 277, 275, 275, 275, 275, 279, 275, 279, 275, 279, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 275, 278, 275, 275, 275, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 279, 275, 275, 275, 275, 275, 275, 279, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 279, 275, 275, 275, 275, 279, 275, 275, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 279, 275, 275, 279, 275, 275, 275, 275, 275, 275, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 279, 275, 279, 275, 279, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 275, 278, 275, 275, 275, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 279, 275, 279, 275, 279, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 276, 277, 275, 275, 275, 275, 279, 275, 279, 275, 279, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 275, 278, 275, 275, 275, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 279, 275, 275, 275, 275, 275, 275, 279, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 275, 275, 275, 278, 275, 275, 275, 275, 279, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 275, 279, 275, 275, 275, 275, 275, 275, 279, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 275, 279, 275, 275, 275, 275, 279, 275, 275, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 279, 275, 275, 279, 275, 275, 275, 275, 275, 275, 275, 275, 275, 297, 110, 22, 23, 23, 23, 23, 23, 24, 110, 132, 275, 279, 275, 275, 275, 275, 454},
                {454, 275, 279, 275, 275, 275, 279, 275, 275, 275, 275, 275, 275, 275, 297, 110, 44, 45, 45, 45, 45, 45, 46, 110, 132, 275, 279, 275, 275, 275, 275, 454}
        };

        int[][] insideArray = {
                {384, 387, 387, 387, 387, 387, 388},
                {406,  275, 276, 276, 276, 277, 410},
                {406,  297, 298, 298, 298, 299, 410},
                {406,  297, 298, 298, 298, 299, 410},
                {406,  297, 298, 298, 298, 299, 410},
                {406,  319, 320, 320, 320, 321, 410},
                {472, 475, 473, 394, 474, 475, 476}
        };

        ArrayList<Building> buildingArrayList = new ArrayList<>();
        buildingArrayList.add(new Building(new PointF(200, 200), Buildings.HOUSE_ONE));
//        buildingArrayList.add(new Building(new PointF(700, 200), Buildings.CAVE_ONE));

        ArrayList<GameObject> gameObjectArrayList = new ArrayList<>();
        gameObjectArrayList.add(new GameObject(new PointF(600, 400), GameObjects.STATUE_ANGRY_YELLOW));
        gameObjectArrayList.add(new GameObject(new PointF(1000, 400), GameObjects.STATUE_ANGRY_YELLOW));
        gameObjectArrayList.add(new GameObject(new PointF(50, 50), GameObjects.BASKET_FULL_RED_FRUIT));
        gameObjectArrayList.add(new GameObject(new PointF(800, 800), GameObjects.OVEN_SNOW_YELLOW));
        gameObjectArrayList.add(new GameObject(new PointF(800, 800), GameObjects.OVEN_SNOW_YELLOW));


        insideMap = new GameMap(insideArray, MapTiles.INSIDE, null, null,HelpMethods.GetSkeletonsRandomized(2, insideArray));
        outsideMap = new GameMap(outsideArray, MapTiles.OUTSIDE, buildingArrayList, gameObjectArrayList, HelpMethods.GetSkeletonsRandomized(5, outsideArray));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 0),
                insideMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        currentMap = outsideMap;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }
}
