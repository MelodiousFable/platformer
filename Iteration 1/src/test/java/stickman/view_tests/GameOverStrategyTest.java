import stickman.view.*;
import javafx.scene.layout.Pane;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameOverStrategyTest {
  @Test
  public void endGameTest() {
    GameEndStrategy strategy = new GameOverStrategy(25.5, new Pane());
    strategy.endGame();
  }

  @Test
  public void nullPaneTest() {
    GameEndStrategy strategy = new GameOverStrategy(25.5, null);
    strategy.endGame();
  }
}
