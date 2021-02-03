package stickman.model;

public class StationaryEntity implements Entity {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double height;
  private double width;
  private Layer layer;
  private boolean hostile;

  public StationaryEntity(String imagePath,
                        double xPos,
                        double yPos,
                        double height,
                        double width,
                        Layer layer,
                        boolean hostile) {
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.height = height;
    this.width = width;
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
  public boolean isHostile() {
    return hostile;
  }

  //DUMMY FUNCTION FOR TESTING ENTITYVIEW
  public void setImagePath(String path) {
    this.imagePath = path;
  }
  //DUMMY FUNCTION FOR TESTING ENTITYVIEW
}
