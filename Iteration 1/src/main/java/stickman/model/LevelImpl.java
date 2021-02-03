package stickman.model;

import java.util.List;
import java.util.ArrayList;

public class LevelImpl implements Level {
  private List<Entity> vanillaEntities;
  private List<MovingEntity> movingEntities;
  private double height;
  private double width;
  private double floorHeight;
  private PlayerControlledEntity hero;
  private boolean canMoveLeft;
  private boolean canMoveRight;

  public LevelImpl(List<Entity> vanillaEntities,
        List<MovingEntity> movingEntities,
        double height,
        double width,
        double floorHeight,
        PlayerControlledEntity hero) {
    if(vanillaEntities == null) {
      this.vanillaEntities = new ArrayList<Entity>();
    }
    else {
      this.vanillaEntities = vanillaEntities;
    }
    if(movingEntities == null) {
      this.movingEntities = new ArrayList<MovingEntity>();
    }
    else {
      this.movingEntities = movingEntities;
    }
    this.height = height;
    this.width = width;
    this.floorHeight = floorHeight;
    this.hero = hero;
    canMoveLeft = true;
    canMoveRight = true;
  }

  @Override
  public List<Entity> getVanillaEntities() {
    return vanillaEntities;
  }

  @Override
  public List<MovingEntity> getMovingEntities() {
    return movingEntities;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public double getFloorHeight() {
    return floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public void tick() {
    List<Entity> collidedEntities = checkCollisions();
    for(Entity collidedEntity : collidedEntities) {
      String relativeHeroPosition = checkCollisionDirection(collidedEntity);
      if(collidedEntity.isHostile()) {
        hostileCollision(relativeHeroPosition, collidedEntity);
      }
      else {
        nonHostileCollision(relativeHeroPosition);
      }
    }
    if(collidedEntities.size() == 0) {
      hero.fall();
      canMoveLeft = true;
      canMoveRight = true;
    }
  }

  @Override
  public boolean jump() {
    if(hero.getYPos() == hero.getJumpHeight()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean moveLeft() {
    if(hero.getXPos() <= 0.0 || !canMoveLeft) {
      return false;
    }
    return true;
  }

  @Override
  public boolean moveRight() {
    if(hero.getXPos() >= width || !canMoveRight) {
      return false;
    }
    return true;
  }

  @Override
  public boolean stopMoving() {
    if(hero.getXPos() <= 0.0 || hero.getXPos() >= width) {
      return true;
    }
    return false;
  }

  private List<Entity> checkCollisions() {
    List<Entity> collidedEntities = new ArrayList<Entity>();
    for(Entity e : vanillaEntities) {
      if(e.getLayer() == Entity.Layer.FOREGROUND &&
          hero.getXPos() < (e.getXPos() + e.getWidth()) &&
          (hero.getXPos() + hero.getWidth()) > e.getXPos() &&
          hero.getYPos() < (e.getYPos() + e.getHeight()) &&
          (hero.getYPos() + hero.getHeight() > e.getYPos())) {
        collidedEntities.add(e);
      }
    }
    for(MovingEntity e : movingEntities) {
      if(e.getLayer() == Entity.Layer.FOREGROUND &&
          hero.getXPos() < (e.getXPos() + e.getWidth()) &&
          (hero.getXPos() + hero.getWidth()) > e.getXPos() &&
          hero.getYPos() < (e.getYPos() + e.getHeight()) &&
          (hero.getYPos() + hero.getHeight() > e.getYPos())) {
        collidedEntities.add(e);
      }
    }
    return collidedEntities;
  }

  private String checkCollisionDirection(Entity e) {
    if((hero.getYPos() + hero.getHeight() - 3) <= e.getYPos()) {
      return "above";
    }
    else if((hero.getXPos() + hero.getWidth() - 3) <= e.getXPos()) {
      return "left";
    }
    else if((hero.getYPos() + 3) >= (e.getYPos() + e.getHeight())) {
      return "below";
    }
    else if((hero.getXPos() + 3) >= (e.getXPos() + e.getWidth())) {
      return "right";
    }
    return "none";
  }

  private void hostileCollision(String relativeHeroPosition, Entity collidedEntity) {
    if(relativeHeroPosition.equals("above")) {
      movingEntities.remove(collidedEntity);
    }
    else if(!hero.hasDied()) {
      hero.killed();
    }
  }

  private void nonHostileCollision(String relativeHeroPosition) {
    if(relativeHeroPosition.equals("above")) {
      hero.setStandHeight();
    }
    if(relativeHeroPosition.equals("left")) {
      canMoveRight = false;
    }
    if(relativeHeroPosition.equals("right")) {
      canMoveLeft = false;
    }
  }
}
