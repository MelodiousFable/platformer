package stickman.model;

import java.util.ArrayList;
import java.util.List;
import stickman.model.movement_strategies.*;

public class GameEngineImpl implements GameEngine {
  private Level currentLevel;
  private JSONLevelReader reader;
  private PlayerControlledEntity hero;
  private double startX;
  private boolean left;
  private boolean right;
  private boolean jump;

  public GameEngineImpl(String jsonFile) {
    reader = new JSONLevelReader(jsonFile);
    reader.read();
    double h = reader.getHeroHeight();
    double w = reader.getHeroWidth();
    this.startX = reader.getStartXPos();
    double floorHeight = reader.getFloorHeight();
    PlayerControlledEntity hero = new Hero(this.startX, floorHeight - h, h, w);
    this.hero = hero;
    startLevel();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
    List<MovingEntity> movingEnts = new ArrayList<MovingEntity>();
    List<Entity> vanillaEnts = new ArrayList<Entity>();
    for(Entity e : reader.getVanillaEnts()) {
      vanillaEnts.add(e);
    }
    for(MovingEntity e : reader.getMovingEnts()) {
      movingEnts.add(e);
    }
    double lvHeight = reader.getLevelHeight();
    double lvWidth = reader.getLevelWidth();
    double floorHeight = reader.getFloorHeight();
    Level level1 = new LevelImpl(vanillaEnts, movingEnts, lvHeight, lvWidth, floorHeight, this.hero);
    currentLevel = level1;
  }

  @Override
  public void tick() {
    if(hero.hasDied()) {
      hero.resetLife();
    }
    currentLevel.tick();
    if(!currentLevel.moveLeft()) {
      left = false;
    }
    if(!currentLevel.moveRight()) {
      right = false;
    }
    hero.handleKeyboardInput(left, right, jump);
    if(jump) {
      jump = false;
    }
    for(MovingEntity e : currentLevel.getMovingEntities()) {
      e.move();
    }
  }

  @Override
  public boolean jump() {
    if(currentLevel.jump()) {
      jump = true;
      return true;
    }
    return false;
  }

  @Override
  public boolean moveLeft() {
    if(currentLevel.moveLeft()) {
      left = true;
      return true;
    }
    else {
      left = false;
    }
    return false;
  }

  @Override
  public boolean moveRight() {
    if(currentLevel.moveRight()) {
      right = true;
      return true;
    }
    else {
      right = false;
    }
    return false;
  }

  @Override
  public boolean stopMoving() {
    left = false;
    right = false;
    return true;
  }

  @Override
  public PlayerControlledEntity getHero() {
    return this.hero;
  }

  @Override
  public boolean gameOver() {
    if(hero.getLives() == -1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean youWin() {
    if(hero.getXPos() >= (currentLevel.getWidth() - 20) && !gameOver()) {
      return true;
    }
    return false;
  }
}
