package com.beaver.caveknight.environments;

import com.beaver.caveknight.entities.buildings.Building;

import java.util.ArrayList;

public class GameMap {

    private final int[][] spriteIds;
    private final MapTiles mapTilesType;
    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<Doorway> doorwayArrayList;

    public GameMap(int[][] spriteIds, MapTiles mapTilesType, ArrayList<Building> buildingArrayList) {
        this.spriteIds = spriteIds;
        this.mapTilesType = mapTilesType;
        this.buildingArrayList = buildingArrayList;
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


}
