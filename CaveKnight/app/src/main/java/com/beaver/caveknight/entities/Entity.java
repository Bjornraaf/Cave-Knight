package com.beaver.caveknight.entities;

import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Entity implements Comparable<Entity>{

    protected RectF hitbox;
    protected boolean active = true;
    protected float lastCameraValueY = 0;

    public Entity(PointF pos, float width, float height) {
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public void setLastCameraValueY(float lastCameraValueY) {
        this.lastCameraValueY = lastCameraValueY;
    }

    @Override
    public int compareTo(Entity other){
        return Float.compare(hitbox.top - lastCameraValueY, other.hitbox.top - other.lastCameraValueY);
    }
}
