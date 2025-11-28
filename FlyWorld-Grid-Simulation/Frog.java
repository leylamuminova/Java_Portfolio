/**
 * Frog class extends Critter.
 * Handles display, movement, and fly-eating behavior for frogs.
 */
public class Frog extends Critter {

    protected static final String imgFile = "frog.png";

    /**
     * Creates a new Frog object.
     * The image file for a frog is frog.png.
     *
     * @param loc the GridLocation (the square where the frog starts)
     * @param fw  the FlyWorld this frog is in
     */
    public Frog(GridLocation loc, FlyWorld fw) {
        super(loc, fw, imgFile);
    }

    /** Marks this location as containing a frog. */
    @Override
    protected void occupyLocation(GridLocation loc) {
        loc.setCritter(this);
    }

    /** Removes the frog from the current location. */
    @Override
    protected void vacateLocation(GridLocation loc) {
        loc.removeCritter();
    }

    /**
     * Generates all possible legal moves for this frog.
     * Frogs can move one step in the four cardinal directions.
     * Cannot move off the grid or onto another predator.
     *
     * @return GridLocation[] of legal grid locations
     */
    @Override
    protected GridLocation[] generateLegalMoves() {
        GridLocation[] temp = new GridLocation[4];
        int count = 0;

        int r = location.getRow();
        int c = location.getCol();

        // north
        if (world.isValidLoc(r - 1, c) && !world.getLocation(r - 1, c).hasPredator()) {
            temp[count++] = world.getLocation(r - 1, c);
        }
        // south
        if (world.isValidLoc(r + 1, c) && !world.getLocation(r + 1, c).hasPredator()) {
            temp[count++] = world.getLocation(r + 1, c);
        }
        // east
        if (world.isValidLoc(r, c + 1) && !world.getLocation(r, c + 1).hasPredator()) {
            temp[count++] = world.getLocation(r, c + 1);
        }
        // west
        if (world.isValidLoc(r, c - 1) && !world.getLocation(r, c - 1).hasPredator()) {
            temp[count++] = world.getLocation(r, c - 1);
        }

        GridLocation[] result = new GridLocation[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    /**
     * Determines if this frog can eat the fly.
     * A frog can eat the fly if it is:
     *  - On the same square
     *  - OR one square away (N, S, E, or W — not diagonal)
     *
     * @return true if the fly can be eaten, false otherwise
     */
    @Override
    public boolean eatsFly() {
        GridLocation flyLoc = world.getFlyLocation();

        int fr = location.getRow();
        int fc = location.getCol();
        int r = flyLoc.getRow();
        int c = flyLoc.getCol();

        // same square
        if (fr == r && fc == c) return true;

        // directly above, below, left, or right
        return (fr == r && Math.abs(fc - c) == 1) ||
               (fc == c && Math.abs(fr - r) == 1);
    }

    /** String representation of the frog for debugging. */
    @Override
    public String toString() {
        return "Frog at (" + location.getRow() + ", " + location.getCol() + ")";
    }
}
