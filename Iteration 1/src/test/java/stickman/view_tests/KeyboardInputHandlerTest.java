import stickman.model.*;
import stickman.view.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;

@RunWith(JfxRunner.class)
public class KeyboardInputHandlerTest {
  private GameEngine engine;
  private KeyboardInputHandler handler;
  private KeyEvent left;
  private KeyEvent right;
  private KeyEvent up;
  private KeyEvent other;

  @Before
  @TestInJfxThread
  public void setup() {
    engine = new GameEngineImpl("testing-jsons/gameEngineImplTest.json");
    handler = new KeyboardInputHandler(engine);
    left = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.LEFT.toString(), KeyCode.LEFT.toString(), KeyCode.LEFT, false,
            false, false, false);
    right = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.RIGHT.toString(), KeyCode.RIGHT.toString(), KeyCode.RIGHT, false,
            false, false, false);
    up =  new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP.toString(), KeyCode.UP.toString(), KeyCode.UP, false,
            false, false, false);
    other = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.G.toString(), KeyCode.G.toString(), KeyCode.G, false,
            false, false, false);
  }

  @Test
  @TestInJfxThread
  public void moveLeftTest() {
    handler.handlePressed(left);
    assertTrue(handler.getLeft());
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void moveRightTest() {
    handler.handlePressed(right);
    assertTrue(handler.getRight());
    assertFalse(handler.getLeft());
  }

  @Test
  @TestInJfxThread
  public void moveLeftAndRightTest() {
    handler.handlePressed(left);
    handler.handlePressed(right);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
    //If both keys are pressed at the same time, there shoud not be any movement.
  }

  @Test
  @TestInJfxThread
  public void jumpTest() {
    handler.handlePressed(up);
    //A sound should be played when this is called.
    assertFalse(handler.getRight());
    assertFalse(handler.getLeft());
  }

  @Test
  @TestInJfxThread
  public void moveLeftDoubleTest() {
    handler.handlePressed(left);
    handler.handlePressed(left);
    assertTrue(handler.getLeft());
    //The second left "press" should not be included as a separate press
    //if the key has not been released yet.
  }

  @Test
  @TestInJfxThread
  public void moveRightDoubleTest() {
    handler.handlePressed(right);
    handler.handlePressed(right);
    assertTrue(handler.getRight());
    //The second right "press" should not be included as a separate press
    //if the key has not been released yet.
  }

  @Test
  @TestInJfxThread
  public void moveLeftAndReleaseTest() {
    handler.handlePressed(left);
    handler.handleReleased(left);
    assertFalse(handler.getLeft());
  }

  @Test
  @TestInJfxThread
  public void moveRightAndReleaseTest() {
    handler.handlePressed(right);
    handler.handleReleased(right);
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void releaseLeftTest() {
    //Test if the code functions properly if told to release a key
    //which hasn't been pressed yet.
    handler.handleReleased(left);
    assertFalse(handler.getLeft());
  }

  @Test
  @TestInJfxThread
  public void releaseRightTest() {
    //Test if the code functions properly if told to release a key
    //which hasn't been pressed yet.
    handler.handleReleased(right);
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void releaseLeftAndRightTest() {
    handler.handlePressed(left);
    handler.handlePressed(right);
    handler.handleReleased(left);
    handler.handleReleased(right);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void nonFunctionalPressTest() {
    handler.handlePressed(other);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void nonFunctionalReleaseTest() {
    handler.handleReleased(other);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void nonFunctionalPressAndReleaseTest() {
    handler.handlePressed(other);
    handler.handleReleased(other);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
  }

  @Test
  @TestInJfxThread
  public void nonFunctionalDoublePressTest() {
    handler.handlePressed(other);
    handler.handlePressed(other);
    assertFalse(handler.getLeft());
    assertFalse(handler.getRight());
  }

}
