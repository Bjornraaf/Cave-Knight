package com.beaver.caveknight.gamestates;

import android.view.MotionEvent;

import com.beaver.caveknight.main.Game;
import com.beaver.caveknight.ui.CustomButton;

public abstract class BaseState {
    protected Game game;

    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isButtonTouched(MotionEvent motionEvent, CustomButton button){
        return button.getHitbox().contains(motionEvent.getX(), motionEvent.getY());
    }
}
