import java.util.Random;

/**
 * Spider class extends Critter.
 * Handles how spiders look, move, and eat the fly.
 */
public class Spider extends Critter {

    protected static final String imgFile = "spider.png";

    public Spider(GridLocation loc, FlyWorld fw) {
        super(loc, fw, imgFile);
    }

    @Override
    protected void occupyLocation(GridLocation loc) {
        loc.setCritter(this);
    }

    @Override
    protected void vacateLocation(GridLocation loc) {
        loc.removeCritter();
    }

    @Override
    public GridLocation[] generateLegalMoves() {
        GridLocation[] moves = new GridLocation[2];
        int count = 0;

        GridLocation flyLoc = world.getFlyLocation();
        int flyRow = flyLoc.getRow();
        int flyCol = flyLoc.getCol();

        int row = location.getRow();
        int col = location.getCol();

        // Horizontal move
        if (flyCol != col) {
            int newCol = (flyCol > col) ? col + 1 : col - 1;
            if (world.isValidLoc(row, newCol) && !world.getLocation(row, newCol).hasPredator()) {
                moves[count++] = world.getLocation(row, newCol);
            }
        }

        // Vertical move
        if (flyRow != row) {
            int newRow = (flyRow > row) ? row + 1 : row - 1;
            if (world.isValidLoc(newRow, col) && !world.getLocation(newRow, col).hasPredator()) {
                moves[count++] = world.getLocation(newRow, col);
            }
        }

        GridLocation[] result = new GridLocation[count];
        for (int i = 0; i < count; i++) {
            result[i] = moves[i];
        }
        return result;
    }

    @Override
    public boolean eatsFly() {
        GridLocation flyLoc = world.getFlyLocation();
        return location.equals(flyLoc);
    }

    @Override
    public String toString() {
        return "Spider at (" + location.getRow() + ", " + location.getCol() + ")";
    }
}
