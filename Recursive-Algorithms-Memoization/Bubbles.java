import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CPS 112 – Homework 5: Bubbles
 * Recursively finds the maximum money that can be collected
 * by moving diagonally upward (NW or NE) on a grid.
 * Uses memoization for efficiency.
 *
 * Usage: java Bubbles file startRow startCol
 * Example: java Bubbles board.txt 6 3
 *
 * Time complexity is O(n^2)
 */

public class Bubbles
{
    /** Add any instance variables you might need */
    // memo moved into bubbleMoney (no longer needed here)

    /**
     * Helper method for bubbleMoney with memoization.
     * @param grid 2D array of dollar amounts
     * @param row starting row
     * @param col starting column
     * @return maximum dollar amount collectible
     */

    private static int bubbleMoneyHelper(int[][] grid, int[][] memo, int row, int col) {
        
        // Check for out of bounds first
        if (row < 0 || col < 0 || col >= memo[0].length)
            return 0;
            
        // Check if we've already computed this cell
        if (memo[row][col] != -1 )
            return memo[row][col];
            
        // Base case: reached top row  (comment kept — logic removed per professor)

        // Recursive case: try both diagonal paths
        int leftPath = grid[row][col] + bubbleMoneyHelper(grid, memo, row - 1, col - 1);
        int rightPath = grid[row][col] + bubbleMoneyHelper(grid, memo, row - 1, col + 1);
        
        // Store and return the maximum path value
        memo[row][col] =  Math.max(leftPath, rightPath);
        return memo[row][col];
    }
    
    /**
     * Main method to initiate bubble money calculation.
     * @param grid 2D array of dollar amounts
     * @param row starting row
     * @param col starting column
     * @return maximum dollar amount collectible
     */

    public static int bubbleMoney(int[][] grid, int row, int col) {

        // (debug printing removed per professor comments)

        // Divided out bounds checks for row and col
        if (row < 0 )
            throw new IndexOutOfBoundsException();
        if (row >= grid.length)
            throw new IndexOutOfBoundsException("grid length: " + grid.length + " grid[0].length: " + grid[0].length + " row: " + row + " col: " + col);
        if (col < 0)
            throw new IndexOutOfBoundsException();
        if (col >= grid[0].length)
            throw new IndexOutOfBoundsException();
            
        // memo is created locally (professor-approved)
        int[][] memo = new int[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) // Filling memo with -1
            for (int c = 0; c < grid[0].length; c++)
                memo[r][c] = -1;

        // Initialize top row directly (replaces removed base case)
        for (int c = 0; c < grid[0].length; c++)
            memo[0][c] = grid[0][c];
                
        return bubbleMoneyHelper(grid, memo, row, col);
    }

    /**
     * USAGE: java Bubbles file startRow startCol
     *        Ex. java Bubbles board.txt 6 3
     *
     * Reads file and stores the dollar amounts
     * in some variable.  Then calls bubbleMoney method
     * with the starting row and col.  You should print
     * the final answer returned by bubbleMoney
     */
    public static void main(String [] args)
    {
        // FILL IN
        try (Scanner input = new Scanner(new File(args[0]))) {
            int rows = input.nextInt();
            int cols = input.nextInt();
            int[][] grid = new int[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    grid[i][j] = input.nextInt();
            int startRow = Integer.parseInt(args[1]);
            int startCol = Integer.parseInt(args[2]);

            System.out.println(bubbleMoney(grid, startRow, startCol));

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
