import java.util.List;
import java.util.ArrayList;

/**
 * Abstract class representing a collection of MazeGridLocation
 * objects waiting to be processed (either as a Stack or a Queue).
 */
public abstract class Agenda {
    
    /** Internal list to hold MazeGridLocations. */
    protected List<MazeGridLocation> locations;

    // Constructor
    public Agenda() {
        // Default to ArrayList; subclasses (StackAgenda / QueueAgenda) can override if needed
        locations = new ArrayList<MazeGridLocation>();
    }

    /**
	 * Add a location to the agenda.
	 * @param loc The MazeGridLocation to add.
	 */
    public abstract void addLocation(MazeGridLocation loc);

    /**
	 * Remove and return a location from the agenda.
	 * @return The next MazeGridLocation to process.
	 */
    public abstract MazeGridLocation removeLocation();

    /**
	 * Checks if the agenda is empty.
	 * @return True if the agenda is empty, false otherwise.
	 */
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    /**
	 * Clears all locations from the agenda.
	 */
    public void clear() {
        locations.clear();
    }

    /**
     * Returns a string representation of the agenda for debugging.
     */
    @Override
    public String toString() {
        return locations.toString();
    }
}
