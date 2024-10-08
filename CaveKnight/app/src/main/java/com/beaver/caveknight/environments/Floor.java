package com.beaver.caveknight.environments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.main.MainActivity;
import com.beaver.caveknight.helpers.GameConstants;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;

public enum Floor implements BitmapMethods {

    OUTSIDE(R.drawable.tileset_floor, 22, 26);

    private final Bitmap[] sprites;

    Floor(int resID, int tilesInWidth, int tilesInHeight) {
        options.inScaled = false;
        sprites = new Bitmap[tilesInHeight * tilesInWidth];
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < tilesInHeight; j++)
            for (int i = 0; i < tilesInWidth; i++) {
                int index = j * tilesInWidth + i;
                sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
            }

    }

    public Bitmap getSprite(int id){
        return sprites[id];
    }

}