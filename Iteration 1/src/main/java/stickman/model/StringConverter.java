package stickman.model;

import stickman.model.movement_strategies.*;
import stickman.model.Entity.Layer;

public class StringConverter {
  public Entity.Layer makeLayer(String layerName) {
    if(layerName.equals("BACKGROUND")) {
      return Layer.BACKGROUND;
    }
    if(layerName.equals("FOREGROUND")) {
      return Layer.FOREGROUND;
    }
    if(layerName.equals("EFFECT")) {
      return Layer.EFFECT;
    }
    return null;
  }

  public MoveStrategy makeStrategy(String strategyName) {
    if(strategyName.equals("redEnemy")) {
      return new RedEnemyMoveStrategy();
    }
    if(strategyName.equals("blueEnemy")) {
      return new BlueEnemyMoveStrategy();
    }
    if(strategyName.equals("greenEnemy")) {
      return new GreenEnemyMoveStrategy();
    }
    if(strategyName.equals("yellowEnemy")) {
      return new YellowEnemyMoveStrategy();
    }
    if(strategyName.equals("cloud")) {
      return new CloudMoveStrategy();
    }
    return null;
  }
}
