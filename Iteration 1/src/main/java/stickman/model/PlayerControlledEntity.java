package stickman.model;

public interface PlayerControlledEntity extends Entity {
  void handleKeyboardInput(boolean left, boolean right, boolean jump);
  void killed();
  void resetLife();
  boolean hasDied();
  int getLives();
  void setStandHeight();
  double getJumpHeight();
  void fall();
}
