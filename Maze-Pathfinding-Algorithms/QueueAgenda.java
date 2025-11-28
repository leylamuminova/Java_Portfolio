import java.util.LinkedList;

/**
 * QueueAgenda - an Agenda that works as a queue (FIFO).
 * Uses a LinkedList for efficient add/remove operations.
 */
public class QueueAgenda extends Agenda {

    /**
     * Constructs a QueueAgenda and initializes locations as a LinkedList.
     */
    public QueueAgenda() {
        locations = new LinkedList<MazeGridLocation>();
    }

    /**
     * Adds a location to the end of the queue.
     */
    @Override
    public void addLocation(MazeGridLocation loc) {
        locations.add(loc);  // enqueue
    }

    /**
     * Removes and returns the location at the front of the queue.
     * Returns null if queue is empty.
     */
    @Override
    public MazeGridLocation removeLocation() {
        if (isEmpty()) {
            return null;
        }
        return ((LinkedList<MazeGridLocation>) locations).removeFirst(); // dequeue from front
    }

    /**
     * Test main method for QueueAgenda.
     */
    public static void main(String[] args) {
        QueueAgenda queue = new QueueAgenda();

        MazeGridLocation loc1 = new MazeGridLocation(0, 0, '.');
        MazeGridLocation loc2 = new MazeGridLocation(1, 1, '.');
        MazeGridLocation loc3 = new MazeGridLocation(2, 2, '.');

        queue.addLocation(loc1);
        queue.addLocation(loc2);
        queue.addLocation(loc3);

        System.out.println("Queue contents: " + queue);
        System.out.println("Removed: " + queue.removeLocation());
        System.out.println("After removal: " + queue);
    }
}
