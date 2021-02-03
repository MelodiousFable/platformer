import java.util.List;
import java.util.ArrayList;
import stickman.model.*;
import stickman.model.Entity.Layer;
import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LevelImplTest {
  private PlayerControlledEntity hero;
  private List<MovingEntity> hostileEntityRight;
  private List<MovingEntity> hostileEntityLeft;
  private List<MovingEntity> hostileEntityBelow;
  private List<MovingEntity> hostileEntityAbove;

  private List<MovingEntity> hostileEntityAboveAndBelow;
  private List<MovingEntity> hostileEntityLeftAndRight;

  private List<Entity> nonHostileEntityRight;
  private List<Entity> nonHostileEntityLeft;
  private List<Entity> nonHostileEntityAbove;

  private List<Entity> nonHostileEntityLeftAndRight;

  @Before
  public void setup() {
    hostileEntityRight = new ArrayList<MovingEntity>();
    hostileEntityLeft = new ArrayList<MovingEntity>();
    hostileEntityBelow = new ArrayList<MovingEntity>();
    hostileEntityAbove = new ArrayList<MovingEntity>();
    hostileEntityAboveAndBelow = new ArrayList<MovingEntity>();
    hostileEntityLeftAndRight = new ArrayList<MovingEntity>();

    nonHostileEntityRight = new ArrayList<Entity>();
    nonHostileEntityLeft = new ArrayList<Entity>();
    nonHostileEntityAbove = new ArrayList<Entity>();

    nonHostileEntityLeftAndRight = new ArrayList<Entity>();

    hero = new Hero(50, 25, 100, 20);
    MovingEntity enemy1 = new MovingEntityImpl("path.png", 40, 35, 50, 11, Layer.FOREGROUND, new RedEnemyMoveStrategy(), true);
    MovingEntity enemy2 = new MovingEntityImpl("path.png", 69, 35, 50, 11, Layer.FOREGROUND, new RedEnemyMoveStrategy(), true);
    MovingEntity enemy3 = new MovingEntityImpl("path.png", 60, -24, 50, 5, Layer.FOREGROUND, new RedEnemyMoveStrategy(), true);
    MovingEntity enemy4 = new MovingEntityImpl("path.png", 60, 124, 50, 5, Layer.FOREGROUND, new RedEnemyMoveStrategy(), true);
    Entity platform1 = new StationaryEntity("path.png", 40, 35, 50, 11, Layer.FOREGROUND, false);
    Entity platform2 = new StationaryEntity("path.png", 69, 35, 50, 11, Layer.FOREGROUND, false);
    Entity platform3 = new StationaryEntity("path.png", 60, 124, 50, 5, Layer.FOREGROUND, false);
    hostileEntityLeft.add(enemy1);
    hostileEntityRight.add(enemy2);
    hostileEntityBelow.add(enemy3);
    hostileEntityAbove.add(enemy4);

    hostileEntityLeftAndRight.add(enemy1);
    hostileEntityLeftAndRight.add(enemy2);
    hostileEntityAboveAndBelow.add(enemy3);
    hostileEntityAboveAndBelow.add(enemy4);

    nonHostileEntityLeft.add(platform1);
    nonHostileEntityRight.add(platform2);
    nonHostileEntityAbove.add(platform3);

    nonHostileEntityLeftAndRight.add(platform1);
    nonHostileEntityLeftAndRight.add(platform2);
  }

  @Test
  public void testLevelNotNull() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    assertNotNull(l);
  }

  @Test
  public void getVanillaEntitiesTest() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    List<Entity> ents = l.getVanillaEntities();
    assertNotNull(ents);
    for(Entity e : ents) {
      assertNotNull(e);
    }
  }

  @Test
  public void getNullVanillaEntitiesTest() {
    Level l = new LevelImpl(null, hostileEntityRight, 400, 640, 340, hero);
    List<Entity> ents = l.getVanillaEntities();
    assertNotNull(ents);
    assertTrue(ents.size() == 0);
  }

  @Test
  public void getMovingEntitiesTest() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    List<MovingEntity> ents = l.getMovingEntities();
    assertNotNull(ents);
    for(MovingEntity e : ents) {
      assertNotNull(e);
    }
  }

  @Test
  public void getNullMovingEntitiesTest() {
    Level l = new LevelImpl(nonHostileEntityRight, null, 400, 640, 340, hero);
    List<MovingEntity> ents = l.getMovingEntities();
    assertNotNull(ents);
    assertTrue(ents.size() == 0);
  }

  @Test
  public void getHeightTest() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    assertTrue(l.getHeight() == 400);
  }

  @Test
  public void getWidthTest() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    assertTrue(l.getWidth() == 640);
  }

  @Test
  public void getFloorHeightTest() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    assertTrue(l.getFloorHeight() == 340);
  }

  @Test
  public void getHeroXTestNonMoving() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    assertTrue(l.getHeroX() == hero.getXPos());
  }

  @Test
  public void getHeroXTestAfterMoving() {
    Level l = new LevelImpl(nonHostileEntityRight, hostileEntityRight, 400, 640, 340, hero);
    hero.handleKeyboardInput(true, false, false); //Move the hero a little bit to the left
    assertTrue(l.getHeroX() == hero.getXPos());
  }

  @Test
  public void checkSingleHostileCollisionFromLeft() {
    Level l = new LevelImpl(null, hostileEntityLeft, 400, 640, 340, hero);
    l.tick();
    assertTrue(hero.hasDied()); //Ensure the hero loses a life from an enemy collision on the left
  }

  @Test
  public void checkSingleHostileCollisionFromRight() {
    Level l = new LevelImpl(null, hostileEntityRight, 400, 640, 340, hero);
    l.tick();
    assertTrue(hero.hasDied()); //Ensure the hero loses a life from an enemy collision on the right
  }

  @Test
  public void checkSingleHostileCollisionFromBelow() {
    Level l = new LevelImpl(null, hostileEntityBelow, 400, 640, 340, hero);
    l.tick();
    assertTrue(hero.hasDied()); //Ensure the hero loses a life from an enemy collision from below the enemy
  }

  @Test
  public void checkSingleHostileCollisionFromAbove() {
    Level l = new LevelImpl(null, hostileEntityAbove, 400, 640, 340, hero);
    l.tick();
    assertTrue(l.getMovingEntities().size() == 0); //Ensure the enemy is destroyed if the hero jumps on it
  }

  @Test
  public void checkTwoHostileCollisions() {
    Level l = new LevelImpl(null, hostileEntityLeftAndRight, 400, 640, 340, hero);
    l.tick();
    assertTrue(hero.hasDied()); //Ensure the hero loses a life from an enemy collision
    assertTrue(hero.getLives() == 2); //Ensure only one life has been lost and not 2
  }

  @Test
  public void checkTwoMoreHostileCollisions() {
    //In the event the hero jumps on an enemy at the same time an enemy kills the hero,
    //The hero should both lose a life and also crush the enemy.
    Level l = new LevelImpl(null, hostileEntityAboveAndBelow, 400, 640, 340, hero);
    l.tick();
    assertTrue(hero.hasDied()); //Ensure the hero loses a life from an enemy collision
    assertTrue(hero.getLives() == 2); //Ensure only one life has been lost and not 2
    assertTrue(l.getMovingEntities().size() == 1); //Ensure the enemy that was jumped on was also crushed
  }

  @Test
  public void checkNonHostileCollisionFromLeft() {
    Level l = new LevelImpl(nonHostileEntityLeft, null, 400, 640, 340, hero);
    l.tick();
    assertFalse(l.moveLeft());
    assertTrue(l.moveRight());
    //If there is an obstacle on the left, the character should be able to still move right
    //but not left.
  }

  @Test
  public void checkNonHostileCollisionFromRight() {
    Level l = new LevelImpl(nonHostileEntityRight, null, 400, 640, 340, hero);
    l.tick();
    assertTrue(l.moveLeft());
    assertFalse(l.moveRight());
    //If there is an obstacle on the right, the character should be able to still move left
    //but not right.
  }

  @Test
  public void checkNonHostileCollisionFromAbove() {
    Level l = new LevelImpl(nonHostileEntityAbove, null, 400, 640, 340, hero);
    l.tick();
    assertTrue(l.moveLeft());
    assertTrue(l.moveRight());
    assertTrue(hero.getJumpHeight() == hero.getYPos());
    //If the hero jumps on a platform, the hero should not be able to fall off it
    //unless he walks off.
  }

  @Test
  public void checkSingleHostileCollisionFromLeftAndRight() {
    Level l = new LevelImpl(nonHostileEntityLeftAndRight, null, 400, 640, 340, hero);
    l.tick();
    assertFalse(l.moveLeft());
    assertFalse(l.moveRight());
    //If there is an obstacle on the right and left, the character should only be able to jump.
  }

}
