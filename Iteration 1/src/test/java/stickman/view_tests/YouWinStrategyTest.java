import stickman.view.*;
import javafx.scene.layout.Pane;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class YouWinStrategyTest {
  @Test
  public void endGameTest() {
    GameEndStrategy strategy = new YouWinStrategy(25.5, 3, new Pane());
    strategy.endGame();
  }

  @Test
  public void nullPaneTest() {
    GameEndStrategy strategy = new YouWinStrategy(25.5, 3, null);
    strategy.endGame();
  }
}
