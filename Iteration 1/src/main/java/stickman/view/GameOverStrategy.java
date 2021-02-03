package stickman.view;

import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;

public class GameOverStrategy implements GameEndStrategy {
  public double timeTaken;
  public Pane pane;

  public GameOverStrategy(double timeTaken, Pane pane) {
    this.timeTaken = timeTaken;
    if(pane == null) {
      this.pane = new Pane();
    }
    else {
      this.pane = pane;
    }
  }

  @Override
  public void endGame() {
    Text gameOverText = createText(100, 100, 80, Color.RED, "Game Over!");
    Text noLivesLeft = createText(50, 210, 60, Color.RED, "You ran out of lives...");
    Text stats = createText(190, 320, 30, Color.RED, "Time taken: " + Double.toString(timeTaken) + "s");
    pane.getChildren().clear();
    pane.getChildren().addAll(gameOverText, noLivesLeft, stats);
  }

  private Text createText(double x, double y, double size, Color colour, String text) {
    Text product = new Text(x, y, text);
    product.setFont(new Font(size));
    product.setFill(colour);
    return product;
  }
}
