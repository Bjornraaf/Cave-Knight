package com.beaver.caveknight.helpers;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.entities.Player;
import com.beaver.caveknight.entities.Character;
import com.beaver.caveknight.entities.buildings.Building;
import com.beaver.caveknight.entities.objects.GameObject;
import com.beaver.caveknight.environments.Doorway;
import com.beaver.caveknight.environments.GameMap;
import com.beaver.caveknight.entities.enemies.Skeleton;
import com.beaver.caveknight.environments.MapTiles;

import java.util.ArrayList;

public class HelpMethods {


    public static PointF CreatePointForDoorway(GameMap gameMapLocatedIn, int buildingIndex) {
        Building building = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex);

        float x = building.getPos().x;
        float y = building.getPos().y;
        PointF point = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getDoorwayPoint();

        return new PointF(point.x + x, point.y + y - building.getBuildingType().getHitboxRoof());

    }

    public static PointF CreatePointForDoorway(int xTile, int yTile) {
        float x = xTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;
        float y = yTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;

        return new PointF(x, y);
    }

    public static void ConnectTwoDoorways(GameMap gameMapOne, PointF pointOne, GameMap gameMapTwo, PointF pointTwo) {

        Doorway doorwayOne = new Doorway(pointOne, gameMapOne);
        Doorway doorwayTwo = new Doorway(pointTwo, gameMapTwo);

        doorwayOne.connectDoorway(doorwayTwo);
        doorwayTwo.connectDoorway(doorwayOne);
    }

    public static ArrayList<Skeleton> GetSkeletonsRandomized(int amount, int[][] gameMapArray, GameMap gameMap) {
        ArrayList<Skeleton> skeletonArrayList = new ArrayList<>();
        float buffer = GameConstants.Sprite.SIZE; // Buffer size to avoid collisions

        for (int i = 0; i < amount; i++) {
            boolean isValidPosition = false;
            PointF position = null;

            while (!isValidPosition) {
                float x = (float) (Math.random() * (gameMapArray[0].length - 1) * GameConstants.Sprite.SIZE);
                float y = (float) (Math.random() * (gameMapArray.length - 1) * GameConstants.Sprite.SIZE);

                position = new PointF(x, y);

                if (HelpMethods.CanWalkHere(position.x + buffer, position.y, gameMap) &&
                        HelpMethods.CanWalkHere(position.x - buffer, position.y, gameMap) &&
                        HelpMethods.CanWalkHere(position.x, position.y + buffer, gameMap) &&
                        HelpMethods.CanWalkHere(position.x, position.y - buffer, gameMap)) {
                    isValidPosition = true;
                }
            }

            skeletonArrayList.add(new Skeleton(position));
        }

        return skeletonArrayList;
    }

    public static float MoveNextToTileUpDown(RectF hitbox, float cameraY, float deltaY) {
        int currentTile;
        int playerPosY;
        float cameraYReturn;

        if (deltaY > 0) {
            playerPosY = (int) (hitbox.top - cameraY);
            currentTile = playerPosY / GameConstants.Sprite.SIZE;
            cameraYReturn = hitbox.top - (currentTile * GameConstants.Sprite.SIZE);
        } else {
            playerPosY = (int) (hitbox.bottom - cameraY);
            currentTile = playerPosY / GameConstants.Sprite.SIZE;
            cameraYReturn = hitbox.bottom - (currentTile * GameConstants.Sprite.SIZE) - (GameConstants.Sprite.SIZE - 1);
        }

        return cameraYReturn;
    }

    public static float MoveNextToTileLeftRight(RectF hitbox, float cameraX, float deltaX) {
        int currentTile;
        int playerPosX;
        float cameraXReturn;

        if (deltaX > 0) {
            playerPosX = (int) (hitbox.left - cameraX);
            currentTile = playerPosX / GameConstants.Sprite.SIZE;
            cameraXReturn = hitbox.left - (currentTile * GameConstants.Sprite.SIZE);
        } else {
            playerPosX = (int) (hitbox.right - cameraX);
            currentTile = playerPosX / GameConstants.Sprite.SIZE;
            cameraXReturn = hitbox.right - (currentTile * GameConstants.Sprite.SIZE) - (GameConstants.Sprite.SIZE - 1);
        }

        return cameraXReturn;
    }

    public static boolean CanWalkHere(float x, float y, GameMap gameMap) {
        if (x < 0 || y < 0)
            return false;

        if (x >= gameMap.getMapWidth() || y >= gameMap.getMapHeight())
            return false;

        int tileX = (int) (x / GameConstants.Sprite.SIZE);
        int tileY = (int) (y / GameConstants.Sprite.SIZE);

        int tileId = gameMap.getSpriteID(tileX, tileY);

        return IsTileWalkable(tileId, gameMap.getFloorType());
    }

    public static boolean CanWalkHereUpDown(RectF hitbox, float deltaY, float currentCameraX, GameMap gameMap) {
        if (hitbox.top + deltaY < 0) return false;
        else if (hitbox.bottom + deltaY >= gameMap.getMapHeight()) return false;

        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + currentCameraX, hitbox.top + deltaY, hitbox.right + currentCameraX, hitbox.bottom + deltaY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + currentCameraX, hitbox.top + deltaY, hitbox.right + currentCameraX, hitbox.bottom + deltaY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, currentCameraX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }

    public static boolean CanWalkHereLeftRight(RectF hitbox, float deltaX, float currentCameraY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0) return false;
        else if (hitbox.right + deltaX >= gameMap.getMapWidth()) return false;

        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + currentCameraY, hitbox.right + deltaX, hitbox.bottom + currentCameraY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + currentCameraY, hitbox.right + deltaX, hitbox.bottom + currentCameraY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, deltaX, currentCameraY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }


    public static boolean CanWalkHere(RectF hitbox, float deltaX, float deltaY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0 || hitbox.top + deltaY < 0) return false;
        else if (hitbox.right + deltaX >= gameMap.getMapWidth()) return false;
        else if (hitbox.bottom + deltaY >= gameMap.getMapHeight()) return false;

        //Objects
        if (gameMap.getGameObjectArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + deltaY, hitbox.right + deltaX, hitbox.bottom + deltaY);
            for (GameObject go : gameMap.getGameObjectArrayList()) {
                if (RectF.intersects(go.getHitbox(), tempHitbox))
                    return false;
            }
        }

        //Buildings
        if (gameMap.getBuildingArrayList() != null) {
            RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + deltaY, hitbox.right + deltaX, hitbox.bottom + deltaY);
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), tempHitbox))
                    return false;
        }

        Point[] tileCords = GetTileCords(hitbox, deltaX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());
    }


    private static int[] GetTileIds(Point[] tileCords, GameMap gameMap) {
        int[] tileIds = new int[4];

        for (int i = 0; i < tileCords.length; i++)
            tileIds[i] = gameMap.getSpriteID(tileCords[i].x, tileCords[i].y);

        return tileIds;
    }


    private static Point[] GetTileCords(RectF hitbox, float deltaX, float deltaY) {
        Point[] tileCords = new Point[4];

        int left = (int) ((hitbox.left + deltaX) / GameConstants.Sprite.SIZE);
        int right = (int) ((hitbox.right + deltaX) / GameConstants.Sprite.SIZE);
        int top = (int) ((hitbox.top + deltaY) / GameConstants.Sprite.SIZE);
        int bottom = (int) ((hitbox.bottom + deltaY) / GameConstants.Sprite.SIZE);

        tileCords[0] = new Point(left, top);
        tileCords[1] = new Point(right, top);
        tileCords[2] = new Point(left, bottom);
        tileCords[3] = new Point(right, bottom);

        return tileCords;

    }

    public static boolean IsTilesWalkable(int[] tileIds, MapTiles tilesType) {
        for (int i : tileIds)
            if (!(IsTileWalkable(i, tilesType)))
                return false;
        return true;
    }

    public static boolean IsTileWalkable(int tileId, MapTiles tilesType) {
        if (tilesType == MapTiles.INSIDE)
            return (tileId == 394 || tileId < 374);

        return true;
    }

    public static boolean IsPlayerCloseForAttack(Character character, Player player, float cameraY, float cameraX) {
        float xDelta = character.getHitbox().left  - (player.getHitbox().left - cameraX);
        float yDelta = character.getHitbox().top - (player.getHitbox().top - cameraY);

        float distance = (float) Math.hypot(xDelta, yDelta);

        return distance < GameConstants.Sprite.SIZE * 1.5f;
    }
}
