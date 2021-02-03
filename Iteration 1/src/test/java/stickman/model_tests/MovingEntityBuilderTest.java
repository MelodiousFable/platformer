import stickman.model.Entity.Layer;
import stickman.model.movement_strategies.*;
import stickman.model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MovingEntityBuilderTest {

  @Test
  public void allPartsAddedTest() {
    MovingEntityBuilder builder = new MovingEntityBuilder();
    builder = builder.addImagePath("sample.png");
    assertNotNull(builder);
    builder = builder.addXPos(10);
    assertNotNull(builder);
    builder = builder.addYPos(11);
    assertNotNull(builder);
    builder = builder.addHeight(12);
    assertNotNull(builder);
    builder = builder.addWidth(13);
    assertNotNull(builder);
    builder = builder.addLayer(Layer.EFFECT);
    assertNotNull(builder);
    builder = builder.addHostile(false);
    assertNotNull(builder);
    builder = builder.addStrategy(new RedEnemyMoveStrategy());
    assertNotNull(builder);
    MovingEntity entityCreated = null;
    try {
      entityCreated = builder.build();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    assertNotNull(entityCreated);
    assertTrue(entityCreated.getImagePath().equals("sample.png"));
    assertTrue(entityCreated.getXPos() == 10);
    assertTrue(entityCreated.getYPos() == 11);
    assertTrue(entityCreated.getHeight() == 12);
    assertTrue(entityCreated.getWidth() == 13);
    assertTrue(entityCreated.getLayer() == Layer.EFFECT);
    assertFalse(entityCreated.isHostile());
  }

  @Test
  public void missingPartsTest() {
    MovingEntityBuilder builder = new MovingEntityBuilder();
    MovingEntity entityCreated = builder.build();
    assertNull(entityCreated);
  }
}
