package com.beaver.caveknight.helpers;

import android.graphics.RectF;

import com.beaver.caveknight.environments.Doorway;
import com.beaver.caveknight.environments.GameMap;

public class HelpMethods {

    public static void AddDoorwayToGameMap(GameMap gameMapLocatedIn, GameMap gameMapTarget, int buildingIndex) {
        float houseX = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getPos().x;
        float houseY = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getPos().y;

        RectF hitbox = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getHitboxDoorway();
        Doorway doorway = new Doorway(new RectF(hitbox.left + houseX, hitbox.top + houseY, hitbox.right + houseX, hitbox.bottom + houseY), gameMapTarget);

        gameMapLocatedIn.addDoorway(doorway);

    }
}
