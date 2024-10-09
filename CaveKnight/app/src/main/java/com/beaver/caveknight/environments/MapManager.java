package com.beaver.caveknight.environments;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.buildings.Buildings;
import com.beaver.caveknight.helpers.GameConstants;
import com.beaver.caveknight.helpers.HelpMethods;

import java.util.ArrayList;

public class MapManager {

    private GameMap currentMap, outsideMap, insideMap;
    private float cameraX, cameraY;

    public MapManager() {
        initTestMap();
    }

    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canMoveHere(float x, float y) {
        if (x < 0 || y < 0)
            return false;

        if (x >= getMaxWidthCurrentMap() || y >= getMaxHeightCurrentMap())
            return false;

        return true;
    }

    public int getMaxWidthCurrentMap() {
        return currentMap.getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMaxHeightCurrentMap() {
        return currentMap.getArrayHeight() * GameConstants.Sprite.SIZE;
    }


    public void drawBuildings(Canvas c) {
        if (currentMap.getBuildingArrayList() != null)
            for (Building b : currentMap.getBuildingArrayList())
                c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y + cameraY, null);
    }

    public void drawTiles(Canvas c) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                c.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }

    public void draw(Canvas c) {
        drawTiles(c);
        drawBuildings(c);
    }

    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox, cameraX, cameraY))
                return doorway;

        return null;
    }

    public void changeMap(GameMap gameMap) {
        this.currentMap = gameMap;
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
        buildingArrayList.add(new Building(new PointF(700, 200), Buildings.CAVE_ONE));

        insideMap = new GameMap(insideArray, MapTiles.INSIDE, null);
        outsideMap = new GameMap(outsideArray, MapTiles.OUTSIDE, buildingArrayList);

        HelpMethods.AddDoorwayToGameMap(outsideMap, insideMap, 1);

        currentMap = outsideMap;
    }
}
