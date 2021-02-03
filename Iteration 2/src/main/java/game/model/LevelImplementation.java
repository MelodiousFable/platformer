package game.model;

import java.util.ArrayList;
import java.util.List;
import static game.model.Collision.*;
import java.lang.Math;

/**
 * Represents the implementation of a level
 */
public class LevelImplementation implements Level {

    private double targetTime;
    private boolean enemyDestroyed = false;
    private Controllable hero;
    private List<Entity> entities;
    private List<Entity> tangibles;
    private List<MovableEntity> movableEntities;
    private List<Enemy> enemies;
    private double floorHeight;
    private double width;
    private double height;
    private boolean slideEffect;
    private double slideVel;
    private boolean finished;

    /**
     * Creates a new level object
     */
    public LevelImplementation() {
        this.tangibles = new ArrayList<>();
    }

    public LevelImplementation(LevelImplementation other) {
        this.targetTime = other.targetTime;
        this.enemyDestroyed = other.enemyDestroyed;
        this.hero = other.hero.copy();
        this.entities = new ArrayList<Entity>();
        for(Entity e : other.entities) {
            this.entities.add(e.copy());
        }
        this.tangibles = new ArrayList<Entity>();
        for(Entity e : other.tangibles) {
            this.tangibles.add(e.copy());
        };
        this.movableEntities = new ArrayList<MovableEntity>();
        for(MovableEntity e : other.movableEntities) {
            this.movableEntities.add(e.copy());
        };
        this.enemies = new ArrayList<Enemy>();
        for(Enemy e : other.enemies) {
            this.enemies.add(e.copy());
        };
        this.floorHeight = other.floorHeight;
        this.width = other.width;
        this.height = other.height;
        this.slideEffect = other.slideEffect;
        this.slideVel = other.slideVel;
        this.finished = other.finished;
    }

    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public List<Entity> getTangibles() {
        return this.tangibles;
    }

    @Override
    public List<MovableEntity> getMovableEntities() {
        return this.movableEntities;
    }

    @Override
    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public boolean enemyWasKilled() {
        if(enemyDestroyed) {
            enemyDestroyed = false;
            return true;
        }
        return false;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getTargetTime() {
        return this.targetTime;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public void tick() {
        hero.tick();
        handleHeroVerticalMovement();
        handleHeroHorizontalMovement();
        deleteEnemiesMarkedForDeletion();
        handleEnemyMovement();
        handleMovableEntityMovement();
        handleEnemyAnimations();
    }

    @Override
    public double getFloorHeight() {
        return this.floorHeight;
    }

    @Override
    public void setHeroLives(int lives) {
        this.hero.setLivesAfterLevelTransition(lives);
    }

    @Override
    public double getHeroX() {
        return this.hero.getXPos();
    }

    @Override
    public int getHeroHealth() {
        return hero.getHealth();
    }

    @Override
    public boolean jump() {
        return this.hero.signalJump();
    }

    @Override
    public boolean moveLeft() {
        return this.hero.signalMoveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.hero.signalMoveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.hero.signalStopMoving();
    }

    /**
     * Deals with the vertical movement of the controllable entity
     */
    private void handleHeroVerticalMovement() {

        if (!(hero.isFalling() || hero.isBouncing() || hero.isJumping())) {
            hero.initiateFall();
        }



        for (Entity entity: tangibles) {
            if (verticalCollision(hero, entity, hero.getYVel())) {
                if (entity.isFinishFlag()) {
                    finished = true;
                    return;
                } else if (hero.getYVel() > 0) {
                    boolean conserveMomentum = ((hero.getYPos() + hero.getHeight() != entity.getYPos()) && (hero.isMovingLeft() || hero.isMovingRight()));
                    hero.setYPos(entity.getYPos() - hero.getHeight());
                    hero.finishGravitationalState();
                    if (entity.isIcy()) {
                        double slideAcc = 0.1;
                        if (!slideEffect) {
                            if (hero.isMovingRight()) {
                                slideEffect = true;
                                if (conserveMomentum) {
                                    slideVel = hero.getXVel();
                                } else {
                                    slideVel = slideAcc;
                                }
                            } else if (hero.isMovingLeft()) {
                                slideEffect = true;
                                if (conserveMomentum) {
                                    slideVel =  -1 * hero.getXVel();
                                } else {
                                    slideVel = -1 * slideAcc;
                                }
                            }
                        } else {
                            double slideVelMaximum = 8;
                            if (hero.isMovingRight()) {
                                if (slideVel + slideAcc > slideVelMaximum) {
                                    slideVel = slideVelMaximum;
                                } else {
                                    slideVel += slideAcc;
                                }
                            } else if (hero.isMovingLeft()){
                                if (slideVel - slideAcc < -1 * slideVelMaximum) {
                                    slideVel = -1 * slideVelMaximum;
                                } else {
                                    slideVel -= slideAcc;
                                }
                            }
                        }
                    } else if (!entity.isIcy()) {
                        slideEffect = false;
                    }
                    return;
                } else {
                    hero.setYPos(entity.getYPos() + entity.getHeight());
                    hero.reverseYVel();
                    return;
                }
            }
        }

        for (Enemy enemy: enemies) {
            if (verticalCollision(hero, enemy, hero.getYVel())) {
                if (hero.getYVel() >= 0) {
                    hero.setYPos(enemy.getYPos() - hero.getHeight());
                    hero.initiateBounce();
                    enemy.markForDeletion();
                    this.enemyDestroyed = true;
                    return;
                } else {
                    hero.takeDamage();
                }
            }
        }

        hero.moveVertically();

    }

    /**
     * Deals with the hero's horizontal movement
     */
    private void handleHeroHorizontalMovement() {
        double dx;
        if (slideEffect) {
            dx = slideVel;
        } else if (hero.isMovingLeft()) {
            dx = hero.getXVel() * -1;
        } else if (hero.isMovingRight()) {
            dx = hero.getXVel();
        } else {
            return;
        }
        if (hero.isMovingRight() || hero.isMovingLeft() || slideEffect) {
            for (Entity entity: tangibles) {
                if (horizontalCollision(hero, entity, dx)) {
                    if (entity.isFinishFlag()) {
                        finished = true;
                        return;
                    }
                    if (hero.getXPos() < entity.getXPos()) {
                        hero.setXPos(entity.getXPos() - hero.getWidth());
                    } else {
                        hero.setXPos(entity.getXPos() + entity.getWidth());
                    }
                    slideEffect = false;
                    return;
                }
            }
            for (Enemy enemy: enemies) {
                if (horizontalCollision(hero, enemy, dx)) {
                    if (hero.getXPos() < enemy.getXPos()) {
                        hero.setXPos(enemy.getXPos() - hero.getWidth());
                    } else {
                        hero.setXPos(enemy.getXPos() + enemy.getWidth());
                    }
                    slideEffect = false;
                    hero.takeDamage();
                    return;
                }
            }
            if (slideEffect) {
                hero.setXPos(hero.getXPos() + dx);
            } else if (hero.isMovingLeft()) {
                hero.moveLeft();
            } else {
                hero.moveRight();
            }
        }
    }

    /**
     * Deals with the movement of all enemies
     */
    private void handleEnemyMovement() {

        for (Enemy enemy: enemies) {
            boolean leftCollision = false;
            boolean rightCollision = false;

            if (horizontalCollision(enemy, hero, enemy.getXVel())) {
                rightCollision = true;
            } else if (horizontalCollision(enemy, hero, -1 * enemy.getXVel())) {
                leftCollision = true;
            }

            for (Entity entity: tangibles) {
                if (horizontalCollision(enemy, entity, enemy.getXVel())) {
                    rightCollision = true;
                } else if (horizontalCollision(enemy, entity, -1 * enemy.getXVel())) {
                    leftCollision = true;
                }
            }

            for (Enemy enemyB: enemies) {
                if (horizontalCollision(enemy, enemyB, enemy.getXVel())) {
                    rightCollision = true;
                } else if (horizontalCollision(enemy, enemyB, -1 * enemy.getXVel())) {
                    leftCollision = true;
                }
            }

            enemy.moveHorizontally(hero, leftCollision, rightCollision);
        }

    }

    /**
     * Deals with the movement of all movable entities
     */
    private void handleMovableEntityMovement() {
        for (MovableEntity entity: movableEntities) {
            entity.moveHorizontally();
        }
    }

    /**
     * Deals with the animation of all enemies
     */
    private void handleEnemyAnimations() {
        for (Enemy enemy: enemies) {
            enemy.animate();
        }
    }

    /**
     * Deletes any enemies marked for deletion
     */
    private void deleteEnemiesMarkedForDeletion() {
        for (Enemy enemy: enemies) {
            if (enemy.isMarkedForDeletion()) {
                entities.remove(enemy);
            }
        }
        enemies.removeIf(Enemy::isMarkedForDeletion);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public double getHeroY() {
        return hero.getYPos();
    }

    @Override
    public Controllable getHero() {
        return hero;
    }

    @Override
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
        this.tangibles.addAll(entities);
        this.tangibles.removeIf(entity -> !entity.isTangible());
    }

    @Override
    public void setMovableEntities(List<MovableEntity> movableEntities) {
        this.movableEntities = movableEntities;
    }

    @Override
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
        this.tangibles.removeAll(enemies);
    }

    @Override
    public void setHero(Controllable hero) {
        this.hero = hero;
        this.slideVel = hero.getXVel();
    }

    @Override
    public void setFloorHeight(double floorHeight) {
        this.floorHeight = floorHeight;
    }

    @Override
    public void setLevelWidth(double width) {
        this.width = width;
    }

    @Override
    public void setTargetTime(double time) {
        this.targetTime = time;
    }

    @Override
    public void setLevelHeight(double height) {
        this.height = height;
    }

    @Override
    public void setGround(Entity ground) {
        this.entities.add(ground);
        this.tangibles.add(ground);
    }

    @Override
    public Level copy() {
        return new LevelImplementation(this);
    }

}
