package game.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GameSaveState {
  private Level currentLevel;
  private int currentLevelId;
  private Duration interval;
  private int lives;
  private double levelScore;
  private double totalScore;

  public GameSaveState(Level l, int lid, Duration d, int lvs, double lvScore, double tScore) {
    currentLevel = l;
    currentLevelId = lid;
    interval = d;
    lives = lvs;
    levelScore = lvScore;
    totalScore = tScore;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }

  public int getCurrentLevelId() {
    return currentLevelId;
  }

  public Duration getInterval() {
    return interval;
  }

  public int getLives() {
    return lives;
  }

  public double getLevelScore() {
    return levelScore;
  }

  public double getTotalScore() {
    return totalScore;
  }
}
