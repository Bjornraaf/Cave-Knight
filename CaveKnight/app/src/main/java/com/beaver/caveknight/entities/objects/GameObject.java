package com.beaver.caveknight.entities.objects;

import android.graphics.PointF;

import com.beaver.caveknight.entities.Entity;

public class GameObject extends Entity {

    private final GameObjects objectType;

    public GameObject(PointF pos, GameObjects objectType) {
        super(new PointF(pos.x,pos.y + objectType.hitboxRoof),
                objectType.getHitboxWidth(),
                objectType.getHitboxHeight()
        );
        this.objectType = objectType;
    }

    public GameObjects getObjectType() {
        return objectType;
    }
}