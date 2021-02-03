package stickman.view;

import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;

public class YouWinStrategy implements GameEndStrategy {
  private double timeTaken;
  private Pane pane;
  private int livesLeft;

  public YouWinStrategy(double timeTaken, int livesLeft, Pane pane) {
    this.timeTaken = timeTaken;
    this.livesLeft = livesLeft;
    if(pane == null) {
      this.pane = new Pane();
    }
    else {
      this.pane = pane;
    }
  }

  @Override
  public void endGame() {
    Text youWin = createText(160, 100, 80, Color.GREEN, "You win!");
    Text lives = createText(80, 210, 60, Color.GREEN, "You had " + Integer.toString(livesLeft) + " lives left");
    Text stats = createText(190, 320, 30, Color.GREEN, "Time taken: " + Double.toString(timeTaken) + "s");
    pane.getChildren().clear();
    pane.getChildren().addAll(youWin, lives, stats);
  }

  private Text createText(double x, double y, double size, Color colour, String text) {
    Text product = new Text(x, y, text);
    product.setFont(new Font(size));
    product.setFill(colour);
    return product;
  }
}
