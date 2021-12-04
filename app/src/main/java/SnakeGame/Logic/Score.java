package SnakeGame.Logic;

public class Score {

    private int score;

    public Score() {
        score = 0;
    }

    public void setScore() {
        this.score += 10;
    }

    public int getScore() {
        return this.score;
    }

}
