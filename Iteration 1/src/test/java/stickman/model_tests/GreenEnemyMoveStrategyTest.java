import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GreenEnemyMoveStrategyTest {
  private MoveStrategy strategy;

  @Before
  public void setup() {
    strategy = new GreenEnemyMoveStrategy();
  }

  @Test
  public void strategyUpTest() {
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0);
    assertTrue(movement[1] == -4);
  }

  @Test
  public void strategyDownTest() {
    for(int i = 0; i < 67; i++) {
      strategy.move(); //Move 67 places. The slime should change direction.
    }
    double[] movement = strategy.move();
    assertTrue(movement[0] == 0);
    assertTrue(movement[1] > 0.02 && movement[1] < 0.0201);
    //This assertion must be doe due to the inaccuracies of the double type.
    //The result should be equal to 0.02 but is slightly greater.
  }
}
