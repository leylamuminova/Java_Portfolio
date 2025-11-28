import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Contains information about the world (i.e., the grid of squares)
 * and handles most of the game play work that is NOT GUI specific.
 */
public class FlyWorld {
    protected int numRows;
    protected int numCols;

    protected GridLocation[][] world;

    protected GridLocation start;
    protected GridLocation goal;

    protected Fly mosca;

    // Unified predator array
    protected Critter[] critters;

    /**
     * Reads a file containing information about
     * the grid setup. Initializes the grid
     * and other instance variables for use by
     * FlyWorldGUI and other pieces of code.
     *
     * @param fileName the file containing the world grid information
     */
    public FlyWorld(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));

            // first two numbers in the file tell us grid size
            numRows = input.nextInt();
            numCols = input.nextInt();

            // move scanner to next line after reading two ints
            input.nextLine();

            // initialize arrays
            critters = new Critter[0];
            world = new GridLocation[numRows][numCols];

            // read each row of the map
            for (int r = 0; r < numRows; r++) {
                String line = input.nextLine();
                for (int c = 0; c < numCols; c++) {
                    world[r][c] = new GridLocation(r, c);
                    char ch = line.charAt(c);

                    if (ch == 'f') {
                        Frog frog = new Frog(world[r][c], this);
                        critters = Arrays.copyOf(critters, critters.length + 1);
                        critters[critters.length - 1] = frog;
                    } 
                    else if (ch == 'a') {
                        Spider spider = new Spider(world[r][c], this);
                        critters = Arrays.copyOf(critters, critters.length + 1);
                        critters[critters.length - 1] = spider;
                    } 
                    else if (ch == 's') {
                        start = world[r][c];
                        start.setBackgroundColor(Color.GREEN);
                        mosca = new Fly(world[r][c], this);
                    } 
                    else if (ch == 'h') {
                        goal = world[r][c];
                        goal.setBackgroundColor(Color.RED);
                    }
                }
            }

            input.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e.getMessage());
        }

        // Debug print (safe to remove later)
        System.out.println("numRows: " + numRows + "   numCols: " + numCols);
        System.out.println("start: " + start + "   goal: " + goal);
    }

    /** @return number of rows in the world */
    public int getNumRows() {
        return numRows;
    }

    /** @return number of columns in the world */
    public int getNumCols() {
        return numCols;
    }

    /** Checks if a specific row/column is inside the grid. */
    public boolean isValidLoc(int r, int c) {
        return (r >= 0 && r < numRows && c >= 0 && c < numCols);
    }

    /** @return GridLocation object at (r, c) */
    public GridLocation getLocation(int r, int c) {
        return world[r][c];
    }

    /** @return the fly’s current location */
    public GridLocation getFlyLocation() {
        return mosca.getLocation();
    }

    /** @return the Fly object itself (optional) */
    public Fly getFly() {
        return mosca;
    }

    /**
     * Moves the fly in the given direction and checks result.
     *
     * @param direction NORTH, SOUTH, EAST, or WEST
     * @return ATHOME if fly reaches goal, EATEN if predator gets it, NOACTION otherwise
     */
    public int moveFly(int direction) {
        mosca.update(direction);

        // Check if Mosca reached home
        if (mosca.getLocation().equals(goal)) {
            return FlyWorldGUI.ATHOME;
        }

        // Check if Mosca eaten by any critter
        for (int i = 0; i < critters.length; i++) {
            if (critters[i].eatsFly()) {
                return FlyWorldGUI.EATEN;
            }
        }

        return FlyWorldGUI.NOACTION;
    }

    /** Update one predator and tell if it ate the fly. */
    private boolean updateOnePredator(Critter c) {
        c.update();
        return c.eatsFly();
    }

    /**
     * Moves all predators one step, then checks if Mosca is eaten.
     *
     * @return true if any predator eats the fly, false otherwise
     */
    public boolean movePredators() {
        for (int i = 0; i < critters.length; i++) {
            if (updateOnePredator(critters[i])) {
                return true;
            }
        }
        return false;
    }
}
