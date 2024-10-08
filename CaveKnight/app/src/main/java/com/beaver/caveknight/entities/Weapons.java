package com.beaver.caveknight.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum Weapons implements BitmapMethods {

    BIG_SWORD(R.drawable.sword),
    SHADOW(R.drawable.shadow);

    final Bitmap weaponImg;

    Weapons(int resId) {
        options.inScaled = false;
        weaponImg = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options));
    }

    public Bitmap getWeaponImg() {
        return weaponImg;
    }

    public int getWidth() {
        return weaponImg.getWidth();
    }

    public int getHeight() {
        return weaponImg.getHeight();
    }
}
