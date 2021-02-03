import stickman.model.movement_strategies.*;
import stickman.model.Entity.Layer;
import stickman.model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StringConverterTest {
  private StringConverter converter;

  @Before
  public void setup() {
    converter = new StringConverter();
  }

  @Test
  public void makeLayerBackgroundTest() {
    Layer l = converter.makeLayer("BACKGROUND");
    assertNotNull(l);
    assertTrue(l == Entity.Layer.BACKGROUND);
  }

  @Test
  public void makeLayerForegroundTest() {
    Layer l = converter.makeLayer("FOREGROUND");
    assertNotNull(l);
    assertTrue(l == Entity.Layer.FOREGROUND);
  }

  @Test
  public void makeLayerEffectTest() {
    Layer l = converter.makeLayer("EFFECT");
    assertNotNull(l);
    assertTrue(l == Entity.Layer.EFFECT);
  }

  @Test
  public void makeLayerInvalidTest() {
    Layer l = converter.makeLayer("BIGLY");
    assertNull(l); //Should return a null object if the item is not one of the above three.
  }

  @Test
  public void makeStrategyRedEnemyTest() {
    MoveStrategy m = converter.makeStrategy("redEnemy");
    assertNotNull(m);
    assertTrue(m instanceof RedEnemyMoveStrategy);
  }

  @Test
  public void makeStrategyGreenEnemyTest() {
    MoveStrategy m = converter.makeStrategy("greenEnemy");
    assertNotNull(m);
    assertTrue(m instanceof GreenEnemyMoveStrategy);
  }

  @Test
  public void makeStrategyBlueEnemyTest() {
    MoveStrategy m = converter.makeStrategy("blueEnemy");
    assertNotNull(m);
    assertTrue(m instanceof BlueEnemyMoveStrategy);
  }

  @Test
  public void makeStrategyCloudTest() {
    MoveStrategy m = converter.makeStrategy("cloud");
    assertNotNull(m);
    assertTrue(m instanceof CloudMoveStrategy);
  }

  @Test
  public void makeStrategyInvalidTest() {
    MoveStrategy m = converter.makeStrategy("purpleEnemy");
    assertNull(m); //Should return a null object if the item is not one of the above four.
  }
}
