/**
 * TextAnalytics.java
 * Homework 6, Part 1–3
 * Reads a Gutenberg text, counts word frequencies, prints top 5,
 * and supports user queries. 
 */

import java.io.*;
import java.util.*;

public class TextAnalytics {

    public static void main(String[] args) {
        try {

            // Check if any command-line argument is provided
            if (args.length == 0) {
                System.out.println("Usage: java TextAnalytics <book.txt> ");
                return; 
            }

            String fileName = args[0];
            File inputFile = new File(fileName);
            
            Scanner scan = new Scanner(inputFile);

            // Skip lines prior to the start of the book 
            boolean hasStart = false; 
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();

                // Strict Gutenberg start marker
                if (line.contains("*** start of this project gutenberg ebook")) {
                    hasStart = true; 
                    break;
                } 
            }
            
            if (!hasStart){
                System.out.println("Invalid format. Missing proper Project Gutenberg START marker.");
                scan.close();
                return;
            }

            // Create a chaining HashMap to store word-occurence pairs
            ObjectHashMap wordMap = new ObjectHashMap(.9);
            // O(n): reading the entire book and touching each word once (n = number of words)
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();

                // Strict Gutenberg END marker
                if (line.contains("*** end of this project gutenberg ebook")){
                    break; 
                }

                // Clean + split
                String[] wordsInLine = line.replaceAll("[^a-z\\s]", "").split("\\s+");

                /* 
                 * O(n) across entire file: each word is processed once.
                 * put() is O(1) average; resize() is O(n) but rare.
                 */
                for (String word : wordsInLine) {
                    if (!word.isEmpty()) {
                        if (wordMap.containsKey(word)) {
                            Integer newValue = (Integer) wordMap.find(word) + 1;
                            wordMap.put(word, newValue);
                        } else {    
                            wordMap.put(word, 1);
                        }
                    }
                }
            }
    
            // O(n): getEntries() collects every (word, count) pair into one array
            Entry[] allWords = wordMap.getEntries();

            // O(n^2): insertion sort (worst-case pairwise shifting)
            insertionSort(allWords);

            // Top 5 frequent words
            System.out.println("Top 5 Most Frequent Words");
            for (int i = 0; i < 5 && i < allWords.length; i++){
                Entry entry = allWords[i];
                System.out.println((i + 1) + ".) '" + (String) entry.key + "'   " + entry.value + " uses.");
            }
            System.out.println();
                
            // user interaction
            // O(1): hashmap lookup for each user query
            Scanner input = new Scanner(System.in);
            System.out.print("Type a word or type 'q' to quit: ");
            String command = input.nextLine().trim().toLowerCase();

            while (!command.equals("q")) {

                if (!wordMap.containsKey(command)) {
                    System.out.println("The word '" + command + "' is not present.");
                } else {
                    System.out.println("The word '" + command + "' occurs " + wordMap.find(command) + " times.");
                }

                System.out.print("Type a word or type 'q' to quit: ");
                command = input.nextLine().trim().toLowerCase();
            }

            scan.close();
            input.close();    

        } catch (FileNotFoundException err) {
            System.out.println("File not found");
        }
    }
    
    /*
     * A reverse insertion method that sorts entries from largest to smallest values.
     * O(n^2) worst case because each element may shift many positions.
     */
    public static void insertionSort(Entry[] arr) {
        int i = 1;
        while (i < arr.length) {
            Entry currEntry = arr[i]; 
            int j = i - 1;

            while (j >= 0 && (int) arr[j].value < (int) currEntry.value) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = currEntry; 
            i++;
        }
    }
}
