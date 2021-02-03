import stickman.model.*;
import stickman.model.Entity.Layer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StationaryEntityTest {
  private StationaryEntity entity;

  @Before
  public void createEntityUnderTest() {
    entity = new StationaryEntity("path.png", 5, 5, 10, 10, Layer.FOREGROUND, false);
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
    double y = entity.getYPos();
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
  public void hostileTest() {
    boolean hostile = entity.isHostile();
    assertFalse(hostile);
  }
}
