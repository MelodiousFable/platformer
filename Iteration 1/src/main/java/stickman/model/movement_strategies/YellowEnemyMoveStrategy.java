package stickman.model.movement_strategies;

public class YellowEnemyMoveStrategy implements MoveStrategy {
  private int timer;
  private double vel;
  private double distance;
  private int modifier;

  public YellowEnemyMoveStrategy() {
    timer = 0;
    vel = -15;
    distance = 0;
    modifier = 1;
  }

  public double[] move() {
    while(timer < 500) {
      timer += 1;
      return new double[] {0, 0};
    }
    vel += 0.04;
    if(distance + vel <= -150) {
      modifier = -1;
    }
    else if(distance + vel >= 150) {
      modifier = 1;
    }
    distance += vel*modifier;

    if(vel >= 0) {
      timer = 0;
      vel = -15;
      return new double[] {0, 0};
    }

    return new double[] {vel*modifier, 0};
  }
}
