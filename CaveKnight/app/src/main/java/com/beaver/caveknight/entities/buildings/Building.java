package com.beaver.caveknight.entities.buildings;

import android.graphics.PointF;

public class Building {

    private final PointF pos;
    private final Buildings buildingType;

    public Building(PointF pos, Buildings buildingType) {
        this.pos = pos;
        this.buildingType = buildingType;
    }

    public Buildings getBuildingType() {
        return buildingType;
    }

    public PointF getPos() {
        return pos;
    }
}
