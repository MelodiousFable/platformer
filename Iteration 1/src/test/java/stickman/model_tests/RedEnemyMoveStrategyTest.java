import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RedEnemyMoveStrategyTest {
  private MoveStrategy strategy;

  @Before
  public void setup() {
    strategy = new RedEnemyMoveStrategy();
  }

  @Test
  public void strategyLeftTest() {
    double[] movement = strategy.move();
    assertTrue(movement[0] == -0.8);
    assertTrue(movement[1] == 0);
  }

  @Test
  public void strategyRightTest() {
    for(int i = 0; i < 126; i++) {
      strategy.move(); //Move 126 places. The slime should change direction.
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0.8);
    assertTrue(movement[1] == 0);
  }

  @Test
  public void strategyLotsOfMovementTest() {
    for(int i = 0; i < 12500; i++) {
      strategy.move(); //Move 12500 places. The slime should be moving left.
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == -0.8);
    assertTrue(movement[1] == 0);
  }
}
