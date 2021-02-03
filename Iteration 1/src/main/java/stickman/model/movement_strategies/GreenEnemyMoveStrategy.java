package stickman.model.movement_strategies;

public class GreenEnemyMoveStrategy implements MoveStrategy {
  private double heightAboveGround;
  private double velocity;

  public GreenEnemyMoveStrategy() {
    heightAboveGround = 0;
    velocity = 4;
  }

  public double[] move() {
    if(heightAboveGround <= 0) {
      velocity = 4;
    }
    else {
      velocity -= 0.06;
    }
    heightAboveGround += velocity;
    return new double[] {0, -1*velocity};
  }
}
