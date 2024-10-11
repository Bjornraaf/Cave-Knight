package com.beaver.caveknight.entities.buildings;

import static com.beaver.caveknight.helpers.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum Buildings implements BitmapMethods {


    HOUSE_ONE(0, 0, 64, 48, 23, 38, 20, 52);
//    CAVE_ONE(157, 50, 49, 40, 24,19,0,0);


    final Bitmap houseImg;
    final PointF doorwayPoint;
    final int hitboxRoof;
    final int hitboxFloor;
    final int hitboxHeight;
    final int hitboxWidth;


    Buildings(int x, int y, int width, int height, int doorwayX, int doorwayY, int hitboxRoof, int hitboxFloor) {
        options.inScaled = false;

        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * SCALE_MULTIPLIER;
        this.hitboxWidth = width * SCALE_MULTIPLIER;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

        doorwayPoint = new PointF(doorwayX * SCALE_MULTIPLIER, doorwayY * SCALE_MULTIPLIER);


    }

    public PointF getDoorwayPoint() {
        return doorwayPoint;
    }

    public int getHitboxRoof() {
        return hitboxRoof;
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }
}