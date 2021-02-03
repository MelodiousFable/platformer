package stickman.model;

import stickman.model.Entity.Layer;
import stickman.model.movement_strategies.*;

public class StationaryEntityBuilder {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double height;
  private double width;
  private Layer layer;
  private boolean hostile;

  public StationaryEntityBuilder addImagePath(String path) {
    this.imagePath = path;
    return this;
  }

  public StationaryEntityBuilder addXPos(double pos) {
    this.xPos = pos;
    return this;
  }

  public StationaryEntityBuilder addYPos(double pos) {
    this.yPos = pos;
    return this;
  }

  public StationaryEntityBuilder addHeight(double h) {
    this.height = h;
    return this;
  }

  public StationaryEntityBuilder addWidth(double w) {
    this.width = w;
    return this;
  }

  public StationaryEntityBuilder addLayer(Layer layer) {
    this.layer = layer;
    return this;
  }

  public StationaryEntityBuilder addHostile(boolean hostile) {
    this.hostile = hostile;
    return this;
  }

  public StationaryEntity build() {
    if(imagePath == null || layer == null) {
      return null;
    }
    return new StationaryEntity(imagePath, xPos, yPos, height, width, layer, hostile);
  }
}
