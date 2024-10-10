package com.beaver.caveknight.environments;

import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.enemies.Skeleton;
import com.beaver.caveknight.entities.objects.GameObject;
import com.beaver.caveknight.entities.objects.GameObjects;
import com.beaver.caveknight.helpers.GameConstants;

import java.util.ArrayList;

public class GameMap {

    private final int[][] spriteIds;
    private final MapTiles mapTilesType;
    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final ArrayList<Doorway> doorwayArrayList;
    private final ArrayList<Skeleton> skeletonArrayList;

    public GameMap(int[][] spriteIds, MapTiles mapTilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectsArrayList, ArrayList<Skeleton> skeletonArrayList) {
        this.spriteIds = spriteIds;
        this.mapTilesType = mapTilesType;
        this.buildingArrayList = buildingArrayList;
        this.gameObjectArrayList = gameObjectsArrayList;
        this.skeletonArrayList = skeletonArrayList;
        this.doorwayArrayList = new ArrayList<>();
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

    public ArrayList<Skeleton> getSkeletonArrayList() {
        return skeletonArrayList;
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
}
