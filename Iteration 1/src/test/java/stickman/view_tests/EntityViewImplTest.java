import stickman.view.*;
import stickman.model.*;
import stickman.model.movement_strategies.*;
import stickman.model.Entity.Layer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;

@RunWith(JfxRunner.class)
public class EntityViewImplTest {
  private EntityView view;
  private StationaryEntity entity;

  @Before
  @TestInJfxThread
  public void setup() {
    entity = new StationaryEntity("ch_stand1.png", 35, 40, 45, 50, Entity.Layer.FOREGROUND, false);
    view = new EntityViewImpl(entity);
  }

  @Test
  @TestInJfxThread
  public void updateAfterMoveTest() {
    view.update(0);
    //Simply ensures the update method functions properly
  }

  @Test
  @TestInJfxThread
  public void matchesEntityTest() {
    boolean trueOne = view.matchesEntity(entity);
    boolean falseOne = view.matchesEntity(new StationaryEntity("path_2.png", 35, 40, 45, 50, Entity.Layer.FOREGROUND, false));
    //The newly created entity should not be the same as the original one,
    //even though all values are the same.
    assertTrue(trueOne);
    assertFalse(falseOne);
  }

  @Test
  @TestInJfxThread
  public void imagePathNotFoundTest() {
    Entity noFileEntity = new StationaryEntity("oogabooga.png", 35, 40, 45, 50, Entity.Layer.BACKGROUND, false);
    EntityView noFileView = new EntityViewImpl(noFileEntity);
    assertNotNull(noFileView);
    //The EntityView should assign a default imagepath to an entity with
    //a broken path
  }

  @Test
  @TestInJfxThread
  public void markForDeleteTest() {
    boolean before = view.isMarkedForDelete();
    view.markForDelete();
    boolean after = view.isMarkedForDelete();
    assertTrue(after);
    assertFalse(before);
  }

  @Test
  @TestInJfxThread
  public void getNodeTest() {
    Node n = view.getNode();
    assertNotNull(n); //Ensure something is returned
    ImageView i = (ImageView) n;
    double nodeX = i.getX();
    double nodeY = i.getY();
    double nodeHeight = i.getFitHeight();
    double nodeWidth = i.getFitWidth();
    assertTrue(nodeX == entity.getXPos());
    assertTrue(nodeY == entity.getYPos());
    assertTrue(nodeHeight == entity.getHeight());
    assertTrue(nodeWidth == entity.getWidth());
  }

  @Test
  @TestInJfxThread
  public void changeImagePathTest() {
    //Test if the EntityView updates its imagepath if the entity does.
    //Will be useful for animation.
    entity.setImagePath("slimeYa.png");
    view.update(0);
    assertTrue(view.matchesEntity(entity));
  }
}
