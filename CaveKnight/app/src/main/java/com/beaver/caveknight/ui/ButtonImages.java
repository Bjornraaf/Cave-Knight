package com.beaver.caveknight.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    MENU_START(R.drawable.mainmenu_startbutton, 300, 140),
    MENU_OPTIONS(R.drawable.mainmenu_button_options, 300, 140),
    MENU_EXIT(R.drawable.mainmenu_button_exit, 300, 140),
    HOME_BUTTON(R.drawable.game_homebutton, 140, 140),
    ATTACK_BUTTON(R.drawable.attackbutton, 210, 210),
    ABILITY_BUTTON(R.drawable.ablitybutton, 210, 210);

    private final int width;
    private final int height;
    private final Bitmap normal;
    private final Bitmap pushed;

    ButtonImages(int resID, int width, int height){
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        normal = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);
        pushed = Bitmap.createBitmap(buttonAtlas, width, 0, width, height);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getButtonImg(boolean isButtonPushed){
        return isButtonPushed ? pushed : normal;
    }

}
