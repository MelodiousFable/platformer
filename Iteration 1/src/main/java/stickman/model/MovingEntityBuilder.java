package stickman.model;

import stickman.model.Entity.Layer;
import stickman.model.movement_strategies.*;

public class MovingEntityBuilder {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double height;
  private double width;
  private Layer layer;
  private MoveStrategy strategy;
  private boolean hostile;

  public MovingEntityBuilder addImagePath(String path) {
    this.imagePath = path;
    return this;
  }

  public MovingEntityBuilder addXPos(double pos) {
    this.xPos = pos;
    return this;
  }

  public MovingEntityBuilder addYPos(double pos) {
    this.yPos = pos;
    return this;
  }

  public MovingEntityBuilder addHeight(double h) {
    this.height = h;
    return this;
  }

  public MovingEntityBuilder addWidth(double w) {
    this.width = w;
    return this;
  }

  public MovingEntityBuilder addLayer(Layer layer) {
    this.layer = layer;
    return this;
  }

  public MovingEntityBuilder addStrategy(MoveStrategy strategy) {
    this.strategy = strategy;
    return this;
  }

  public MovingEntityBuilder addHostile(boolean hostile) {
    this.hostile = hostile;
    return this;
  }

  public MovingEntityImpl build() {
    if(imagePath == null || layer == null || strategy == null) {
      return null;
    }
    return new MovingEntityImpl(imagePath, xPos, yPos, height, width, layer, strategy, hostile);
  }
}
