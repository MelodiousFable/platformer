import stickman.model.*;
import stickman.model.Entity.Layer;
import stickman.model.movement_strategies.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MovingEntityImplTest {
  private MovingEntityImpl entity;

  @Before
  public void createEntityUnderTest() {
    entity = new MovingEntityImpl("path.png", 5, 5, 10, 10, Layer.FOREGROUND, new RedEnemyMoveStrategy(), false);
  }

  @Test
  public void getImagePathTest() {
    String path = entity.getImagePath();
    assertTrue(path.equals("path.png"));
  }

  @Test
  public void getXPosTest() {
    double x = entity.getXPos();
    assertTrue(x == 5);
  }

  @Test
  public void getYPosTest() {
    double y = entity.getXPos();
    assertTrue(y == 5);
  }

  @Test
  public void getHeightTest() {
    double h = entity.getHeight();
    assertTrue(h == 10);
  }

  @Test
  public void getWidthTest() {
    double w = entity.getWidth();
    assertTrue(w == 10);
  }

  @Test
  public void getLayerTest() {
    Layer l = entity.getLayer();
    assertEquals(Layer.FOREGROUND, l);
  }

  @Test
  public void moveTest() {
    double xBefore = entity.getXPos();
    double yBefore = entity.getYPos();
    entity.move();
    double xAfter = entity.getXPos();
    double yAfter = entity.getYPos();
    assertTrue(xBefore == xAfter + 0.8);
    assertTrue(yBefore == yAfter);
  }

  @Test
  public void hostileTest() {
    boolean hostile = entity.isHostile();
    assertFalse(hostile);
  }
}
