package com.beaver.caveknight.helpers;

public class ScoreManager {
    private int score;
    private static int finalScore;

    public ScoreManager() {
        this.score = 0;
    }

    public void incrementScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return this.score;
    }

    public void resetScore(){
        score = 0;
    }

    public static void setFinalScore(int score) {
        finalScore = score;
    }

    public static int getFinalScore() {
        return finalScore; // Get the final score
    }
}
