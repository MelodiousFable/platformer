package stickman.model;

import java.util.List;

public interface Level {
    List<Entity> getVanillaEntities();
    List<MovingEntity> getMovingEntities();
    double getHeight();
    double getWidth();

    void tick();

    double getFloorHeight();
    double getHeroX();

    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();
}
