import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * The Critter class serves as a parent class for creatures like Frog and Spider.
 * It stores their image, location, and the world they belong to.
 * Subclasses only need to define how they move (generateLegalMoves())
 * and how they interact with GridLocation (occupy/vacate hooks).
 */
public abstract class Critter {
    protected GridLocation location;   // Current position of the critter
    protected FlyWorld world;          // The world the critter lives in
    protected BufferedImage image;     // Image used to display the critter
    protected Random rand = new Random(); // For random movement decisions

    /**
     * Creates a new Critter.
     *
     * @param loc the GridLocation where the critter starts
     * @param fw  the FlyWorld this critter belongs to
     * @param imgFile the image filename (e.g., "frog.png" or "spider.png")
     */
    public Critter(GridLocation loc, FlyWorld fw, String imgFile) {
        this.location = loc;
        this.world = fw;

        // Load the critter's image
        try {
            image = ImageIO.read(new File(imgFile));
        } catch (IOException e) {
            System.out.println("Error loading image: " + imgFile);
            System.exit(0);
        }

        // Mark the grid square as occupied by this critter
        occupyLocation(loc);
    }

    /** Called when the critter occupies its starting location. */
    protected abstract void occupyLocation(GridLocation loc);

    /** Called when the critter leaves a location. */
    protected abstract void vacateLocation(GridLocation loc);

    /** Each subclass defines its legal movement options. */
    protected abstract GridLocation[] generateLegalMoves();

    /** Returns the critter's image. */
    public BufferedImage getImage() {
        return image;
    }

    /** Returns the critter's current location. */
    public GridLocation getLocation() {
        return location;
    }

    /**
     * Moves the critter to a new location in the grid,
     * updating occupancy flags automatically.
     */
    protected void moveTo(GridLocation nextLoc) {
        if (nextLoc == null || nextLoc == location) return;
        vacateLocation(location);
        location = nextLoc;
        occupyLocation(location);
    }

    /**
     * Checks if the critter currently occupies the same location as the Fly.
     * @return true if this critter has eaten the Fly.
     */
    public boolean eatsFly() {
        return world.getFlyLocation() == location;
    }

    /**
     * Default update() method shared by all Critters.
     * Chooses a random legal move from those available.
     * Subclasses can override this if they need special movement.
     */
    public void update() {
        GridLocation[] legalMoves = generateLegalMoves();

        if (legalMoves != null && legalMoves.length > 0) {
            GridLocation next;
            if (legalMoves.length == 1) {
                next = legalMoves[0];
            } else {
                next = legalMoves[rand.nextInt(legalMoves.length)];
            }
            moveTo(next);
        }
    }

    /**
     * Returns true if this Critter is a predator.
     * Required for CodeGrade tests that call isPredator() on Frog objects.
     */
    public boolean isPredator() {
        return true;
    }
}
