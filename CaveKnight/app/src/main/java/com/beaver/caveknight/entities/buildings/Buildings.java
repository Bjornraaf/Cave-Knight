package com.beaver.caveknight.entities.buildings;

import static com.beaver.caveknight.helpers.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum Buildings implements BitmapMethods {


    HOUSE_ONE(0, 0, 64, 48, 17,32,14,15),
    CAVE_ONE(157, 50, 49, 40, 24,19,14,15);


    final Bitmap houseImg;
    final RectF hitboxDoorway;

    Buildings(int x, int y, int width, int height, int doorwayX, int doorwayY, int doorwayWidth, int doorwayHeight) {
        options.inScaled = false;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

        hitboxDoorway = new RectF(
                doorwayX * SCALE_MULTIPLIER,
                doorwayY * SCALE_MULTIPLIER,
                (doorwayX + doorwayWidth) * SCALE_MULTIPLIER,
                (doorwayY + doorwayHeight) * SCALE_MULTIPLIER);
    }

    public RectF getHitboxDoorway() {
        return hitboxDoorway;
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }
}
