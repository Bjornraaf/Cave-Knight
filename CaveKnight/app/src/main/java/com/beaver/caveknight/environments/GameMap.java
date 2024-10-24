package com.beaver.caveknight.environments;

import com.beaver.caveknight.entities.Character;
import com.beaver.caveknight.entities.Entity;
import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.objects.GameObject;
import com.beaver.caveknight.helpers.GameConstants;

import java.util.ArrayList;

public class GameMap {

    private final int[][] spriteIds;
    private final MapTiles mapTilesType;
    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final ArrayList<Doorway> doorwayArrayList;
    private ArrayList<Character> enemyArrayList;

    public GameMap(int[][] spriteIds, MapTiles mapTilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectsArrayList, ArrayList<Character> enemyArrayList) {
        this.spriteIds = spriteIds;
        this.mapTilesType = mapTilesType;
        this.buildingArrayList = buildingArrayList;
        this.gameObjectArrayList = gameObjectsArrayList;
        this.enemyArrayList = enemyArrayList;
        this.doorwayArrayList = new ArrayList<>();
    }

    public Entity[] getDrawableList() {
        Entity[] list = new Entity[getDrawableAmount()];
        int i = 0;

        if (buildingArrayList != null)
            for (Building b : buildingArrayList)
                list[i++] = b;
        if (enemyArrayList != null)
            for (Character ch : enemyArrayList)
                list[i++] = ch;
        if (gameObjectArrayList != null)
            for (GameObject go : gameObjectArrayList)
                list[i++] = go;

        return list;
    }

    private int getDrawableAmount() {
        int amount = 0;
        if (buildingArrayList != null)
            amount += buildingArrayList.size();
        if (gameObjectArrayList != null)
            amount += gameObjectArrayList.size();
        if (enemyArrayList != null)
            amount += enemyArrayList.size();
        amount++;

        return amount;
    }

    public void addDoorway(Doorway doorway) {
        this.doorwayArrayList.add(doorway);
    }

    public ArrayList<Doorway> getDoorwayArrayList() {
        return doorwayArrayList;
    }

    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }

    public ArrayList<GameObject> getGameObjectArrayList() {
        return gameObjectArrayList;
    }

    public ArrayList<Character> getEnemyArrayList() {
        return enemyArrayList;
    }

    public MapTiles getFloorType() {
        return mapTilesType;
    }

    public int getSpriteID(int xIndex, int yIndex) {
        return spriteIds[yIndex][xIndex];
    }

    public int getArrayWidth() {
        return spriteIds[0].length;
    }

    public int getArrayHeight() {
        return spriteIds.length;
    }


    public float getMapWidth() {
        return getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public float getMapHeight() {
        return getArrayHeight() * GameConstants.Sprite.SIZE;
    }

    public int[][] getSpriteIds() {
        return spriteIds;
    }

    public void addEnemy(ArrayList<Character> enemies) {
        if (this.enemyArrayList == null) {
            this.enemyArrayList = new ArrayList<>();
        }
        this.enemyArrayList.addAll(enemies);
    }


}
