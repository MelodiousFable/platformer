package game.model;

import game.view.GameWindow;

/**
 * Represents the hero in the game
 */
public class Hero extends MovableEntityAbstraction implements Controllable {
    private GravitationalState gravitationalState;
    private GravitationalState fallingState;
    private GravitationalState jumpingState;
    private GravitationalState bouncingState;
    private boolean right;
    private boolean left;
    private boolean jump;
    private boolean fall;
    private boolean bounce;
    private boolean hurt;
    private boolean facingRight;
    private int rightTicks = 0;
    private int leftTicks = 0;
    private int notMovingTicks = 0;
    private int immunityTicks = 0;
    private int health = 3;

    /**
     * Creates a hero with the specified imagePath, xPos, yPos, width, height
     * and xVel
     */
    public Hero(String imagePath, double xPos, double yPos, double width, double height, double xVel) {
        super(imagePath, xPos, yPos, width, height, xVel, Layer.FOREGROUND);
        this.fallingState = new FallingState();
        this.jumpingState = new JumpingState();
        this.bouncingState = new BouncingState();
        this.gravitationalState = this.fallingState;
        this.right = false;
        this.left = false;
        this.jump = false;
        this.fall = false;
        this.bounce = false;
        this.facingRight = true;
        this.hurt = false;
    }

    public Hero(Hero other) {
        super(other);
        this.fallingState = other.fallingState;
        this.jumpingState = other.jumpingState;
        this.bouncingState = other.bouncingState;
        this.gravitationalState = other.gravitationalState;
        this.right = other.right;
        this.left = other.left;
        this.jump = other.jump;
        this.fall = other.fall;
        this.bounce = other.bounce;
        this.facingRight = other.facingRight;
        this.hurt = other.hurt;
        this.rightTicks = other.rightTicks;
        this.leftTicks = other.leftTicks;
        this.notMovingTicks = other.notMovingTicks;
        this.immunityTicks = other.immunityTicks;
        this.health = other.health;
    }

    @Override
    public boolean signalJump() {
        if (jump || fall || bounce) {
            return false;
        }
        jump = true;
        setGravitationalState(jumpingState);
        return true;
    }

    @Override
    public boolean signalMoveRight() {
        if (right || left) {
            return false;
        }
        facingRight = true;
        right = true;
        rightTicks = 0;
        return true;
    }

    @Override
    public boolean signalMoveLeft() {
        if (left || right) {
            return false;
        }
        facingRight = false;
        left = true;
        leftTicks = 0;
        return true;
    }

    @Override
    public boolean signalStopMoving() {
        if (right || left) {
            right = false;
            left = false;
            return true;
        }
        return false;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void initiateBounce() {
        bounce = true;
        setGravitationalState(bouncingState);
    }

    @Override
    public void initiateFall() {
        fall = true;
        setGravitationalState(fallingState);
    }

    @Override
    public boolean isBouncing() {
        return bounce;
    }

    @Override
    public boolean isFalling() {
        return fall;
    }

    @Override
    public boolean isJumping() {
        return jump;
    }

    @Override
    public boolean isMovingRight() {
        return right;
    }

    @Override
    public boolean isMovingLeft() {
        return left;
    }

    @Override
    public void finishGravitationalState() {
        this.jump = false;
        this.fall = false;
        this.bounce = false;
    }

    @Override
    public void moveVertically() {
        gravitationalState.moveVertically(this);
    }

    @Override
    public void moveLeft() {
        if (xPos - xVel >= 0) {
            xPos -= xVel;
        }
    }

    @Override
    public void moveRight() {
        xPos += xVel;
    }

    @Override
    public void reverseYVel() {
        this.gravitationalState.getForce().reverseForce();
    }

    @Override
    public double getYVel() {
        return this.gravitationalState.getForce().getCurrentVelocity();
    }

    @Override
    public double getXVel() {
        return this.xVel;
    }

    /**
     * Animates the hero according to his movement
     */
    private void animate() {
        int animationTimer = 1;
        int animationTicks = animationTimer * GameWindow.getTimer();
        String type = "";
        if (hurt) {
            type = "hurt/";
        }
        if (right) {
            imagePath = type + "ch_walk" + ((rightTicks / animationTicks) + 1) + ".png";
        } else if (left && (xPos - xVel >= 0)) {
            imagePath = type + "ch_walk" + ((leftTicks / animationTicks) + 5) + ".png";
        } else if (facingRight) {
            imagePath = type + "ch_stand" + ((notMovingTicks / animationTicks) + 1) + ".png";
        } else {
            imagePath = type + "ch_stand" + ((notMovingTicks / animationTicks) + 4) + ".png";
        }
        rightTicks = (rightTicks + 1) % (animationTicks * 4);
        leftTicks = (leftTicks + 1) % (animationTicks * 4);
        notMovingTicks = (notMovingTicks + 1) % (animationTicks * 3);
    }

    /**
     * Changes the current gravitational state of the hero to the specified state and resets the y velocity
     * associated with the changed state.
     * @param gravitationalState The gravitational state to change to
     */
    private void setGravitationalState(GravitationalState gravitationalState) {
        this.gravitationalState = gravitationalState;
        this.gravitationalState.getForce().reset();
    }

    @Override
    public void tick() {
        handleImmunity();
        animate();
    }

    @Override
    public void takeDamage() {
        if (!hurt) {
            this.health--;
        }
        hurt = true;
    }

    @Override
    public void setLivesAfterLevelTransition(int lives) {
        this.health = lives;
    }

    @Override
    public Controllable copy() {
        return new Hero(this);
    }

    /**
     * Handles how long the hero is immune after taking damage from an enemy
     */
    private void handleImmunity() {
        if (!hurt) {
            return;
        }
        int immunityTimer = 2;
        immunityTicks += 1;
        if (immunityTicks == GameWindow.ticksPerSecond() * immunityTimer) {
            hurt = false;
            immunityTicks = 0;
        }
    }

}
