import stickman.model.*;
import stickman.view.*;
import javafx.scene.Scene;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;

@RunWith(JfxRunner.class)
public class GameWindowTest {
  private GameEngine testModel;
  private GameWindow testWindow;

  @Before
  @TestInJfxThread
  public void setup() {
    testModel = new GameEngineImpl("testing-jsons/gameEngineImplTest.json");
    testWindow = new GameWindow(testModel, 600, 410);
  }

  @Test
  @TestInJfxThread
  public void runTest() {
    testWindow.run();
    //Ensure nothing breaks during a regular run call
  }

  @Test
  @TestInJfxThread
  public void winGameTest() {
    GameEngine model = new GameEngineImpl("testing-jsons/instantGameWin.json");
    //In this file, the hero is already at the finish line
    GameWindow window = new GameWindow(model, 640, 400);
    assertTrue(model.youWin());
    //Ensure the game has been won
    window.run();
    //Ensure the run command does not break upon winning the game
  }

  @Test
  @TestInJfxThread
  public void loseGameTest() {
    GameEngine model = new GameEngineImpl("testing-jsons/instantGameLoss.json");
    //In this file, an enemy kills the hero each time he respawns
    GameWindow window = new GameWindow(model, 640, 400);
    for(int i = 0; i < 4; i++) {
      model.tick();
    }
    assertTrue(model.gameOver());
    //Ensure the game has been lost
    window.run();
    //Ensure the run command does not break upon losing the game
  }

  @Test
  @TestInJfxThread
  public void loseAndWinGameTest() {
    GameEngine model = new GameEngineImpl("testing-jsons/instantGameLossAndWin.json");
    //In this file, an enemy kills the hero each time he respawns, and the hero
    //respawns at the finish line
    GameWindow window = new GameWindow(model, 640, 400);
    for(int i = 0; i < 4; i++) {
      model.tick();
    }
    assertTrue(model.gameOver());
    assertFalse(model.youWin());
    //Ensure the game has been lost and not won
    window.run();
    //Ensure the run command does not break upon this event occuring
  }
}
