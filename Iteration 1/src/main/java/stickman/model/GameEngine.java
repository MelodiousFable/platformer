package stickman.model;

public interface GameEngine {
    Level getCurrentLevel();
    PlayerControlledEntity getHero();

    void startLevel();

    // Hero inputs - boolean for success (possibly for sound feedback)
    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();

    void tick();
    boolean gameOver();
    boolean youWin();
}
