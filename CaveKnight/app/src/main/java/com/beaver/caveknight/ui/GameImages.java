package com.beaver.caveknight.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum GameImages implements BitmapMethods {

    MAINMENU_BUTTONBG(R.drawable.mainmenu_buttonbackground),
    MAINMENU_BG(R.drawable.cave_bg),
    DEATHSCREEN_MENUBG(R.drawable.mainmenu_deathscreen_background);


    private final Bitmap image;

    GameImages(int resID) {
        options.inScaled = false;
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
    }

    public Bitmap getImage() {
        return image;
    }
}
