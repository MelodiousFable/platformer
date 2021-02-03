package stickman.model.movement_strategies;

public class RedEnemyMoveStrategy implements MoveStrategy {
  private double distanceFromOrigin;
  private int modifier;

  public RedEnemyMoveStrategy() {
    distanceFromOrigin = 0;
    modifier = -1;
  }
  public double[] move() {
    if(distanceFromOrigin < -125) {
      modifier = 1;
    }
    if(distanceFromOrigin > 125) {
      modifier = -1;
    }
    distanceFromOrigin += modifier;
    return new double[] {0.8*modifier, 0};
  }
}
