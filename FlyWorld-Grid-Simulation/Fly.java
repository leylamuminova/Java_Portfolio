import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Contains several methods that aid in the
 * display and movement of Mosca, the fly.
 */
public class Fly {
    protected static final String imgFile = "fly.png";
    
    protected GridLocation location;   // the current location of the fly
    protected FlyWorld world;          // the world that the fly is in
    protected BufferedImage image;     // the image used to draw the fly

    /**
     * Creates a new Fly object.<br>
     * The image file for a fly is fly.png<br>
     *
     * @param loc the GridLocation where the fly starts
     * @param fw the FlyWorld the fly is in
     */
    public Fly(GridLocation loc, FlyWorld fw) {
        location = loc;
        world = fw;

        try {
            image = ImageIO.read(new File(imgFile));
        } catch (IOException ioe) {
            System.out.println("Unable to read image file: " + imgFile);
            System.exit(0);
        }

        // mark the starting location with the fly
        location.setFly(this);
    }

    /** @return BufferedImage, the image of the fly */
    public BufferedImage getImage() {
        return image;
    }

    /** @return GridLocation, the current location of the fly */
    public GridLocation getLocation() {
        return location;
    }

    /** @return boolean, always false because Mosca is not a predator */
    public boolean isPredator() {
        return false;
    }

    /** 
     * Returns a string representation of this Fly showing
     * the location coordinates and the world.
     */
    public String toString() {
        return "Fly in world: " + this.world +
               " at location (" + this.location.getRow() + ", " +
               this.location.getCol() + ")";
    }
    
    /**
     * Updates the fly's location in the world.<br>
     * The fly can move North, South, East, or West,
     * but only if the move stays inside the grid.<br>
     * If the move would leave the grid, the fly simply does not move.
     *
     * @param direction one of FlyWorldGUI.NORTH, SOUTH, EAST, WEST
     */
    public void update(int direction) {
        // current row and column
        int r = location.getRow();
        int c = location.getCol();

        // direction offsets: dr = change in row, dc = change in col
        int dr = 0, dc = 0;
        if (direction == FlyWorldGUI.NORTH) {
            dr = -1;  // move up
        } else if (direction == FlyWorldGUI.SOUTH) {
            dr = 1;   // move down
        } else if (direction == FlyWorldGUI.EAST) {
            dc = 1;   // move right
        } else if (direction == FlyWorldGUI.WEST) {
            dc = -1;  // move left
        }

        // compute new row and column
        int newR = r + dr;
        int newC = c + dc;

        // only move if the new position is valid (inside grid)
        if (world.isValidLoc(newR, newC)) {
            // remove fly from old location
            location.removeFly();

            // put fly in new location
            GridLocation next = world.getLocation(newR, newC);
            next.setFly(this);

            // update instance variable
            location = next;
        }
        // if not valid, nothing happens (fly stays put)
    }
}
