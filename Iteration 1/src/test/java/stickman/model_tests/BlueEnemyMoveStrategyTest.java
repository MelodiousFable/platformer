import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BlueEnemyMoveStrategyTest {
  private MoveStrategy strategy;

  @Before
  public void setup() {
    strategy = new BlueEnemyMoveStrategy();
  }

  @Test
  public void strategyFirstMovementTest() {
    double[] movement = strategy.move();
    assertTrue(movement[0] == -3);
    assertTrue(movement[1] == -2);
  }

  @Test
  public void strategyChangeXDirectionTest() {
    for(int i = 0; i < 50; i++) {
      strategy.move();
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] > 0); //The enemy is now moving right instead of left
  }

  @Test
  public void strategyChangeYDirectionTest() {
    for(int i = 0; i < 34; i++) {
      strategy.move();
    }
    double[] movement = strategy.move();
    assertTrue(movement[1] > 0); //The enemy is now moving down instead of up
  }
}
