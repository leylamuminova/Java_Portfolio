// Resubmission: I fixed argument handling (0 args = interactive, 2 args = file I/O),
// changed printChessBoard to use a PrintStream out instead of System.out,
// modularized into helper methods (isValidFEN and printChessBoard),
// and added clearer comments including why both row-length checks are needed.

/**
 * ReadFEN.java
 * Homework 1, Part 2A and 2B
 * 
 * This program takes a chess position written in Forsyth–Edwards Notation (FEN)
 * and shows it as an 8x8 ASCII board with dots for empty squares.
 *
 * Part 2A: user types FEN (interactive mode).
 * Part 2B: program can also read FEN from a file and write the board to another file.
 *
 * Example (interactive mode):
 * ---------------------------
 * $ java ReadFEN
 * Please enter a FEN:
 * 8/kb6/p7/2K5/8/5N2/8/8/
 *
 * Output:
 * ........
 * kb......
 * p.......
 * ..K.....
 * ........
 * .....N..
 * ........
 * ........
 *
 * Example (file mode):
 * --------------------
 * $ java ReadFEN input.txt output.txt
 *
 * input.txt contains:
 * rn3rk1/pbppq1pp/1p2pb2/4N2Q/3PN3/3B4/PPP2PPP/R3K2R/
 *
 * output.txt will contain:
 * rn...rk.
 * pbppq.pp
 * .p..pb..
 * ....N..Q
 * ...PN...
 * ...B....
 * PPP..PPP
 * R...K..R
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main class. Reads FEN and prints a chessboard.
 * Can do interactive (keyboard) or file mode.
 */
public class ReadFEN {

    /**
     * Main method.
     * Professor’s feedback addressed here:
     * fixed argument handling for 0 args (interactive) and 2 args (file I/O).
     * modularized code into helper methods isValidFEN and printChessBoard.
     *
     * @param args 0 or 2 arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            // interactive mode
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter a FEN:");
            String fen = input.nextLine();
            input.close();

            if (isValidFEN(fen)) {
                printChessBoard(fen, System.out);
            } else {
                System.out.println("Invalid input: FEN is not valid.");
            }

        } else if (args.length == 2) {
            // file mode, first arg = input file, second = output file
            String inputFile = args[0];
            String outputFile = args[1];
            try {
                Scanner fileScanner = new Scanner(new File(inputFile));
                if (fileScanner.hasNextLine()) {
                    String fen = fileScanner.nextLine().trim();
                    fileScanner.close();

                    if (isValidFEN(fen)) {
                        PrintStream out = new PrintStream(new File(outputFile));
                        printChessBoard(fen, out); // using PrintStream so it works for file or console
                        out.close();
                    } else {
                        System.out.println("Invalid input: FEN is not valid.");
                    }
                } else {
                    // file is empty
                    fileScanner.close();
                    System.out.println("Invalid input: File is empty.");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error: Input file not found.");
            }
        } else {
            // wrong number of arguments
            // error message for wrong number of arguments (addressing Muhammad Haroon's comment)
            System.out.println("Error: wrong number of arguments.");

        }
    }

    /**
     * Checks if FEN is valid.
     * Professor’s feedback addressed here:
     * explained why two row-length checks are needed:
     * 1) must be exactly 8 rows total
     * 2) each row must add up to exactly 8 squares
     *
     * @param fen the FEN string
     * @return true if valid, false otherwise
     */

    private static boolean isValidFEN(String fen) {
        String[] rows = fen.split("/");
        if (rows.length != 8) {
            return false; // check 1: must be exactly 8 rows
        }
        for (String row : rows) {
            int count = 0;      // count how many squares in this row
            char prev = ' ';    // save last char to check if two numbers are together
            for (int i = 0; i < row.length(); i++) {
                char c = row.charAt(i);
                if (c >= '1' && c <= '8') {
                    if (prev >= '1' && prev <= '8') {
                        return false; // no two digits allowed in a row
                    }
                    count += c - '0'; // convert digit char into int value
                } else if ("pnbrqkPNBRQK".indexOf(c) != -1) {
                    count++; // count a chess piece
                } else {
                    return false; // invalid symbol
                }
                prev = c;
            }
            if (count != 8) {
                return false; // check 2: row must add to exactly 8
            }
        }
        return true;
    }

    /**
     * Prints the ASCII board.
     * Professor’s feedback addressed here:
     * changed method to take PrintStream out instead of always using System.out.
     * this way we can print to screen or file with the same method.
     *
     * @param fen the FEN string
     * @param out where to print (System.out or file)
     */
    
    private static void printChessBoard(String fen, PrintStream out) {
        String[] rows = fen.split("/");
        for (String row : rows) {
            StringBuilder line = new StringBuilder(); // use StringBuilder to make row
            for (int i = 0; i < row.length(); i++) {
                char c = row.charAt(i);
                if (c >= '1' && c <= '8') {
                    int empty = c - '0';
                    for (int j = 0; j < empty; j++) {
                        line.append('.'); // put dots for empty squares
                    }
                } else {
                    line.append(c); // put the piece
                }
            }
            out.println(line.toString());
        }
    }
}
