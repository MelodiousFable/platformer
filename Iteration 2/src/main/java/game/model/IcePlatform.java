package game.model;

/**
 * Represents an ice platform in the game
 */
public class IcePlatform extends EntityAbstraction {

    /**
     * Creates an ice platform with the specified image path, xPos, yPos,
     * width and height
     */
    public IcePlatform(String imagePath, double xPos, double yPos, double width, double height) {
        super(imagePath, xPos, yPos, width, height, Layer.FOREGROUND);
    }

    public IcePlatform(IcePlatform other) {
        super(other);
    }

    @Override
    public Entity copy() {
        return new IcePlatform(this);
    }

    @Override
    public boolean isIcy() {
        return true;
    }

}
