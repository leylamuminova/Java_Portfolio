import java.util.ArrayList;

/**
 * StackAgenda - an Agenda that works as a stack (LIFO).
 * Uses the ArrayList from the abstract parent class for O(1) push/pop at the end.
 */
public class StackAgenda extends Agenda {

    /**
     * Constructs a StackAgenda and initializes locations as an ArrayList.
     */
    public StackAgenda() {
        locations = new ArrayList<MazeGridLocation>();
    }

    /** 
     * Adds a location to the top of the stack.
     */
    @Override
    public void addLocation(MazeGridLocation loc) {
        locations.add(loc);  // push to the end
    }

    /** 
     * Removes and returns the location on top of the stack.
     * Returns null if stack is empty.
     */
    @Override
    public MazeGridLocation removeLocation() {
        if (isEmpty()) {
            return null;
        }
        return locations.remove(locations.size() - 1); // pop from end
    }

    /** 
     * Test main method for StackAgenda (I didn't create another file).
     */
    public static void main(String[] args) {
        StackAgenda stack = new StackAgenda();

        MazeGridLocation loc1 = new MazeGridLocation(0, 0, '.');
        MazeGridLocation loc2 = new MazeGridLocation(1, 1, '#');
        MazeGridLocation loc3 = new MazeGridLocation(2, 2, '@');

        stack.addLocation(loc1);
        stack.addLocation(loc2);
        stack.addLocation(loc3);

        System.out.println("Stack contents: " + stack);
        System.out.println("Removed: " + stack.removeLocation());
        System.out.println("After removal: " + stack);
    }
}

