import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ArrayListDB.java
 *
 * Goal:
 * (Part 2) Read a transactions file and build an in-memory inventory of Album objects
 * (Part 3) Let the user type commands:
 *   ADD, REMOVE, LIST, QUIT
 */

public class ArrayListDB {

    public static void main(String[] args) {

        // main "database" in memory 
        AlbumArrayList inventory = new AlbumArrayList(2);

        // load from file if filename is given
        if (args.length >= 1) {
            loadFromFile(args[0], inventory);
        }

        // show number of albums after loading (even if zero)
        System.out.println(inventory.size() + " albums");

        // moved UI part to new method after feedback
        runUI(inventory);
    }


    // runs the user interface (moved from main)
    private static void runUI(AlbumArrayList inventory) {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            String cmd = input.nextLine();

            if (cmd.isEmpty()) {
                // ignore empty lines
            } 
            else if (cmd.equals("ADD")) {
                handleAdd(input, inventory); // added separate method for ADD
            } 
            else if (cmd.equals("REMOVE")) {
                handleRemove(input, inventory); // added separate method for REMOVE
            } 
            else if (cmd.equals("LIST")) {
                handleList(inventory);
            } 
            else if (cmd.equals("QUIT")) {
                running = false; // end loop
            } 
            else {
                System.out.println("Unknown command: " + cmd);
            }
        }

        input.close();
    }


    // ADD handler 
    private static void handleAdd(Scanner input, AlbumArrayList inventory) {
        String line = input.nextLine(); // example: "Michael Jackson - BAD"
        String[] at = line.split(" - ", 2);

        if (at.length == 2 && !at[0].isEmpty() && !at[1].isEmpty()) {
            Album a = new Album(at[0], at[1]);
            inventory.add(a);
        } else {
            System.out.println("Invalid album format. Use: Artist - Title");
        }
    }


    // REMOVE handler 
    private static void handleRemove(Scanner input, AlbumArrayList inventory) {
        String s = input.nextLine();

        // used regex instead of manual loop (simpler digits check)
        if (s.matches("\\d+")) {
            int n = Integer.parseInt(s);
            if (n >= 1 && n <= inventory.size()) {
                inventory.remove(n - 1);
            } else {
                System.out.println("Number out of range.");
            }
        } else {
            System.out.println("Invalid number. Please enter a valid index.");
        }

        // print count after remove
        System.out.println(inventory.size() + " albums");
    }


    // LIST just prints everything
    private static void handleList(AlbumArrayList inventory) {
        System.out.println(inventory.size() + " albums");
        for (int i = 0; i < inventory.size(); i++) {
            Album a = inventory.get(i);
            System.out.println((i + 1) + ". " + a.getArtist() + " - " + a.getAlbumName());
        }
    }


    // file loading
    private static void loadFromFile(String fileName, AlbumArrayList inventory) {
        try (Scanner in = new Scanner(new File(fileName))) {

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.isEmpty()) continue;

                // format: COMMAND:Artist - Title
                String[] parts = line.split(":", 2);
                if (parts.length != 2) continue;

                String command = parts[0];
                String[] at = parts[1].split(" - ", 2);
                if (at.length != 2) continue;

                String artist = at[0];
                String title = at[1];
                Album album = new Album(artist, title);

                if (command.equals("ADD")) {
                    inventory.add(album);
                } else if (command.equals("REMOVE")) {
                    inventory.remove(album);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + fileName);
        }
    }
}
