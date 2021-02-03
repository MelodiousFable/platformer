package game.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class GameEngineImplementation implements GameEngine {
    private double height;
    private Level currentLevel;
    private Map<Integer, Level> levels;
    private List<String> jsonPaths;
    private int levelId;
    private int currentLevelId;
    private Instant start;
    private Duration interval;
    private int lives;
    private double levelScore;
    private double totalScore;
    private SaveKeeper saves;
    private boolean wasLoaded;

    /**
     * Creates the game engine using the specified json configuration file and height
     * @param jsonPath The path to the json configuration file containing the level information
     * @param height The height of the game engine's window
     */
    public GameEngineImplementation(List<String> jsonPaths, double height) {
        this.height = height;
        this.levels = new HashMap<Integer, Level>();
        this.jsonPaths = jsonPaths;
        this.levelId = 1;
        this.currentLevelId = 1;
        this.lives = 3;
        this.levelScore = 0;
        this.totalScore = 0;
        this.saves = new SaveKeeper();
        wasLoaded = false;
        createLevels();
        startLevel(false);
    }

    /**
     * Creates the levels associated with the json file
     */
    public void createLevels() {
        for(String s : jsonPaths) {
            LevelBuilder levelBuilder = new LevelBuilder(s);
            LevelDirector levelDirector = new LevelDirector(levelBuilder);
            levelDirector.buildLevel();
            this.levels.put(this.levelId, levelDirector.getLevel());
            levelId += 1;
        }
    }

    @Override
    public double getTotalScore() {
        return this.totalScore;
    }

    @Override
    public void startLevel(boolean restart) {
        if(wasLoaded) {
            LevelBuilder levelBuilder = new LevelBuilder("saved_state.json");
            LevelDirector levelDirector = new LevelDirector(levelBuilder);
            levelDirector.buildLevel();
            this.currentLevel = levelDirector.getLevel();
            this.currentLevel.setHeroLives(saves.loadState().getCurrentLevel().getHeroHealth());
            wasLoaded = false;
            return;
        }
        int tempLives = 3;
        if(currentLevel != null) {
            tempLives = this.currentLevel.getHeroHealth();
        }
        this.currentLevel = this.levels.get(currentLevelId);
        if(!restart) {
            resetCurrentLevel(true);
            this.currentLevel.setHeroLives(tempLives);
            this.levelScore = this.currentLevel.getTargetTime();
            if(currentLevelId == 1) {
                start = Instant.now();
            }
        }
    }

    @Override
    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public boolean jump() {
        return this.currentLevel.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.currentLevel.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.currentLevel.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.currentLevel.stopMoving();
    }

    @Override
    public double getLevelScore() {
        return this.levelScore;
    }

    @Override
    public void tick() {
        this.currentLevel.tick();
        double s = this.levelScore - 0.017;
        s = Math.round(s*100);
        s = s/100;
        if(this.currentLevel.enemyWasKilled()) {
            s = s + 100;
            s = Math.round(s*100);
            s = s/100;
        }
        this.levelScore = s;
        interval = Duration.between(start, Instant.now());
    }

    @Override
    public void resetCurrentLevel(boolean newLevel) {
        if(!newLevel) {
            this.lives--;
            if (this.lives == 0) {
                return;
            }
        }
        LevelBuilder levelBuilder = new LevelBuilder(this.jsonPaths.get(currentLevelId - 1));
        LevelDirector levelDirector = new LevelDirector(levelBuilder);
        levelDirector.buildLevel();
        this.levels.put(this.currentLevelId, levelDirector.getLevel());
        if(!newLevel) {
            startLevel(true);
        }
    }

    @Override
    public boolean isFinished() {
        if(currentLevel.isFinished() && currentLevelId + 1 > levels.size()) {
            totalScore += levelScore;
            return true;
        }
        else if(currentLevel.isFinished() && currentLevelId + 1 <= levels.size()) {
            currentLevelId += 1;
            totalScore += levelScore;
            levelScore = -1;
            startLevel(false);
        }
        return false;
    }

    @Override
    public Duration getDuration() {
        return interval;
    }

    @Override
    public boolean gameOver() {
        return this.lives == 0;
    }

    @Override
    public int getLives() {
        return this.lives;
    }

    @Override
    public void saveState() {
        saves.saveState(this.currentLevel.copy(), this.currentLevelId, this.interval, this.lives, this.levelScore, this.totalScore);
    }

    @Override
    public void loadState() {
        if(saves.loadState() == null) {
            return;
        }
        this.wasLoaded = true;
        this.currentLevelId = saves.loadState().getCurrentLevelId();
        this.interval = saves.loadState().getInterval();
        this.lives = saves.loadState().getLives();
        this.levelScore = saves.loadState().getLevelScore();
        this.totalScore = saves.loadState().getTotalScore();
        startLevel(true);
    }
}
