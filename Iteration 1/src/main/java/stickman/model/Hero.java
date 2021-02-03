package stickman.model;

public class Hero implements PlayerControlledEntity {
  private String imagePath;
  private double xPos;
  private double yPos;
  private double height;
  private double width;
  private double startY;
  private double startX;

  private double xVelocity;
  private double yVelocity;
  private double relativeY;
  private double jumpStartY;
  private boolean jumping;

  private boolean dead;
  private int lives;

  private boolean canMoveRight;
  private boolean canMoveLeft;

  public Hero(double xPos, double yPos, double height, double width) {
    imagePath = "ch_stand1.png";
    this.xPos = xPos;
    this.yPos = yPos;
    this.height = height;
    this.width = width;
    xVelocity = 0;
    yVelocity = 0;
    relativeY = 0;
    jumpStartY = yPos;
    jumping = false;
    dead = false;
    lives = 3;
    startY = yPos;
    startX = xPos;
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
    return Layer.FOREGROUND;
  }

  @Override
  public void handleKeyboardInput(boolean left, boolean right, boolean jump) {
    if((!left && !right) || left && right) {
      xVelocity = 0;
    }
    else if(left) {
      xVelocity = -1;
    }
    else if(right) {
      xVelocity = 1;
    }
    xPos += xVelocity;
    jump(jump);
  }

  @Override
  public void killed() {
    dead = true;
    lives -= 1;
  }

  @Override
  public boolean hasDied() {
    return dead;
  }

  @Override
  public void resetLife() {
    xPos = startX;
    yPos = startY;
    setStandHeight();
    dead = false;
  }

  @Override
  public int getLives() {
    return lives;
  }

  private void jump(boolean jump) {
    if(relativeY >= 0 && jump) {
      jumping = true;
      yVelocity = -3;
    }
    if(jumping) {
      yVelocity += 0.075;
      yPos += yVelocity;
      relativeY += yVelocity;
      if(relativeY >= 0) {
        relativeY = 0;
        yPos = jumpStartY;
        yVelocity = 0;
      }
    }
  }

  @Override
  public void setStandHeight() {
    yVelocity = 0;
    relativeY = 0;
    jumpStartY = yPos;
  }

  @Override
  public boolean isHostile() {
    return false;
  }

  @Override
  public double getJumpHeight() {
    return jumpStartY;
  }

  @Override
  public void fall() {
    relativeY = yPos - startY;
    jumpStartY = startY;
  }
}
