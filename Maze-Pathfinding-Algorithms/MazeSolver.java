import java.util.ArrayList;

/**
 * MazeSolver
 * 
 * This class solves a maze using either Depth-First Search (DFS)
 * or Breadth-First Search (BFS), depending on which type of Agenda
 * (StackAgenda or QueueAgenda) is provided.
 * 
 * The solver keeps track of visited locations and uses parent links
 * to reconstruct the path from the start to the goal once the goal is found.
 */
public class MazeSolver {

    /** The agenda (stack or queue) controlling the exploration order. 
     * 
    */
    private Agenda agenda;

    /** A list of parent-child links used to reconstruct the path. 
     * 
    */
    private ArrayList<Link> parents;

    /**
     * Constructs a MazeSolver that will use the given Agenda type.
     * 
     * @param a the Agenda to use (StackAgenda for DFS, QueueAgenda for BFS)
     */
    public MazeSolver(Agenda a) {
        agenda = a;
        parents = new ArrayList<Link>();
    }

    /**
     * Attempts to solve the given maze and returns a path from start to goal.
     * 
     * The MazeGUI is updated step-by-step as the solver explores each location.
     * 
     * @param m  the Maze object to solve
     * @param mg the MazeGUI for visualization (used to show visited cells, agenda, and path)
     * @return an ArrayList of MazeGridLocation objects representing the path from
     *         start ('o') to goal ('*'); if no path is found, returns an empty list
     */
    public ArrayList<MazeGridLocation> solveMaze(Maze m, MazeGUI mg) {
        // Clear any previous search data
        agenda.clear();
        parents.clear();

        // Get start and goal locations
        MazeGridLocation start = m.getStartLocation();
        MazeGridLocation goal = m.getGoalLocation();

        // Add the start location to the agenda
        agenda.addLocation(start);
        mg.addLocToAgenda(start);

        // Continue searching until agenda is empty or goal is found
        MazeGridLocation goalReached = null;
        while (!agenda.isEmpty()) {
            MazeGridLocation current = agenda.removeLocation(); // get next location
            mg.visitLoc(current); // visualize visiting the location
            mg.pause(40); // small delay for animation
            m.markVisited(current); // mark it as visited

            // Check if goal is reached
            if (current.equals(goal)) {
                goalReached = current;
                break;
            }

            // Explore all open neighbors of current location
            ArrayList<MazeGridLocation> neighbors = m.getOpenNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                MazeGridLocation next = neighbors.get(i);
                if (!m.isVisited(next)) {
                    // Do not mark neighbors visited here; mark when they are
                    // removed from the agenda. Store parent now so path can
                    // be reconstructed later.
                    parents.add(new Link(next, current));
                    agenda.addLocation(next);
                    mg.addLocToAgenda(next);
                    mg.pause(30); // added for smoother animation
                }
            }
        }

        // Reconstruct and return the final path if goal was found
        return reconstructPath(m, mg, start, goalReached);
    }

    /**
     * Reconstructs the final path from start to goal using stored parent links.
     */
    private ArrayList<MazeGridLocation> reconstructPath(Maze m, MazeGUI mg, MazeGridLocation start, MazeGridLocation goalReached) {
        ArrayList<MazeGridLocation> path = new ArrayList<MazeGridLocation>();
        if (goalReached == null) {
            return path;
        }

        // Trace back from goal to start using parent links
        ArrayList<MazeGridLocation> reversePath = new ArrayList<>();
        MazeGridLocation step = goalReached;
        while (step != null && !step.equals(start)) {
            reversePath.add(step);
            step = getParent(step);
        }
        reversePath.add(start);

        // Reverse once instead of add(0, step) for efficiency
        for (int i = reversePath.size() - 1; i >= 0; i--) {
            path.add(reversePath.get(i));
            mg.addLocToPath(reversePath.get(i));
            mg.pause(40);
        }

        return path;
    }

    /**
     * Finds the parent location of a given child in the parent list.
     * 
     * @param child the MazeGridLocation whose parent is to be found
     * @return the parent MazeGridLocation if found, otherwise null
     */
    private MazeGridLocation getParent(MazeGridLocation child) {
        for (int i = parents.size() - 1; i >= 0; i--) {
            Link l = parents.get(i);
            if (l.child.equals(child)) {
                return l.parent;
            }
        }
        return null;
    }

    /**
     * Inner helper class to store a parent-child relationship between maze cells.
     */
    private static class Link {
        MazeGridLocation child;
        MazeGridLocation parent;

        /**
         * Constructs a new Link between a child and its parent.
         * 
         * @param c the child MazeGridLocation
         * @param p the parent MazeGridLocation
         */
        Link(MazeGridLocation c, MazeGridLocation p) {
            child = c;
            parent = p;
        }
    }
}