package com.beaver.caveknight.environments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.beaver.caveknight.entities.enemies.Skeleton;
import com.beaver.caveknight.helpers.HelpMethods;
import com.beaver.caveknight.main.MainActivity;

import java.util.ArrayList;

public class WaveManager {
    private int waveCount;
    private final int skeletonsPerWave;
    private final float waveDelay;
    private float timeSinceLastWave;
    private boolean isWaveActive;
    private GameMap currentMap;
    private static int wavesSurvived;

    private String announcementText;
    private final Paint announcementPaint;
    private long announcementStartTime;

    public WaveManager(int skeletonsPerWave, float waveDelay) {
        this.skeletonsPerWave = skeletonsPerWave;
        this.waveDelay = waveDelay;
        this.waveCount = 0;
        this.timeSinceLastWave = 0;
        this.isWaveActive = false;

        announcementPaint = new Paint();
        announcementPaint.setColor(Color.RED);
        announcementPaint.setTextSize(100);
    }

    public void update(double delta) {
        if (isWaveActive) {
            if (areAllSkeletonsDefeated()) {
                timeSinceLastWave += (float) delta;
                if (timeSinceLastWave >= waveDelay) {
                    spawnWave();
                    timeSinceLastWave = 0;
                }
            }
        }
    }

    public void startWave(GameMap map) {
        this.currentMap = map;
        this.isWaveActive = true;
        spawnWave();
    }

    private void spawnWave() {
        waveCount++;
        announcementText = "WAVE " + waveCount;
        announcementStartTime = System.currentTimeMillis();
        ArrayList<Skeleton> skeletons = HelpMethods.GetSkeletonsRandomized(skeletonsPerWave * waveCount, currentMap.getSpriteIds(), currentMap);
        currentMap.addSkeletons(skeletons);
    }

    public void announceWave(Canvas c) {

        if (System.currentTimeMillis() - announcementStartTime < 3000) {
            float textWidth = announcementPaint.measureText(announcementText);
            float x = (float) MainActivity.GAME_WIDTH / 2 - textWidth / 2;
            float y = (float) MainActivity.GAME_HEIGHT / 2;
            c.drawText(announcementText, x, y, announcementPaint);
        }
    }

    public void stopWave() {
        isWaveActive = false;
        waveCount = 0;
    }

    public boolean isWaveActive() {
        return isWaveActive;
    }

    private boolean areAllSkeletonsDefeated() {
        for (Skeleton skeleton : currentMap.getSkeletonArrayList()) {
            if (skeleton.isActive()) {
                return false;
            }
        }
        return true;
    }

    public int getWaveCount() {
        return waveCount;
    }

    public static void setWavesSurvived(int waveCount) {
        wavesSurvived = waveCount - 1;
    }

    public static int getWavesSurvived() {
        return wavesSurvived;
    }
}
