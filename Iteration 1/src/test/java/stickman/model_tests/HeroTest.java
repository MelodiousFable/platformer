import stickman.model.*;
import stickman.model.Entity.Layer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HeroTest {
  private Hero hero;

  @Before
  public void createHero() {
    hero = new Hero(100, 100, 20, 30);
  }

  @Test
  public void getImagePathTest() {
    String path = hero.getImagePath();
    assertTrue(path.equals("ch_stand1.png"));
  }

  @Test
  public void getXPosTest() {
    double x = hero.getXPos();
    assertTrue(x == 100);
  }

  @Test
  public void getYPosTest() {
    double y = hero.getXPos();
    assertTrue(y == 100);
  }

  @Test
  public void getHeightTest() {
    double h = hero.getHeight();
    assertTrue(h == 20);
  }

  @Test
  public void getWidthTest() {
    double w = hero.getWidth();
    assertTrue(w == 30);
  }

  @Test
  public void getLayerTest() {
    Layer l = hero.getLayer();
    assertEquals(Layer.FOREGROUND, l);
  }

  @Test
  public void hostileTest() {
    boolean hostile = hero.isHostile();
    assertFalse(hostile);
  }

  @Test
  public void getLivesTest() {
    int lives = hero.getLives();
    assertTrue(lives == 3);
  }

  @Test
  public void getJumpHeightTest() {
    double jumpH = hero.getJumpHeight();
    double y = hero.getYPos();
    assertTrue(y == jumpH && jumpH == 100);
  }

  @Test
  public void isDeadTest() {
    boolean dead = hero.hasDied();
    assertFalse(dead);
  }

  @Test
  public void noKeyboardInputTest() {
    double xBefore = hero.getXPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(false, false, false);
    }
    double xAfter = hero.getXPos();
    assertTrue(xBefore == xAfter);
  }

  @Test
  public void keyboardInputLeftTest() {
    double xBefore = hero.getXPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(true, false, false);
    }
    double xAfter = hero.getXPos();
    assertTrue(xBefore == xAfter + 10);
  }

  @Test
  public void keyboardInputRightTest() {
    double xBefore = hero.getXPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(false, true, false);
    }
    double xAfter = hero.getXPos();
    assertTrue(xBefore == xAfter - 10);
  }

  @Test
  public void keyboardInputLeftAndRightTest() {
    double xBefore = hero.getXPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(true, true, false);
    }
    double xAfter = hero.getXPos();
    assertTrue(xBefore == xAfter);
  }

  @Test
  public void keyboardInputJumpTest() {
    double yBefore = hero.getYPos();
    for(int i = 0; i < 3; i++) {
      hero.handleKeyboardInput(false, false, true);
    }
    double yAfter = hero.getYPos();
    assertTrue(yAfter < yBefore);
  }

  @Test
  public void keyboardInputContinuousJumpTest() {
    double initialY = hero.getYPos();
    double lowestY = hero.getYPos();
    for(int i = 0; i < 100; i++) {
      hero.handleKeyboardInput(false, false, true);
      double yAfter = hero.getYPos();
      if(yAfter < lowestY) {
        lowestY = yAfter;
      }
      assertTrue(yAfter <= initialY);
    }
    assertFalse(lowestY == initialY);
  }

  @Test
  public void keyboardInputRightAndJumpTest() {
    double xBefore = hero.getXPos();
    double yBefore = hero.getYPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(false, true, true);
    }
    double xAfter = hero.getXPos();
    double yAfter = hero.getYPos();
    assertTrue(xBefore == xAfter - 10);
    assertTrue(yBefore > yAfter);
  }

  @Test
  public void keyboardInputLeftAndJumpTest() {
    double xBefore = hero.getXPos();
    double yBefore = hero.getYPos();
    for(int i = 0; i < 10; i++) {
      hero.handleKeyboardInput(true, false, true);
    }
    double xAfter = hero.getXPos();
    double yAfter = hero.getYPos();
    assertTrue(xBefore == xAfter + 10);
    assertTrue(yBefore > yAfter);
  }

  @Test
  public void loseLifeTest() {
    int livesBefore = hero.getLives();
    hero.killed();
    boolean dead = hero.hasDied();
    int livesAfter = hero.getLives();
    assertTrue(dead);
    assertTrue(livesBefore == livesAfter + 1 && livesAfter == 2);
  }

  @Test
  public void respawnTest() {
    double initialX = hero.getXPos();
    double initialY = hero.getYPos();
    for(int i = 0; i < 100; i++) {
      hero.handleKeyboardInput(true, false, true); //Hero has moved away from start point
    }
    hero.killed();
    hero.resetLife(); //Hero should reset at start point
    double finalX = hero.getXPos();
    double finalY = hero.getYPos();
    boolean dead = hero.hasDied();
    assertFalse(dead);
    assertTrue(finalX == initialX);
    assertTrue(finalY == initialY);
  }

  @Test
  public void newStandingHeightTest() {
    double initialStandHeight = hero.getJumpHeight();
    for(int i = 0; i < 3; i++) {
      hero.handleKeyboardInput(false, false, true); //Jump to a new height
    }
    hero.setStandHeight();
    double secondStandHeight = hero.getJumpHeight();
    for(int i = 0; i < 3; i++) {
      hero.handleKeyboardInput(false, false, true); //Jump to an even higher height
    }
    hero.setStandHeight();
    double thirdStandHeight = hero.getJumpHeight();
    assertTrue(initialStandHeight > secondStandHeight && secondStandHeight > thirdStandHeight);
    for(int i = 0; i < 5; i++) {
      hero.handleKeyboardInput(true, false, false); //Move short distance to make sure hero does not fall
    }
    double currentYPos = hero.getYPos();
    assertTrue(currentYPos == thirdStandHeight);
  }

  @Test
  public void fallToGroundTest() {
    double initialStandHeight = hero.getJumpHeight();
    for(int i = 0; i < 3; i++) {
      hero.handleKeyboardInput(false, false, true); //Jump to a new height
    }
    hero.setStandHeight();
    double newStandHeight = hero.getJumpHeight();
    hero.fall();
    double afterFallStandHeight = hero.getJumpHeight();
    assertTrue(newStandHeight < afterFallStandHeight);
    assertTrue(afterFallStandHeight == initialStandHeight);
  }
}
