package com.beaver.caveknight.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum HealthIcons implements BitmapMethods {

    HEART_FULL(0),
    HEART_3Q(1),
    HEART_HALF(2),
    HEART_1Q(3),
    HEART_EMPTY(4);

    private final Bitmap healthIcon;

    HealthIcons(int xPos) {
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.health_icons, options);
        int widthHeight = 16;
        healthIcon = getScaledBitmap(Bitmap.createBitmap(atlas, xPos * widthHeight, 0, widthHeight, widthHeight));
    }
    public Bitmap getHealthIcon() {
        return healthIcon;
    }
}
