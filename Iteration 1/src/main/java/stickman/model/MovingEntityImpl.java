package stickman.model;

import stickman.model.movement_strategies.*;

public class MovingEntityImpl implements MovingEntity {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double height;
  private double width;
  private MoveStrategy strategy;
  private Layer layer;
  private boolean hostile;

  public MovingEntityImpl(String imagePath,
                        double xPos,
                        double yPos,
                        double height,
                        double width,
                        Layer layer,
                        MoveStrategy strategy,
                        boolean hostile) {
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.height = height;
    this.width = width;
    this.strategy = strategy;
    this.layer = layer;
    this.hostile = hostile;
  }

  @Override
  public String getImagePath() {
    return imagePath;
  }

  @Override
  public double getXPos() {
    return xPos;
  }

  @Override
  public double getYPos() {
    return yPos;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public Layer getLayer() {
    return layer;
  }

  @Override
  public void move() {
    double[] moveValues = strategy.move();
    xPos += moveValues[0];
    yPos += moveValues[1];
  }

  @Override
  public boolean isHostile() {
    return hostile;
  }
}
