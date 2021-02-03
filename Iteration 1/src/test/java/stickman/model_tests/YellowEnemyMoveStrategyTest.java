import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class YellowEnemyMoveStrategyTest {
  private MoveStrategy strategy;

  @Before
  public void setup() {
    strategy = new YellowEnemyMoveStrategy();
  }

  @Test
  public void strategyFirstMovementTest() {
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0);
    assertTrue(movement[1] == 0);
    //The yellow enemy stays stationary initially before moving at an extremely
    //high speed.
  }

  @Test
  public void strategyTimer499Test() {
    for(int i = 0; i < 498; i++) {
      strategy.move();
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0);
    assertTrue(movement[1] == 0);
    //The slime should still be stationary after EXACTLY 499 ticks
  }

  @Test
  public void strategyTimer500Test() {
    for(int i = 0; i < 500; i++) {
      strategy.move();
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == -14.96);
    assertTrue(movement[1] == 0);
    //The slime should start moving after EXACTLY 500 ticks
  }

  @Test
  public void strategyStopMovingTest() {
    for(int i = 0; i < 875; i++) {
      strategy.move();
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0);
    assertTrue(movement[1] == 0);
    //The slime should be stationary once again after a total of 875 ticks
  }
}
