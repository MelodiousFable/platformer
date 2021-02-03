package game.model;

/**
 * Represents the ground in the level
 */
public class Ground extends EntityAbstraction {

    public Ground(String imagePath, double xPos, double yPos, double width, double height) {
        super(imagePath, xPos, yPos, width, height, Layer.FOREGROUND);
    }

    public Ground(Ground other) {
        super(other);
    }

    @Override
    public Entity copy() {
        return new Ground(this);
    }
}
