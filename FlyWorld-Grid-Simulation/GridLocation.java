import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Represents a single grid location (i.e., one row/column position)
 * within a 2D grid in a Graphical User Interface (GUI).
 */
public class GridLocation extends JPanel {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private int row;
    private int col;

    private Color backgroundColor;

    private Fly fly;
    private Critter critter; // Replaces Frog and Spider

    /**
     * Creates a new GridLocation at a specific location within the grid (r = row, c = column)
     * with a white background color.
     *
     * @param r the row
     * @param c the column
     */
    public GridLocation(int r, int c) {
        setBorder(new LineBorder(Color.BLACK));

        row = r;
        col = c;

        backgroundColor = Color.WHITE;
        setBackground(backgroundColor);
    }

    /** @return int, the row index of this location */
    public int getRow() {
        return row;
    }

    /** @return int, the column index of this location */
    public int getCol() {
        return col;
    }

    /**
     * Changes the background color of this location.
     *
     * @param color the color to set this square to (e.g., Color.GREEN)
     */
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
        setBackground(backgroundColor);
        repaint();
    }

    /**
     * Adds a Fly to this location.
     *
     * @param f the Fly to be added
     */
    public void setFly(Fly f) {
        fly = f;
        repaint();
    }

    /** Removes the Fly from this location. */
    public void removeFly() {
        fly = null;
        repaint();
    }

    /**
     * Adds a Critter (Frog or Spider) to this location.
     *
     * @param c the Critter to be added
     */
    public void setCritter(Critter c) {
        critter = c;
        repaint();
    }

    /** Removes the Critter (Frog or Spider) from this location. */
    public void removeCritter() {
        critter = null;
        repaint();
    }

    /** @return the Critter on this square (null if none) */
    public Critter getCritter() {
        return critter;
    }

    /**
     * Returns true if location contains a predator (Frog or Spider), false otherwise.
     */
    public boolean hasPredator() {
        return critter != null;
    }

    /**
     * Determines if two locations represent the same position.
     *
     * @param o another object which may or may not be a GridLocation
     * @return true if they have the same row and column
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof GridLocation)) {
            return false;
        }

        GridLocation other = (GridLocation) o;
        return row == other.row && col == other.col;
    }

    /** Simple string representation of a GridLocation (for debugging). */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Ensures that the size of the grid location is always a very specific size.
     * You do not need to worry about this method or call it ever.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    /**
     * This method actually draws the picture in the location if there is one.
     * You never need to call this method manually.
     *
     * @param g a Graphics object for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fly != null) {
            g.drawImage(fly.getImage(), 0, 0, null);
        }
        if (critter != null) {
            g.drawImage(critter.getImage(), 0, 0, null);
        }
    }
}
