import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CloudMoveStrategyTest {
  private MoveStrategy strategy;

  @Before
  public void setup() {
    strategy = new CloudMoveStrategy();
  }

  @Test
  public void strategyTest() {
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0.3);
    assertTrue(movement[1] == 0);
  }
}
