package com.beaver.caveknight.gamestates;

import com.beaver.caveknight.main.Game;

public abstract class BaseState {
    protected Game game;

    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
