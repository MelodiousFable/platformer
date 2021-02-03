import java.util.ArrayList;
import java.util.List;
import stickman.model.*;
import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameEngineImplTest {
  GameEngineImpl engine;

  @Before
  public void createEngine() {
    engine = new GameEngineImpl("testing-jsons/gameEngineImplTest.json");
    //The constructor already runs the startLevel function, and as
    //it does not return anything, the testing for it will be
    //done through other methods.
  }

  @Test
  public void levelNotNullTest() {
    Level level = engine.getCurrentLevel();
    assertNotNull(level);
  }

  @Test
  public void heroNotNullTest() {
    PlayerControlledEntity hero = engine.getHero();
    assertNotNull(hero);
  }

  @Test
  public void winGameTest() {
    boolean gameWin = engine.youWin();
    assertFalse(gameWin);
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 3000; i++) {
      hero.handleKeyboardInput(false, true, false);
    }
    gameWin = engine.youWin();
    assertTrue(gameWin);
  }

  @Test
  public void loseGameTest() {
    boolean gameLose = engine.gameOver();
    assertFalse(gameLose);
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 4; i++) {
      hero.killed();
      hero.resetLife();
    }
    gameLose = engine.gameOver();
    assertTrue(gameLose);
  }

  @Test
  public void winAndLoseAtSameTimeTest() {
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 3000; i++) {
      hero.handleKeyboardInput(false, true, false);
    }
    for(int i = 0; i < 4; i++) {
      hero.killed();
      hero.resetLife();
    }
    //Win the game and lose the game at the same time
    boolean win = engine.youWin();
    boolean lose = engine.gameOver();
    assertTrue(lose);
    assertFalse(win);
    //If both happen at the same time, you should lose the game
  }

  @Test
  public void stopMovingTest() {
    boolean stop = engine.stopMoving();
    assertTrue(stop);
    //stopMoving() should always return true as there is nothing
    //in the game that prevents you from stopping
  }

  @Test
  public void moveLeftPossibleTest() {
    boolean left = engine.moveLeft();
    assertTrue(left);
    //Moving left is allowed if the hero encounters no obstacles
  }

  @Test
  public void moveLeftImpossibleTest() {
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 100; i++) {
      hero.handleKeyboardInput(true, false, false);
    }
    engine.tick(); //Forces the level to update hero x position
    boolean left = engine.moveLeft();
    assertFalse(left);
    //The hero should be far out of the bounds of the level and
    //unable to go further left
  }

  @Test
  public void moveRightPossibleTest() {
    boolean right = engine.moveRight();
    assertTrue(right);
    //Moving right is allowed if the hero encounters no obstacles
  }

  @Test
  public void moveRightImpossibleTest() {
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 3000; i++) {
      hero.handleKeyboardInput(false, true, false);
    }
    engine.tick(); //Forces the level to update hero x position
    boolean right = engine.moveRight();
    assertFalse(right);
    //The hero should be far out of the bounds of the level and
    //unable to go further right
  }

  @Test
  public void jumpPossibleTest() {
    boolean jump = engine.jump();
    assertTrue(jump);
  }

  @Test
  public void jumpImpossibleTest() {
    PlayerControlledEntity hero = engine.getHero();
    for(int i = 0; i < 4; i++) {
      hero.handleKeyboardInput(false, false, true);
    }
    boolean jump = engine.jump();
    assertFalse(jump);
    //The hero should not be able to double-jump if he is already
    //in the air (the Y-value is lower than the standing height)
  }
}
