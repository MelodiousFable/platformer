package stickman.model.movement_strategies;

public class BlueEnemyMoveStrategy implements MoveStrategy {
  private double xVelocity;
  private double yVelocity;
  private double xPos;
  private double yPos;

  public BlueEnemyMoveStrategy() {
    xVelocity = -3;
    yVelocity = -2;
    xPos = 0;
    yPos = 0;
  }

  public double[] move() {
    if(xPos < 0) {
      xVelocity += 0.06;
    }
    else if(xPos > 0) {
      xVelocity -= 0.06;
    }
    xPos += xVelocity;

    if(yPos >= 0) {
      yVelocity = -2;
    }
    else {
      yVelocity += 0.06;
    }
    yPos += yVelocity;
    return new double[] {xVelocity, yVelocity};
  }
}
